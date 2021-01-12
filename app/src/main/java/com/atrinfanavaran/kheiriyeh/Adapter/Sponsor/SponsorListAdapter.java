package com.atrinfanavaran.kheiriyeh.Adapter.Sponsor;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atrinfanavaran.kheiriyeh.Activity.Sponser.List.ContributionListItemActivity;
import com.atrinfanavaran.kheiriyeh.Activity.Sponser.List.SponsorListItemActivity;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackQuickList;
import com.atrinfanavaran.kheiriyeh.R;

import java.util.ArrayList;

public class SponsorListAdapter extends RecyclerView.Adapter<SponsorListAdapter.ViewHolder> {

    private final ArrayList<String> array_object;
    private onCallBackQuickList onCallBackQuickList;

    public SponsorListAdapter(ArrayList<String> result, onCallBackQuickList onCallBackQuickList) {
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    Intent intent = new Intent(holder.itemView.getContext(), ContributionListItemActivity.class);
                    holder.imageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.mipmap.ic_1));
                    holder.itemView.getContext().startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(holder.itemView.getContext(), SponsorListItemActivity.class);
                    holder.imageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.mipmap.ic_6));
                    holder.itemView.getContext().startActivity(intent);
                } else if (position == 2) {
//                    Intent intent = new Intent(holder.itemView.getContext(), DeceasedNameListItemActivity.class);
//                    holder.itemView.getContext().startActivity(intent);
                }
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