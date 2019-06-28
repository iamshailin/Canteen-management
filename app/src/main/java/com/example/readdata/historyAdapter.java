package com.example.readdata;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.ViewHolder> {

    private List<historyList> HistoryLists;
    private Context context;

    public historyAdapter(List<historyList> historyList, Context context) {
        HistoryLists = historyList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_card,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        historyList historyList = HistoryLists.get(i);
        if (historyList.getOrder0()!=null)
            viewHolder.OrderHistory.append(historyList.getOrder0());
        if (historyList.getOrder1()!=null)
            viewHolder.OrderHistory.append("\n"+historyList.getOrder1());
        if (historyList.getOrder2()!=null)
            viewHolder.OrderHistory.append("\n"+historyList.getOrder2());
        viewHolder.Date.setText(historyList.getDate().toString());
        viewHolder.Total.setText(historyList.getTotal());
        viewHolder.Status.setText(historyList.getStatus());

        if (viewHolder.Status.getText().equals("Confirm")){viewHolder.Status.setTextColor(Color.GREEN);}

        else if (viewHolder.Status.getText().equals("Denied")){viewHolder.Status.setTextColor(Color.RED);}

    }

    @Override
    public int getItemCount() {
        return HistoryLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView OrderHistory;
        public TextView Date;
        public TextView Total;
        public TextView Status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            OrderHistory=itemView.findViewById(R.id.orderHistory);
            Date=itemView.findViewById(R.id.date);
            Total=itemView.findViewById(R.id.Total);
            Status=itemView.findViewById(R.id.Status);

        }
    }
}
