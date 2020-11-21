package com.atrinfanavaran.kheiriyeh.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atrinfanavaran.kheiriyeh.Interface.onCallBackQuickList;
import com.atrinfanavaran.kheiriyeh.R;

import java.util.ArrayList;

public class QuickListAdapter extends RecyclerView.Adapter<QuickListAdapter.ViewHolder> {

    private final ArrayList<String> array_object;
    private onCallBackQuickList onCallBackQuickList;

    public QuickListAdapter(ArrayList<String> result, onCallBackQuickList onCallBackQuickList) {
        this.array_object = result;
        this.onCallBackQuickList = onCallBackQuickList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_quick_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.title.setText(array_object.get(position));
        switch (position) {
            case 0: {
                holder.imageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.mipmap.exteactcir));
                holder.itemView.setOnClickListener(v -> onCallBackQuickList.goTo("0"));
                break;
            }
            case 1: {
                holder.imageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.mipmap.addcir));
                holder.itemView.setOnClickListener(v -> onCallBackQuickList.goTo("1"));
                break;
            }
            case 2: {
                holder.imageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.mipmap.addresscir));
                holder.itemView.setOnClickListener(v -> onCallBackQuickList.goTo("2"));
                break;
            }
            case 3: {
                holder.imageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.mipmap.addroute));
                holder.itemView.setOnClickListener(v -> onCallBackQuickList.goTo("3"));
                break;
            }
            case 4: {
//                holder.imageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.mipmap.addroute));
                holder.itemView.setOnClickListener(v -> onCallBackQuickList.goTo("4"));
                break;
            }
            case 5: {
//                holder.imageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.mipmap.addroute));
                holder.itemView.setOnClickListener(v -> onCallBackQuickList.goTo("5"));
                break;
            }
            case 6: {
//                holder.imageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.mipmap.addroute));
                holder.itemView.setOnClickListener(v -> onCallBackQuickList.goTo("6"));
                break;
            }
        }
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

        TextView title;
        ImageView imageView;

        private ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.imageView4);

        }
    }
}