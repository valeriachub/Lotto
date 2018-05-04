package valeria.app.lotto;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by valeria on 04.05.2018.
 */

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private ArrayList<Ticket> tickets;
    private TicketListener listener;
    private Context context;

    public TicketAdapter(ArrayList<Ticket> tickets, TicketListener listener) {
        this.tickets = tickets;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }

        View view = LayoutInflater.from(context).inflate(R.layout.li_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);

        if (ticket.isSelected()) {
            holder.itemView.setBackgroundResource(R.drawable.circle_selected);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.circle);
        }

        holder.numberView.setText(String.valueOf(ticket.getNumber()));
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item)
        LinearLayout itemView;
        @BindView(R.id.text_number)
        TextView numberView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.item)
        void onTicketClicked() {
            if (listener != null) {
                listener.onTicketClicked(tickets.get(getAdapterPosition()));
            }
        }
    }
}
