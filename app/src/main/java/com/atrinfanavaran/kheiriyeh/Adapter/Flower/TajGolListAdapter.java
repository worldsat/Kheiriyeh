package com.atrinfanavaran.kheiriyeh.Adapter.Flower;

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

import com.atrinfanavaran.kheiriyeh.Activity.Flower.Add.AddTajGolActivity;
import com.atrinfanavaran.kheiriyeh.Domain.FlowerCrownApi;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxEdit;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.AppDatabase;
import com.atrinfanavaran.kheiriyeh.Room.Domian.FlowerCrownR;

import java.text.DecimalFormat;
import java.util.List;

public class TajGolListAdapter extends RecyclerView.Adapter<TajGolListAdapter.ViewHolder> {

    private final List<FlowerCrownApi.Data> array_object;
    private onCallBackBoxEdit onCallBackBoxEdit;
    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");

    public TajGolListAdapter(List<FlowerCrownApi.Data> result) {
        this.array_object = result;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_taj_gol_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        SettingsBll settingsBll = new SettingsBll(holder.itemView.getContext());
        String Url = settingsBll.getUrlAddress();

        holder.title.setText(array_object.get(position).getDonatorName());
        holder.t1.setText(array_object.get(position).getDeceasedFullName());
        holder.t2.setText(array_object.get(position).getIntroducedName());
        holder.t3.setText(formatter.format(Long.valueOf(array_object.get(position).getPrice())) + " تومان ");

        holder.t5.setText("" + array_object.get(position).getFlowerCrownType());
        holder.t6.setText(array_object.get(position).getRegisterDate());

        String CeremonyType = "";
        switch (array_object.get(position).getCeremonyType()) {
            case 1: {
                CeremonyType="ترحیم";
                break;
            }
            case 2: {
                CeremonyType="هفته";
                break;
            }
            case 3: {
                CeremonyType="چهلم";
                break;
            }
            case 4: {
                CeremonyType="سال";
                break;
            }
            case 5: {
                CeremonyType="عید";
                break;
            }
        }
        holder.t4.setText(CeremonyType);


        holder.moreOption.setVisibility(View.GONE);
        holder.moreOption.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(holder.itemView.getContext(), v);

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.popup_edit:

                        Intent intent = new Intent(holder.itemView.getContext(), AddTajGolActivity.class);
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
                        db.FlowerCrownDao().delete(array_object.get(position).getId());

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

        TextView title, t1, t2, t3, t4, t5, t6;
        ImageView moreOption;

        private ViewHolder(View itemView) {
            super(itemView);
            moreOption = itemView.findViewById(R.id.more_options);
            title = itemView.findViewById(R.id.title);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            t4 = itemView.findViewById(R.id.t4);
            t5 = itemView.findViewById(R.id.t5);
            t6 = itemView.findViewById(R.id.t6);

        }
    }
}