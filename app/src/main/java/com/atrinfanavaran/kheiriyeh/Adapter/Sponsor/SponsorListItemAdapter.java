package com.atrinfanavaran.kheiriyeh.Adapter.Sponsor;

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
import com.atrinfanavaran.kheiriyeh.Activity.Sponser.Add.AddSponsorActivity;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxEdit;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.AppDatabase;
import com.atrinfanavaran.kheiriyeh.Room.Domian.FlowerCrownR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.SponsorR;

import java.util.List;

public class SponsorListItemAdapter extends RecyclerView.Adapter<SponsorListItemAdapter.ViewHolder> {

    private final List<SponsorR> array_object;
    private onCallBackBoxEdit onCallBackBoxEdit;
    private boolean showOnly = false;
    public SponsorListItemAdapter(boolean showOnly,List<SponsorR> result) {

        this.array_object = result;
        this.showOnly = showOnly;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_sponser_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        SettingsBll settingsBll = new SettingsBll(holder.itemView.getContext());
        String Url = settingsBll.getUrlAddress();

        holder.title.setText(array_object.get(position).fullName);
        holder.t1.setText("" + array_object.get(position).code);
        holder.t2.setText("" + array_object.get(position).nationalcode);
        holder.t3.setText("" + array_object.get(position).mobile);
        holder.t4.setText(array_object.get(position).phone);
        holder.t5.setText("" + array_object.get(position).birthDate);
        holder.t7.setText("" + array_object.get(position).address);
        if (showOnly) {
            holder.moreOption.setVisibility(View.GONE);
        }
        holder.moreOption.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(holder.itemView.getContext(), v);

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.popup_edit:

                        Intent intent = new Intent(holder.itemView.getContext(), AddSponsorActivity.class);
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
                        db.SponsorDao().delete(array_object.get(position).id);

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

        TextView title, t1, t2, t3, t4, t5, t6, t7;
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
            t7 = itemView.findViewById(R.id.t7);

        }
    }
}