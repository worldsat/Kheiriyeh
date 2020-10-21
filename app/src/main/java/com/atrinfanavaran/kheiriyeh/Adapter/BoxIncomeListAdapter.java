package com.atrinfanavaran.kheiriyeh.Adapter;

import androidx.room.Room;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxIncomeEdit;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.AppDatabase;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxIncomeR;

import java.text.DecimalFormat;
import java.util.List;

public class BoxIncomeListAdapter extends RecyclerView.Adapter<BoxIncomeListAdapter.ViewHolder> {

    private final List<BoxIncomeR> array_object;
    private onCallBackBoxIncomeEdit onCallBackBoxIncomeEdit;
    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");

    public BoxIncomeListAdapter(List<BoxIncomeR> result, onCallBackBoxIncomeEdit onCallBackBoxIncomeEdit) {

        this.array_object = result;
        this.onCallBackBoxIncomeEdit = onCallBackBoxIncomeEdit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_boxincome_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        SettingsBll settingsBll = new SettingsBll(holder.itemView.getContext());
        settingsBll.getUrlAddress();


        holder.number.setText(array_object.get(position).number);
        if (array_object.get(position).price != null && !array_object.get(position).price.equals("")) {
            holder.price.setText(formatter.format(Long.valueOf(array_object.get(position).price)));
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


        holder.moreOption.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(holder.itemView.getContext(), v);

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.popup_edit:
                        onCallBackBoxIncomeEdit.EditBoxIncome(array_object.get(position));

                        return true;
                    case R.id.popup_delete:
                        AppDatabase db = Room.databaseBuilder(holder.itemView.getContext(),
                                AppDatabase.class, "RoomDb")
                                .fallbackToDestructiveMigration()
                                .allowMainThreadQueries()
                                .build();

                        db.BoxIncomeDao().delete(array_object.get(position).id);
                        array_object.remove(holder.getAdapterPosition());

                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(), array_object.size());
                        return true;

                    default:
                        return false;
                }
            });
            popup.inflate(R.menu.popup_image_options);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                popup.setGravity(Gravity.RIGHT);
            }
            popup.show();
        });


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