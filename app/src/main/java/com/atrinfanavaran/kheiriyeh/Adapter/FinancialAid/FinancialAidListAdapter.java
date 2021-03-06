package com.atrinfanavaran.kheiriyeh.Adapter.FinancialAid;

import android.content.Intent;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.atrinfanavaran.kheiriyeh.Activity.Financial.Add.AddFinancialAidActivity;
import com.atrinfanavaran.kheiriyeh.Activity.Flower.Add.AddDonateActivity;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxEdit;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.AppDatabase;
import com.atrinfanavaran.kheiriyeh.Room.Domian.DonatorR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.FinancialAidR;

import java.text.DecimalFormat;
import java.util.List;

public class FinancialAidListAdapter extends RecyclerView.Adapter<FinancialAidListAdapter.ViewHolder> {

    private final List<FinancialAidR> array_object;
    private onCallBackBoxEdit onCallBackBoxEdit;
    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");

    public FinancialAidListAdapter(List<FinancialAidR> result) {

        this.array_object = result;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_financial_aid_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        SettingsBll settingsBll = new SettingsBll(holder.itemView.getContext());
        String Url = settingsBll.getUrlAddress();

        holder.title.setText(array_object.get(position).name);
        holder.t1.setText(formatter.format(Long.valueOf(array_object.get(position).price)) + " تومان ");
        holder.t2.setText(array_object.get(position).financialServiceType);
        String patType = "-";
        if (array_object.get(position).payType == 1) {
            patType = "نقدی";
        } else if (array_object.get(position).payType == 2) {
            patType = "دستگاه Pos";
        }
        holder.t3.setText(patType);


        holder.moreOption.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(holder.itemView.getContext(), v);

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.popup_edit:

                        Intent intent = new Intent(holder.itemView.getContext(), AddFinancialAidActivity.class);
                        intent.putExtra("object", array_object.get(position));
                        intent.putExtra("editable", true);
                        holder.itemView.getContext().startActivity(intent);

                        return true;
                    case R.id.popup_delete:
                        AppDatabase db = Room.databaseBuilder(holder.itemView.getContext(),
                                AppDatabase.class, "RoomDb")
                                .fallbackToDestructiveMigration()
                                .allowMainThreadQueries()
                                .build();
                        db.DonatorDao().delete(array_object.get(position).id);

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

        TextView title, t1, t2, t3;
        ImageView moreOption;

        private ViewHolder(View itemView) {
            super(itemView);
            moreOption = itemView.findViewById(R.id.more_options);
            title = itemView.findViewById(R.id.title);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);

        }
    }
}