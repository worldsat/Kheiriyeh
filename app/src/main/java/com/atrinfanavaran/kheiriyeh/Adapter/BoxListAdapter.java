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

import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxEdit;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.AppDatabase;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxR;

import java.util.List;

public class BoxListAdapter extends RecyclerView.Adapter<BoxListAdapter.ViewHolder> {

    private final List<BoxR> array_object;
    private onCallBackBoxEdit onCallBackBoxEdit;

    public BoxListAdapter(List<BoxR> result, onCallBackBoxEdit onCallBackBoxEdit) {

        this.array_object = result;
        this.onCallBackBoxEdit = onCallBackBoxEdit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_box_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        SettingsBll settingsBll = new SettingsBll(holder.itemView.getContext());
        String Url = settingsBll.getUrlAddress();

        holder.fullName.setText(array_object.get(position).fullName);
        holder.number.setText(array_object.get(position).number);
        holder.mobile.setText(array_object.get(position).mobile);
        holder.code.setText(array_object.get(position).code2);
        holder.assignmentDate.setText(array_object.get(position).assignmentDate);
        holder.address.setText(array_object.get(position).address);

        holder.moreOption.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(holder.itemView.getContext(), v);

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.popup_edit:
                        onCallBackBoxEdit.EditBox(array_object.get(position));

                        return true;
                    case R.id.popup_delete:
                        AppDatabase db = Room.databaseBuilder(holder.itemView.getContext(),
                                AppDatabase.class, "RoomDb")
                                .fallbackToDestructiveMigration()
                                .allowMainThreadQueries()
                                .build();
                        db.BoxDao().delete(array_object.get(position).id);

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

        TextView fullName, number, mobile, code, assignmentDate,address;
        ImageView moreOption;

        private ViewHolder(View itemView) {
            super(itemView);
            moreOption = itemView.findViewById(R.id.more_options);
            fullName = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            mobile = itemView.findViewById(R.id.mobile);
            code = itemView.findViewById(R.id.code);
            assignmentDate = itemView.findViewById(R.id.assignmentDate);
            address = itemView.findViewById(R.id.address);

        }
    }
}