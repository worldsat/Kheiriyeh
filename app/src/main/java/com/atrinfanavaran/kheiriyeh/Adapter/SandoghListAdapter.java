package com.atrinfanavaran.kheiriyeh.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atrinfanavaran.kheiriyeh.Activity.Flower.List.DeceasedNameListItemActivity;
import com.atrinfanavaran.kheiriyeh.Activity.Flower.List.DonatorListItemActivity;
import com.atrinfanavaran.kheiriyeh.Activity.Flower.List.TajGolListItemActivity;
import com.atrinfanavaran.kheiriyeh.Activity.MainActivity;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackQuickList;
import com.atrinfanavaran.kheiriyeh.R;

import java.util.ArrayList;

public class SandoghListAdapter extends RecyclerView.Adapter<SandoghListAdapter.ViewHolder> {

    private final ArrayList<String> array_object;
    private onCallBackQuickList onCallBackQuickList;

    public SandoghListAdapter(ArrayList<String> result, onCallBackQuickList onCallBackQuickList) {
        this.array_object = result;
        this.onCallBackQuickList = onCallBackQuickList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_menu_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.title.setText(array_object.get(position));
        if (position == 0) {
            holder.imageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.mipmap.ic_9));
        } else if (position == 1) {
            holder.imageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.mipmap.ic_12));
        } else if (position == 2) {
            holder.imageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.mipmap.ic_11));
        } else if (position == 3) {
            holder.imageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.mipmap.ic_10));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);
                if (position == 0) {
                intent.putExtra("page","sandogh1");
                } else if (position == 1) {
                    intent.putExtra("page","sandogh2");
                } else if (position == 2) {
                    intent.putExtra("page","sandogh3");
                } else if (position == 3) {
                    intent.putExtra("page","sandogh4");
                }
                holder.itemView.getContext().startActivity(intent);
            }
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

        TextView title;
        ImageView imageView;

        private ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.imageView4);

        }
    }
}