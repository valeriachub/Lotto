package valeria.app.lotto;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.3.1.
 */
public class Lotto extends Contract {
    private static final String BINARY = "606060405260046002556004600355600a600455341561001e57600080fd5b6104ea8061002d6000396000f3006060604052600436106100a35763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663188af65781146100a85780631c771e74146100bd5780631d52fc0f146100e2578063214fa2cc146100f557806350b44712146101005780638bd8ecfd14610116578063a2fb117514610135578063c2dd79ae14610167578063e3ac5d261461017a578063e97206a91461018d575b600080fd5b34156100b357600080fd5b6100bb6101a0565b005b34156100c857600080fd5b6100d06102fc565b60405190815260200160405180910390f35b34156100ed57600080fd5b6100d0610302565b6100bb600435610308565b341561010b57600080fd5b6100d0600435610416565b341561012157600080fd5b6100d0600160a060020a0360043516610428565b341561014057600080fd5b61014b60043561043a565b604051600160a060020a03909116815260200160405180910390f35b341561017257600080fd5b6100d0610462565b341561018557600080fd5b6100d0610468565b341561019857600080fd5b6100d061046e565b6001600080805b6000548310156102215760058054600181016101c38382610474565b506000918252602080832087845260078252604080852088865260019081019093529093205491909201805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0390921691909117905592909201916101a7565b60015460055490925060009011156102ab5760055460015481151561024257fe5b049150600090505b6005548110156102a657600580548290811061026257fe5b600091825260209091200154600160a060020a031682156108fc0283604051600060405180830381858888f19350505050151561029e57600080fd5b60010161024a565b6102f6565b6005805460009081106102ba57fe5b600091825260209091200154600160a060020a031682156108fc0283604051600060405180830381858888f1935050505015156102f657600080fd5b50505050565b60025481565b60005481565b600080821180156103195750600b82105b151561032457600080fd5b600354600160a060020a033316600090815260066020526040902054111561034b57600080fd5b506000818152600760205260409081902090606090519081016040908152600160a060020a03331682523460208084019190915281830185905260008054815260018501909152208151815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03919091161781556020820151816001015560408201516002918201556000805460019081018255805434018155600160a060020a03331682526006602052604082208054909101905590549054109050610412576104126101a0565b5050565b60076020526000908152604090205481565b60066020526000908152604090205481565b600580548290811061044857fe5b600091825260209091200154600160a060020a0316905081565b60035481565b60015481565b60045481565b8154818355818115116104985760008381526020902061049891810190830161049d565b505050565b6104bb91905b808211156104b757600081556001016104a3565b5090565b905600a165627a7a7230582074e219152e7c12224a29404d88b77cb423915dd7c7e69618003134453c0e5afc0029";

    protected Lotto(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Lotto(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> generateWinner() {
        final Function function = new Function(
                "generateWinner", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> maxNumberOfBet() {
        final Function function = new Function("maxNumberOfBet", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> totalLottoTickets() {
        final Function function = new Function("totalLottoTickets", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> pickNumber(BigInteger number, BigInteger weiValue) {
        final Function function = new Function(
                "pickNumber", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(number)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<BigInteger> tickets(BigInteger param0) {
        final Function function = new Function("tickets", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> participantTicketsCount(String param0) {
        final Function function = new Function("participantTicketsCount", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> winners(BigInteger param0) {
        final Function function = new Function("winners", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> maxTicketForParticipant() {
        final Function function = new Function("maxTicketForParticipant", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> prize() {
        final Function function = new Function("prize", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> range() {
        final Function function = new Function("range", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<Lotto> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Lotto.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Lotto> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Lotto.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Lotto load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Lotto(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Lotto load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Lotto(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
