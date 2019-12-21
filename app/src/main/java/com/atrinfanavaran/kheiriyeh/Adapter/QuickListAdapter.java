package com.atrinfanavaran.kheiriyeh.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atrinfanavaran.kheiriyeh.R;

import java.util.ArrayList;

public class QuickListAdapter extends RecyclerView.Adapter<QuickListAdapter.ViewHolder> {

    private final ArrayList<String> array_object;


    public QuickListAdapter(ArrayList<String> result) {
        this.array_object = result;
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
            case 1: {
                holder.imageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.mipmap.exteactcir));
                break;
            }
            case 2: {
                holder.imageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.mipmap.addcir));
                break;
            }
            case 3: {
                holder.imageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.mipmap.addresscir));
                break;
            }
            case 4: {
                holder.imageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.mipmap.add_address));
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