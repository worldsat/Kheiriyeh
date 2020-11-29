package com.atrinfanavaran.kheiriyeh.Kernel.GenericFilter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alirezaafkar.sundatepicker.DatePicker;
import com.alirezaafkar.sundatepicker.components.DateItem;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.FilteredDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.SpinnerDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewType;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.GenericListBll;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallBackSpinner;
import com.atrinfanavaran.kheiriyeh.Kernel.GenericEdit.Interface.OnDateListener;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.roozh;
import com.atrinfanavaran.kheiriyeh.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;


public class GenericFilterAdapter extends RecyclerView.Adapter<GenericFilterAdapter.ViewHolder>
        implements View.OnClickListener {

    private final OnFinishedCallback callbackFinish;
    private ArrayList<DomainInfo> filters;
    private HashMap<Integer, FilteredDomain> filterResult;
    private HashMap<Integer, ArrayList<SpinnerDomain>> spinnerData = new HashMap<>(); //loaded spinner spinnerData
    private RecyclerView recyclerView;
    private Activity activity;

    public GenericFilterAdapter(ArrayList<DomainInfo> filters,
                                HashMap<Integer, FilteredDomain> filteredList, Activity activity,
                                OnFinishedCallback filterListener) {
        this.filters = filters;
        filterResult = filteredList;
        this.callbackFinish = filterListener;
        this.activity = activity;
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
        if (filters.get(position).getApiAddresss().equals("BetweenCalendar")) {


            holder.valueEditTex.setVisibility(View.VISIBLE);
            holder.valueEditTex.setHint("تاریخ شروع");
            holder.valueEditTex2.setVisibility(View.VISIBLE);
            holder.calendarEnd.setVisibility(View.VISIBLE);
            holder.calendarStart.setVisibility(View.VISIBLE);
            holder.spinnerWrapper.setVisibility(View.GONE);
            holder.switchKey.setChecked(false);

            holder.calendarStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePicker((activity), new OnDateListener() {
                        @Override
                        public void DateSelected(String result) {
                            Log.i("moh3n", "DateSelectedStart: " + result);
//                            dateFinal = result;
//                            data.put(domainInfos.get(holder.getAdapterPosition()).getId(), dateFinal + " " + hourFinal);
                            holder.valueEditTex.setText(result);
                        }
                    });
                }
            });
            holder.calendarEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePicker((activity), new OnDateListener() {
                        @Override
                        public void DateSelected(String result) {
                            Log.i("moh3n", "DateSelectedEnd: " + result);
//                            dateFinal = result;
//                            data.put(domainInfos.get(holder.getAdapterPosition()).getId(), dateFinal + " " + hourFinal);
                            holder.valueEditTex2.setText(result);
                        }
                    });
                }
            });
        } else if (!TextUtils.isEmpty(filters.get(position).getApiAddresss())) {
            holder.valueEditTex.setVisibility(View.GONE);
            holder.valueEditTex2.setVisibility(View.GONE);
            holder.calendarEnd.setVisibility(View.GONE);
            holder.calendarStart.setVisibility(View.GONE);
            holder.spinnerWrapper.setVisibility(View.VISIBLE);

            //get the spinnerData to fill the spinner
            GenericListBll bll = new GenericListBll(holder.itemView.getContext());
            DomainInfo domain = filters.get(holder.getAdapterPosition());

            bll.populateSpinner(
                    domain.getId(),
                    domain.getViewType(),
                    domain.getApiAddresss(),
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
            holder.valueEditTex2.setVisibility(View.GONE);
            holder.calendarEnd.setVisibility(View.GONE);
            holder.calendarStart.setVisibility(View.GONE);
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

                    if (domain.getApiAddresss().equals("BetweenCalendar")) {

                        holder.switchKey.setChecked(true);
                        roozh roozh = new roozh();
                        if (filteredDomain != null) {
                            String[] dateAll = (filteredDomain).getValue().split("__");
                            String[] date = dateAll[0].split("/");
                            roozh.GregorianToPersian(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2]));
//                            String month = "";
//                            if (roozh.getMonth() < 10) {
//                                month = "0" + roozh.getMonth();
//                            } else {
//                                month = String.valueOf(roozh.getMonth());
//                            }
//
//                            String day = "";
//                            if (roozh.getDay() < 10) {
//                                day = "0" + roozh.getDay();
//                            } else {
//                                day = String.valueOf(roozh.getDay());
//                            }
                            String dateStart = roozh.getYear() + "/" + roozh.getMonth() + "/" + roozh.getDay();

                            holder.valueEditTex.setText(dateStart);

                            //***********************************************
                            String[] date2 = dateAll[1].split("/");
                            roozh.GregorianToPersian(Integer.valueOf(date2[0]), Integer.valueOf(date2[1]), Integer.valueOf(date2[2]));
//                            String month2 = "";
//                            if (roozh.getMonth() < 10) {
//                                month2 = "0" + roozh.getMonth();
//                            } else {
//                                month2 = String.valueOf(roozh.getMonth());
//                            }
//
//                            String day2 = "";
//                            if (roozh.getDay() < 10) {
//                                day2 = "0" + roozh.getDay();
//                            } else {
//                                day2 = String.valueOf(roozh.getDay());
//                            }
                            String dateEnd = roozh.getYear() + "/" + roozh.getMonth() + "/" + roozh.getDay();
                            holder.valueEditTex2.setText(dateEnd);
                        }
                    } else if (domain.getViewType().equals(ViewType.EDIT_TEXT.name()) || domain.getViewType().equals(ViewType.TEXT_VIEW.name())) {
                        //editText
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
                    if (filters.get(position).getApiAddresss().equals("BetweenCalendar")) {
                        DomainInfo domainInfo = filters.get(position);
                        roozh roozh = new roozh();


                        String[] date = holder.valueEditTex.getText().toString().split("/");
                        roozh.PersianToGregorian(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2]));
                        String month = "";
                        if (roozh.getMonth() < 10) {
                            month = "0" + roozh.getMonth();
                        } else {
                            month = String.valueOf(roozh.getMonth());
                        }

                        String day = "";
                        if (roozh.getDay() < 10) {
                            day = "0" + roozh.getDay();
                        } else {
                            day = String.valueOf(roozh.getDay());
                        }
                        String dateStart = roozh.getYear() + "/" + month + "/" + day;


                        String[] date2 = holder.valueEditTex2.getText().toString().split("/");
                        roozh.PersianToGregorian(Integer.valueOf(date2[0]), Integer.valueOf(date2[1]), Integer.valueOf(date2[2]));
                        if (roozh.getMonth() < 10) {
                            month = "0" + roozh.getMonth();
                        } else {
                            month = String.valueOf(roozh.getMonth());
                        }


                        if (roozh.getDay() < 10) {
                            day = "0" + roozh.getDay();
                        } else {
                            day = String.valueOf(roozh.getDay());
                        }
                        String dateEnd = roozh.getYear() + "/" + month + "/" + day;

                        filterResult.put(position, new FilteredDomain(
                                domainInfo.getId(),
                                dateStart + "__" + dateEnd)
                        );
                    } else if (!TextUtils.isEmpty(filters.get(position).getApiAddresss())) { // check if spinner or editText
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
        EditText valueEditTex, valueEditTex2;
        Switch switchKey;
        Spinner spinner;
        ProgressBar progress;
        ImageView calendarStart, calendarEnd;
        RelativeLayout spinnerWrapper;
        private TextView columnName;

        public ViewHolder(View itemView) {
            super(itemView);
            columnName = itemView.findViewById(R.id.columnName);
            switchKey = itemView.findViewById(R.id.switchKey);
            valueEditTex = itemView.findViewById(R.id.value);
            valueEditTex2 = itemView.findViewById(R.id.value2);
            calendarEnd = itemView.findViewById(R.id.calendar3);
            calendarStart = itemView.findViewById(R.id.calendar2);
            spinner = itemView.findViewById(R.id.spinner);
            spinnerWrapper = itemView.findViewById(R.id.spinner_wrapper);
            progress = itemView.findViewById(R.id.progress);
        }
    }

    public void DatePicker(Context context2, OnDateListener dateListener) {
        String str;
        FragmentActivity context;
        context = (FragmentActivity) context2;
        DatePicker.Builder builder = new DatePicker
                .Builder()
                .theme(R.style.DialogTheme)
                .future(true);
        Date mDate = new Date();
        builder.date(mDate.getDay(), mDate.getMonth(), mDate.getYear());
        builder.build((id, calendar, day, month, year) -> {

            mDate.setDate(day, month, year);

            String str2 = year + "/" + month + "/" + day;
            dateListener.DateSelected(str2);
        }).show(context.getSupportFragmentManager(), "");

    }

    class Date extends DateItem {
        String getDate() {
            Calendar calendar = getCalendar();
            return String.format(Locale.US,
                    "%d/%d/%d",
                    getYear(), getMonth(), getDay(),
                    calendar.get(Calendar.YEAR),
                    +calendar.get(Calendar.MONTH) + 1,
                    +calendar.get(Calendar.DAY_OF_MONTH));
        }
    }
}
