package com.atrinfanavaran.kheiriyeh.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atrinfanavaran.kheiriyeh.Domain.FlowerCrownApi;
import com.atrinfanavaran.kheiriyeh.Domain.Sliders;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.R;

import java.util.ArrayList;
import java.util.List;

public class LastDischargeListAdapter extends RecyclerView.Adapter<LastDischargeListAdapter.ViewHolder> {

    private final List<FlowerCrownApi.Data> array_object;


    public LastDischargeListAdapter(  List<FlowerCrownApi.Data> result) {
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
        String Url = settingsBll.getUrlAddress();

//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.mipmap.profile_pic3);
//        requestOptions.error(R.mipmap.profile_pic3);


//        Glide.with(holder.itemView.getContext())
////                .setDefaultRequestOptions(requestOptions)
//                .load(Url)
//                .apply(RequestOptions.circleCropTransform())
//                .into(holder.pic);

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

        TextView date, code, name, price, address;


        private ViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            code = itemView.findViewById(R.id.code);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            address = itemView.findViewById(R.id.address);


        }
    }
}