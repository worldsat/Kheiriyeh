package com.atrinfanavaran.kheiriyeh.Kernel.GenericFilter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.FilteredDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.SpinnerDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewType;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.GenericListBll;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallBackSpinner;
import com.atrinfanavaran.kheiriyeh.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class GenericFilterAdapter extends RecyclerView.Adapter<GenericFilterAdapter.ViewHolder>
        implements View.OnClickListener {

    private final OnFinishedCallback callbackFinish;
    private ArrayList<DomainInfo> filters;
    private HashMap<Integer, FilteredDomain> filterResult;
    private HashMap<Integer, ArrayList<SpinnerDomain>> spinnerData = new HashMap<>(); //loaded spinner spinnerData
    private RecyclerView recyclerView;

    public GenericFilterAdapter(ArrayList<DomainInfo> filters,
                                HashMap<Integer, FilteredDomain> filteredList,
                                OnFinishedCallback filterListener) {
        this.filters = filters;
        filterResult = filteredList;
        this.callbackFinish = filterListener;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_filter, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.columnName.setText(filters.get(position).getTitle());

        //check if the row is using either a spinner or an editText
        if (!TextUtils.isEmpty(filters.get(position).getApiAddress())) {
            holder.valueEditTex.setVisibility(View.GONE);
            holder.spinnerWrapper.setVisibility(View.VISIBLE);

            //get the spinnerData to fill the spinner
            GenericListBll bll = new GenericListBll(holder.itemView.getContext());
            DomainInfo domain = filters.get(holder.getAdapterPosition());

            bll.populateSpinner(
                    domain.getId(),
                    domain.getViewType(),
                    domain.getApiAddress(),
                    new CallBackSpinner() {
                        @Override
                        public void onSuccess(ArrayList<SpinnerDomain> result) {

                            // dismiss the progress
                            holder.progress.setVisibility(View.GONE);

                            int adapterPosition = holder.getAdapterPosition();
                            spinnerData.put(adapterPosition, result);

                            ArrayAdapter adapter = new ArrayAdapter<>(
                                    holder.itemView.getContext(),
                                    R.layout.spinner_item_blue,
                                    result
                            );

                            holder.spinner.setAdapter(adapter);


                            //search for values that have already been selected
                            if (!filterResult.isEmpty()) {
                                ArrayList<Integer> positions = new ArrayList<>(filterResult.keySet());
                                for (int i = 0; i < filterResult.size(); i++) {
                                    if (Objects.requireNonNull(positions.get(i)).equals(adapterPosition)) {
                                        FilteredDomain item = filterResult.get(adapterPosition);
                                        ArrayList<SpinnerDomain> spinnerDomains = spinnerData.get(adapterPosition);

                                        if (spinnerDomains != null) {

                                            int selectedIndex = getSelectedIndex(item, spinnerDomains);
                                            holder.spinner.setSelection(selectedIndex, true);
                                            SpinnerDomain selectedRow = result.get(selectedIndex);
                                            Log.i("selectedId", selectedRow.getValue());
                                            holder.switchKey.setChecked(true);
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(String error) {
                            holder.progress.setVisibility(View.GONE);
                            Toast.makeText(holder.itemView.getContext(), "خطا در دریافت اطلاعات", Toast.LENGTH_LONG).show();
                        }
                    });

        } else {
            holder.valueEditTex.setVisibility(View.VISIBLE);
            holder.spinnerWrapper.setVisibility(View.GONE);
            holder.switchKey.setChecked(false);
        }

        //select rows of already spinnerSelectedMap for editTexts
        if (!filterResult.isEmpty()) {
            int adapterPosition = holder.getAdapterPosition();
            ArrayList<Integer> postions = new ArrayList<>(filterResult.keySet());

            FilteredDomain filteredDomain = null;
            for (int i = 0; i < filterResult.size(); i++) {
                // if current adapter position happens to be the same as the saved postion modify that row
                if (Objects.requireNonNull(postions.get(i)).equals(adapterPosition)) {
                    filteredDomain = filterResult.get(adapterPosition);
                    DomainInfo domain = filters.get(adapterPosition);

                    //editText
                    if (domain.getViewType().equals(ViewType.EDIT_TEXT.name()) || domain.getViewType().equals(ViewType.TEXT_VIEW.name())) {
                        holder.valueEditTex.setText(Objects.requireNonNull(filteredDomain).getValue());
                        holder.switchKey.setChecked(true);
                    }
                }
            }
        }


    }

    // get selected item's index for a given spinner
    private int getSelectedIndex(FilteredDomain selectedObj, ArrayList<SpinnerDomain> dataSet) {
        for (int j = 0; j < dataSet.size(); j++) {
            String selectedValue = Objects.requireNonNull(selectedObj).getValue();
            String rowValue = dataSet.get(j).getValue();

            if (selectedValue.equals(rowValue)) return j;
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    @Override
    public void onClick(View v) {
        for (int position = 0; position < filters.size(); position++) {

            ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
            if (holder != null) {
                if (holder.switchKey.isChecked()) {
                    if (!TextUtils.isEmpty(filters.get(position).getApiAddress())) { // check if spinner or editText
                        SpinnerDomain spinnerDomain = (SpinnerDomain) holder.spinner.getSelectedItem();

                        filterResult.put(
                                position,
                                new FilteredDomain(
                                        spinnerDomain.getParentId(),
                                        spinnerDomain.getValue())
                        );

                    } else {
                        DomainInfo domainInfo = filters.get(position);

                        filterResult.put(position, new FilteredDomain(
                                domainInfo.getId(),
                                holder.valueEditTex.getText().toString())
                        );
                    }
                } else filterResult.remove(position);
            }
        }

        callbackFinish.onFinish();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText valueEditTex;
        Switch switchKey;
        Spinner spinner;
        ProgressBar progress;
        RelativeLayout spinnerWrapper;
        private TextView columnName;

        public ViewHolder(View itemView) {
            super(itemView);
            columnName = itemView.findViewById(R.id.columnName);
            switchKey = itemView.findViewById(R.id.switchKey);
            valueEditTex = itemView.findViewById(R.id.value);
            spinner = itemView.findViewById(R.id.spinner);
            spinnerWrapper = itemView.findViewById(R.id.spinner_wrapper);
            progress = itemView.findViewById(R.id.progress);
        }
    }
}
