package com.atrinfanavaran.kheiriyeh.Kernel.GenericEdit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alirezaafkar.sundatepicker.DatePicker;
import com.alirezaafkar.sundatepicker.components.DateItem;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.SpinnerDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewType;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.GenericListBll;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallBackSpinner;
import com.atrinfanavaran.kheiriyeh.Kernel.GenericEdit.Interface.OnDateListener;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.SearchableField;
import com.atrinfanavaran.kheiriyeh.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;



public class GenericEditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int EDIT_TEXT = 0;
    private static final int SPINNER = 1;
    private static final int CHECK_BOX = 2;
    private static final int TEXT_VIEW = 3;
    private static final int NUMBER_EDIT_TEXT = 4;
    private static final int DATE_EDIT_TEXT = 5;
    private static final int BIG_EDIT_TEXT = 6;
    private static final int DATE_CALENDAR_EDIT_TEXT = 7;
    private static final int HTML_EDIT_TEXT = 8;
    private final HashMap<String, String> data;
    private ArrayList<DomainInfo> domainInfos;
    private HashMap<Integer, ArrayList<SpinnerDomain>> spinnerData = new HashMap<>();
    private Activity Activity;
    private String dateFinal = "", hourFinal = "";

    public GenericEditAdapter(Activity activity, ArrayList<DomainInfo> domainInfos, HashMap<String, String> decodedData) {
        this.domainInfos = domainInfos;
        this.data = decodedData;
        this.Activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == EDIT_TEXT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_edit_et, parent, false);
            return new EditTextViewHolder(view);
        } else if (viewType == BIG_EDIT_TEXT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_edit_big_et, parent, false);
            return new BigEditTextViewHolder(view);
        } else if (viewType == NUMBER_EDIT_TEXT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_number_edit_et, parent, false);
            return new NumberEditTextViewHolder(view);
        } else if (viewType == DATE_EDIT_TEXT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_date_edit_et, parent, false);
            return new DateEditTextViewHolder(view);
        } else if (viewType == DATE_CALENDAR_EDIT_TEXT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_date_calendar_edit_et, parent, false);
            return new DateCalendarEditTextViewHolder(view);
        } else if (viewType == SPINNER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_edit_sp, parent, false);
            return new SpinnerViewHolder(view);
        } else if (viewType == CHECK_BOX) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_edit_cb, parent, false);
            return new CheckBoxViewHolder(view);
        } else if (viewType == TEXT_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_edit_tv, parent, false);
            return new TextViewViewHolder(view);

        } else {
            throw new RuntimeException("only three view types are allowed: spinner, editText and checkbox");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EditTextViewHolder) {
            initEditText((EditTextViewHolder) holder, position);
        } else if (holder instanceof BigEditTextViewHolder) {
            initBigEditText((BigEditTextViewHolder) holder, position);
        } else if (holder instanceof NumberEditTextViewHolder) {
            initNumberEditText((NumberEditTextViewHolder) holder, position);
        } else if (holder instanceof DateEditTextViewHolder) {
            initDateEditText((DateEditTextViewHolder) holder, position);
        } else if (holder instanceof DateCalendarEditTextViewHolder) {
            initDateCalendarEditText((DateCalendarEditTextViewHolder) holder, position);
        } else if (holder instanceof CheckBoxViewHolder) {
            initCheckBox((CheckBoxViewHolder) holder, position);
        } else if (holder instanceof SpinnerViewHolder) {
            initSpinner((SpinnerViewHolder) holder, position);
        }
    }


    private void initTextView(TextViewViewHolder holder, int position) {
        holder.title.setText(domainInfos.get(position).getTitle());
        if (!data.isEmpty()) {
            holder.info.setText(data.get(domainInfos.get(position).getId()));
        }
    }

    private void initCheckBox(CheckBoxViewHolder holder, int position) {
        holder.checkBoxTitle.setText(domainInfos.get(position).getTitle());

        // fill the view with data from 'data hashMap'
        // abort if 'data hashMap' is empty
        if (!data.isEmpty()) {
            if (data.get(domainInfos.get(position).getId()) != null && data.get(domainInfos.get(position).getId()).equals("1")) {
                holder.checkBox.setChecked(true);
            } else holder.checkBox.setChecked(false);
        }

        // listen for changes on checkBoxes
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                data.put(domainInfos.get(position).getId(), "1");
            } else data.put(domainInfos.get(position).getId(), "0");
        });
    }

    private void initSpinner(SpinnerViewHolder holder, int position) {
        holder.spinnerTitle.setText(domainInfos.get(position).getTitle());

        //populate spinner
        DomainInfo item = domainInfos.get(position);
        GenericListBll bll = new GenericListBll(holder.itemView.getContext());
        bll.populateSpinner(item.getId(), null, item.getViewType(), item.getApiAddress(), new CallBackSpinner() {
            @Override
            public void onSuccess(ArrayList<SpinnerDomain> response) {


                // add a `none` option to the spinner
                ArrayList<SpinnerDomain> result = new ArrayList<>();
                result.add(new SpinnerDomain(" ", " ", null));
                result.addAll(response);

                // dismiss the progress
                holder.progress.setVisibility(View.GONE);

                int adapterPosition = holder.getAdapterPosition();
                spinnerData.put(adapterPosition, result);

                ArrayAdapter adapter = new ArrayAdapter<>(holder.itemView.getContext(), R.layout.spinner_item_blue, result);

                holder.spinner.setAdapter(adapter);
//                SearchableField.setSpinner(holder.spinner, result);

                // select selected item on load completion
                // if data Map is not empty

                if (!data.isEmpty()) {
                    String id = domainInfos.get(adapterPosition).getId();

//                    String selectedId;
//                    if(id.equals("ParentId")){
//                         selectedId = data.get("InspectionTimingId");
//                    }else {
//                         selectedId = data.get(id);
//                    }

                    String selectedId = data.get(id);

                    for (int i = 0; i < spinnerData.get(adapterPosition).size(); i++) {
                        ArrayList<SpinnerDomain> list = spinnerData.get(adapterPosition);
                        for (int j = 0; j < list.size(); j++) {
                            if (selectedId.equals(list.get(j).getValue())) {
                                holder.spinner.setSelection(j, true);
                            }
                        }
                    }
                }

                holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        //save selected row
                        SpinnerDomain selectedRow = result.get(position);

                        if (selectedRow.getValue() != null) {
                            data.put(domainInfos.get(holder.getAdapterPosition()).getId(), selectedRow.getValue());
                        }

//                        Log.i("selectedId", selectedRow.getValue());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onError(String error) {
                // dismiss the progress
                holder.progress.setVisibility(View.GONE);
                Log.e("GenericEditAdapter", error);
            }
        });
    }

    private void initEditText(EditTextViewHolder holder, int position) {
        holder.editTextTitle.setText(domainInfos.get(position).getTitle());

        //set value if data is not empty
        if (!data.isEmpty()) {
            String value = data.get(domainInfos.get(position).getId());
            if (value != null)
                holder.editText.setText(value.replace("null", ""));
        }
        //listen for editText changes
        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data.put(domainInfos.get(holder.getAdapterPosition()).getId(), s.toString());
            }
        });
    }

    private void initBigEditText(BigEditTextViewHolder holder, int position) {
        holder.editTextTitle.setText(domainInfos.get(position).getTitle());

        //set value if data is not empty
        if (!data.isEmpty()) {
            String value = data.get(domainInfos.get(position).getId());
            if (value != null)
                holder.editText.setText(value.replace("null", ""));
        }
        //listen for editText changes
        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data.put(domainInfos.get(holder.getAdapterPosition()).getId(), s.toString());
            }
        });
    }

    private void initNumberEditText(NumberEditTextViewHolder holder, int position) {
        holder.editTextTitle.setText(domainInfos.get(position).getTitle());

        //set value if data is not empty
        if (!data.isEmpty()) {
            holder.editText.setText(data.get(domainInfos.get(position).getId()).replace("null", ""));
        }
        //listen for editText changes
        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data.put(domainInfos.get(holder.getAdapterPosition()).getId(), s.toString());
            }
        });
    }

    private void initDateEditText(DateEditTextViewHolder holder, int position) {
        holder.editTextTitle.setText(domainInfos.get(position).getTitle());

        //set value if data is not empty
        if (!data.isEmpty()) {
            if (data.get(domainInfos.get(position).getId()) != null) {
                holder.editText.setText(data.get(domainInfos.get(position).getId()).replace("null", ""));
            }
        }
        //listen for editText changes
        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data.put(domainInfos.get(holder.getAdapterPosition()).getId(), s.toString());
            }
        });
    }

    private void initDateCalendarEditText(DateCalendarEditTextViewHolder holder, int position) {
        holder.editTextTitle.setText(domainInfos.get(position).getTitle());


        //set value if data is not empty
        if (!data.isEmpty()) {
            if (data.get(domainInfos.get(position).getId()) != null) {
                try {
                    String str = data.get(domainInfos.get(position).getId()).replace("null", "");
                    String[] splited;
                    splited = str.split(" ", 2);

                    holder.editText.setText(splited[0]);
                    holder.Hour.setText(splited[1]);
                    dateFinal = splited[0];
                    hourFinal = splited[1];

                } catch (Exception e) {
                    Log.i("moh3n", "initDateCalendarEditText: " + e.toString());
                }
            }
        }
        holder.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker(Activity, new OnDateListener() {
                    @Override
                    public void DateSelected(String result) {
                        Log.i("moh3n", "DateSelected: " + result);
                        dateFinal = result;
                        data.put(domainInfos.get(holder.getAdapterPosition()).getId(), dateFinal + " " + hourFinal);
                        holder.editText.setText(result);
                    }
                });
            }
        });

        holder.Hour.setOnClickListener(v -> {


        });
    }


    @Override
    public int getItemViewType(int position) {
        DomainInfo item = domainInfos.get(position);
        if (item.getViewType().equals(ViewType.EDIT_TEXT.name())) {
            return EDIT_TEXT;
        } else if (item.getViewType().equals(ViewType.BIG_EDIT_TEXT.name())) {
            return BIG_EDIT_TEXT;
        } else if (item.getViewType().equals(ViewType.NUMBER_EDIT_TEXT.name())) {
            return NUMBER_EDIT_TEXT;
        } else if (item.getViewType().equals(ViewType.DATE_CALENDAR_EDIT_TEXT.name())) {
            return DATE_CALENDAR_EDIT_TEXT;
        } else if (item.getViewType().equals(ViewType.DATE_EDIT_TEXT.name())) {
            return DATE_EDIT_TEXT;
        } else if (!item.getApiAddress().isEmpty()) {
            return SPINNER;
        } else if (item.getViewType().equals(ViewType.CHECK_BOX.name())) {
            return CHECK_BOX;
        } else if (item.getViewType().equals(ViewType.TEXT_VIEW.name())) {
            return TEXT_VIEW;
        } else if (item.getViewType().equals(ViewType.HTML_EDIT_TEXT.name())) {
            return HTML_EDIT_TEXT;
        } else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return domainInfos.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void formateEditTextForDate(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            int len = 0;

            @Override
            public void afterTextChanged(Editable s) {
                String str = editText.getText().toString();
                if (str.length() == 4 && len < str.length()) {//len check for backspace
                    editText.append("/");
                }
                if (str.length() == 7 && len < str.length()) {//len check for backspace
                    editText.append("/");
                }
                if (str.length() == 10 && len < str.length()) {//len check for backspace
                    editText.append("\u0020");
                }
                if (str.length() == 13 && len < str.length()) {//len check for backspace
                    editText.append(":");
                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                String str = editText.getText().toString();
                len = str.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

        });
    }

    private void formateEditTextForHour(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            int len = 0;

            @Override
            public void afterTextChanged(Editable s) {
                String str = editText.getText().toString();
                if (str.length() == 2 && len < str.length()) {//len check for backspace
                    editText.append(":");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                String str = editText.getText().toString();
                len = str.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

        });
    }

    class CheckBoxViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView checkBoxTitle;

        CheckBoxViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.check_box);
            checkBoxTitle = itemView.findViewById(R.id.check_box_title);
        }
    }

    class SpinnerViewHolder extends RecyclerView.ViewHolder {
        private SearchableSpinner spinner;
        private TextView spinnerTitle;
        private ProgressBar progress;

        SpinnerViewHolder(@NonNull View itemView) {
            super(itemView);
            spinner = itemView.findViewById(R.id.spinner);
            spinnerTitle = itemView.findViewById(R.id.spinner_title);
            progress = itemView.findViewById(R.id.progress);
        }
    }

    class EditTextViewHolder extends RecyclerView.ViewHolder {
        private EditText editText;
        private TextView editTextTitle;

        EditTextViewHolder(@NonNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.edit_text);
            editTextTitle = itemView.findViewById(R.id.edit_text_title);
        }
    }

    class BigEditTextViewHolder extends RecyclerView.ViewHolder {
        private EditText editText;
        private TextView editTextTitle;

        BigEditTextViewHolder(@NonNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.edit_text);
            editTextTitle = itemView.findViewById(R.id.edit_text_title);
        }
    }

    class NumberEditTextViewHolder extends RecyclerView.ViewHolder {
        private EditText editText;
        private TextView editTextTitle;

        NumberEditTextViewHolder(@NonNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.edit_text);
            editTextTitle = itemView.findViewById(R.id.edit_text_title);
        }
    }


    class DateEditTextViewHolder extends RecyclerView.ViewHolder {
        private EditText editText;
        private TextView editTextTitle;

        DateEditTextViewHolder(@NonNull View itemView) {
            super(itemView);

            editText = itemView.findViewById(R.id.edit_text);
            editTextTitle = itemView.findViewById(R.id.edit_text_title);
            formateEditTextForDate(editText);

        }
    }

    class DateCalendarEditTextViewHolder extends RecyclerView.ViewHolder {
        private TextView Hour;
        private TextView editText;
        private TextView editTextTitle;

        DateCalendarEditTextViewHolder(@NonNull View itemView) {
            super(itemView);

            editText = itemView.findViewById(R.id.edit_text);
            Hour = itemView.findViewById(R.id.edit_text2);
            editTextTitle = itemView.findViewById(R.id.edit_text_title);
//            formateEditTextForHour(Hour);

        }
    }

    class TextViewViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView info;

        TextViewViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_view);
            info = itemView.findViewById(R.id.title_info);
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

            String str2 = year + "-" + month + "-" + day;
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



    private String colorHex(int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return String.format(Locale.getDefault(), "#%02X%02X%02X", r, g, b);
    }
}
