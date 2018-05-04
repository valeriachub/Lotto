package valeria.app.lotto;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements TicketListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.edit_wei)
    EditText weiView;

    private Unbinder unbinder;
    private Web3j web3j;
    private Lotto contract;
    private Credentials credentials;

    private TicketAdapter adapter;
    private ArrayList<Ticket> tickets;

    private SharedPreferences preferences;

    private ProgressDialog progressDialog;

    private String fileName;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        unbinder = ButterKnife.bind(this);

        initContract();
        initRecycler();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Const.REQUEST_PERMISSION_WRITE_STORAGE: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    finish();
                } else {
                    createWallet();
                }
                break;
            }
        }
    }

    private void initContract() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences != null && preferences.contains(Const.PREF_WALLET_ADDRESS) && !preferences.getString(Const.PREF_WALLET_ADDRESS, "").isEmpty()) {
            getPreferences();
            getContract();
        } else {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Const.REQUEST_PERMISSION_WRITE_STORAGE);
            } else {
                createWallet();
            }
        }

    }

    private void initRecycler() {
        tickets = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            Ticket ticket = new Ticket(i, false);
            tickets.add(ticket);
        }
        adapter = new TicketAdapter(tickets, this);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.btn_get_ticket)
    void onGetTicketClicked() {
        hideKeyoard(weiView);

        if (ifReady() && contract != null) {
            contract.pickNumber(BigInteger.valueOf((long) getSelectedNumber()),
                    BigInteger.valueOf(Long.valueOf(weiView.getText().toString())))
                    .observable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(() -> {
                        progressDialog = new ProgressDialog(this);
                        progressDialog.show();
                    })
                    .subscribe(transactionReceipt -> {
                        progressDialog.dismiss();
                        resetTickets();
                        weiView.setText("");
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    }, throwable -> {
                        progressDialog.dismiss();
                        resetTickets();
                        weiView.setText("");
                        Log.e("app", throwable.toString());
                        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private boolean ifReady() {
        if (weiView.getText().toString().isEmpty()) {
            Toast.makeText(this, "Wei amount is empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (getSelectedNumber() == 0) {
            Toast.makeText(this, "Please choose a ticket", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void hideKeyoard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void createWallet() {
        String password = Const.TEST_PASS;
        generateWallet(password);
    }

    public void generateWallet(final String password) {
        String fileName;
        String walletAddress;
        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!path.exists()) {
                path.mkdir();
            }
            fileName = WalletUtils.generateLightNewWalletFile(password, new File(String.valueOf(path)));
            Log.e("app", "generateWallet: " + path + "/" + fileName);

            Credentials credentials = WalletUtils.loadCredentials(password, path + "/" + fileName);
            walletAddress = credentials.getAddress();
            Log.e("app", "generateWallet: " + credentials.getAddress() + " " + credentials.getEcKeyPair().getPublicKey());

            saveWalletData(fileName, password, walletAddress);

            getPreferences();
            getContract();

        } catch (NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | IOException
                | CipherException e) {
            e.printStackTrace();
        }
    }

    private void saveWalletData(String fileName, String password, String address) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString(Const.PREF_WALLET_ADDRESS, address);
        editor.putString(Const.PREF_WALLET_FILE_NAME, fileName);
        editor.putString(Const.PREF_WALLET_PASSWORD, password);
        editor.apply();
    }

    private void getPreferences() {
        if (preferences == null || !preferences.contains(Const.PREF_WALLET_ADDRESS) || preferences.getString(Const.PREF_WALLET_ADDRESS, "").isEmpty())
            return;

        fileName = preferences.getString(Const.PREF_WALLET_FILE_NAME, "");
        address = preferences.getString(Const.PREF_WALLET_ADDRESS, "");

        Log.e("app", "Address: " + address);
    }

    private void getContract() {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        try {
            credentials = WalletUtils.loadCredentials(Const.TEST_PASS, path + "/" + fileName);
        } catch (IOException | CipherException e) {
            e.printStackTrace();
        }

        web3j = Web3jFactory.build(new HttpService("https://rinkeby.infura.io/IcfLiiNmda5oaEpkKWJk"));

        try {
            Log.e("app", "Connected to Ethereum client version: "
                    + web3j.web3ClientVersion().sendAsync().get().getWeb3ClientVersion());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        try {
            contract = Lotto.load("0x6c4bcd860061d0b586d8af0151470c563dece2a6", web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
            Log.e("app", contract.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTicketClicked(Ticket ticket) {
        for (int i = 0; i < 10; i++) {
            if (tickets.get(i).equals(ticket)) {
                tickets.get(i).setSelected(true);
            } else {
                tickets.get(i).setSelected(false);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private int getSelectedNumber() {
        int number = 0;
        for (int i = 0; i < 10; i++) {
            if (tickets.get(i).isSelected()) {
                number = tickets.get(i).getNumber();
            }
        }
        return number;
    }

    private void resetTickets() {
        for (int i = 0; i < 10; i++) {
            tickets.get(i).setSelected(false);
        }
        adapter.notifyDataSetChanged();
    }
}
