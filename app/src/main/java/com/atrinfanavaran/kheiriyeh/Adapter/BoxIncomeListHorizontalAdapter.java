package com.atrinfanavaran.kheiriyeh.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxIncomeR;

import java.text.DecimalFormat;
import java.util.List;

public class BoxIncomeListHorizontalAdapter extends RecyclerView.Adapter<BoxIncomeListHorizontalAdapter.ViewHolder> {

    private final List<BoxIncomeR> array_object;

    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");

    public BoxIncomeListHorizontalAdapter(List<BoxIncomeR> result) {

        this.array_object = result;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_last_discharge, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        SettingsBll settingsBll = new SettingsBll(holder.itemView.getContext());
        settingsBll.getUrlAddress();


        holder.number.setText(array_object.get(position).number);
        if (array_object.get(position).price != null && !array_object.get(position).price.equals("")) {
            try {
                holder.price.setText(formatter.format(Long.valueOf(array_object.get(position).price)));
            } catch (Exception e) {
                Log.i("moh3n", "onBindViewHolder: ");
            }
        }
        holder.assignmentDate.setText(array_object.get(position).assignmentDate);
        holder.name.setText(settingsBll.getName());
        holder.codeUser.setText("کد: " + settingsBll.getUserId());

        String statusStr = "";
        if (array_object.get(position).status != null) {
            switch (array_object.get(position).status) {
                case "1": {
                    statusStr = "عدم حضور";
                    break;
                }
                case "2": {
                    statusStr = "عدم موجودی";
                    break;
                }
                case "3": {
                    statusStr = "سایر موارد";
                    break;
                }
            }
        }
        holder.status.setText(statusStr);


    }

    @Override
    public int getItemCount() {
        return array_object == null ? 0 : array_object.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView number, price, status, assignmentDate, name, codeUser;
        ImageView moreOption;

        private ViewHolder(View itemView) {
            super(itemView);


            number = itemView.findViewById(R.id.code);
            price = itemView.findViewById(R.id.price);
            status = itemView.findViewById(R.id.status);
            assignmentDate = itemView.findViewById(R.id.assignmentDate);
            moreOption = itemView.findViewById(R.id.more_options);
            name = itemView.findViewById(R.id.name);
            codeUser = itemView.findViewById(R.id.codeUser);

        }
    }
}