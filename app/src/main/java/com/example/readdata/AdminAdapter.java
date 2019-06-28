package com.example.readdata;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.viewHolder> {

    private List<AdminHistory> adminHistories;
    private Context context;

    public AdminAdapter(List<AdminHistory> adminHistories, Context context) {
        this.adminHistories = adminHistories;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.admin_card,viewGroup,false);
        return new viewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder viewHolder, int i) {
        final AdminHistory adminHistory = adminHistories.get(i);

        viewHolder.UserName.setText(adminHistory.getUser());
        viewHolder.Date.setText(adminHistory.getDate());
        viewHolder.Status.setText(adminHistory.getStatus());
        viewHolder.Total.setText(adminHistory.getTotal());
        viewHolder.AdminCheckBox.setOnCheckedChangeListener(null);
        viewHolder.AdminCheckBox.setChecked(adminHistory.isSelected());
        viewHolder.AdminCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                adminHistories.get(viewHolder.getAdapterPosition()).setSelected(isChecked);
            }
        });
        if (adminHistory.getOrder0()!=null)
            viewHolder.OrderHistory.append(adminHistory.getOrder0());
        if (adminHistory.getOrder1()!=null)
            viewHolder.OrderHistory.append("\n"+adminHistory.getOrder1());
        if (adminHistory.getOrder2()!=null)
            viewHolder.OrderHistory.append("\n"+adminHistory.getOrder2());
        viewHolder.UserId.setText(adminHistory.getUserId());

    }

    @Override
    public int getItemCount() {
        return adminHistories.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        public TextView OrderHistory;
        public TextView Date;
        public TextView Total;
        public TextView Status;
        public TextView UserName;
        public TextView UserId;
        public CheckBox AdminCheckBox;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            OrderHistory=itemView.findViewById(R.id.orderHistory);
            Date=itemView.findViewById(R.id.date);
            Total=itemView.findViewById(R.id.Total);
            Status=itemView.findViewById(R.id.Status);
            UserName=itemView.findViewById(R.id.user_name);
            AdminCheckBox=itemView.findViewById(R.id.admin_checkbox);
            UserId=itemView.findViewById(R.id.USERID);

        }
    }
}
