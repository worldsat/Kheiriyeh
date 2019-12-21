package com.atrinfanavaran.kheiriyeh.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.atrinfanavaran.kheiriyeh.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ContractListAdapter extends RecyclerView.Adapter<ContractListAdapter.ViewHolder> {

    private final ArrayList<String> array_object;


    public ContractListAdapter(ArrayList<String> result) {
        this.array_object = result;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_dashboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

//        holder.title.setText(array_object.get(position).getContractName().replace("null", "-"));
//        holder.t1.setText(array_object.get(position).getCompanyName().replace("null", "-"));
//
//
//        NumberFormat nf = new DecimalFormat("################################################.###########################################");
////        holder.t2.setText(String.valueOf(nf.format(scientificDouble)));
//
//        holder.t2.setText(array_object.get(position).getContractNum().replace("null", "0"));
//
//        holder.t3.setText(array_object.get(position).getContractId().replace("null", "-"));
//
//
//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(holder.itemView.getContext(), ContractServiceDescriptionListActivity.class);
//            intent.putExtra("object", array_object.get(position));
//            holder.itemView.getContext().startActivity(intent);
//        });


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
        TextView t1, t2, t3;
        Button btn;
        ImageView line;

        private ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            btn = itemView.findViewById(R.id.btn1);
            line = itemView.findViewById(R.id.line);
        }
    }
}