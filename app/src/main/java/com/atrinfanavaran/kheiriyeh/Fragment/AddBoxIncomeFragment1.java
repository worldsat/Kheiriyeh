package com.atrinfanavaran.kheiriyeh.Fragment;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alirezaafkar.sundatepicker.DatePicker;
import com.alirezaafkar.sundatepicker.components.DateItem;
import com.atrinfanavaran.kheiriyeh.Domain.BoxIncome;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxIncome1;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.NumberTextWatcherForThousand;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.SearchableField;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.AppDatabase;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxR;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class AddBoxIncomeFragment1 extends Fragment {

    private Button btn1Save;
    private onCallBackBoxIncome1 onCallBackBoxIncome1;
    private EditText edt1_1, edt1_3, edt1_4, edt1_8, edt1_6, edt1_7;
    private RadioGroup statusGroup;
    private RadioButton radio1, radio2, radio3;
    private String status = "1";
    private BoxIncome boxIncome;
    private boolean editable = false;
    private ImageView calendarBtn;
    private SearchableSpinner spinner;
    private String numberSelected;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            boxIncome = (BoxIncome) bundle.get("BoxIncome");
            editable = (boolean) bundle.get("editable");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_discharge1, container, false);

        return rootView;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        statusGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioButton1:
                    status = "1";
                    break;
                case R.id.radioButton2:
                    status = "2";
                    break;
                case R.id.radioButton3:
                    status = "3";
                    break;
            }
        });


        AppDatabase db = Room.databaseBuilder(getActivity(),
                AppDatabase.class, "RoomDb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        List<BoxR> boxRS = db.BoxDao().getAll();
        List<String> boxR = new ArrayList<>();
        boxR.add("انتخاب کنید");
        for (int i = 0; i < boxRS.size(); i++) {
            boxR.add(boxRS.get(i).number + ":" + boxRS.get(i).fullName);
        }

        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_blue, boxR);
        SearchableField.setSpinner(spinner, boxR);
        spinner.setAdapter(adapter0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    numberSelected = getPosition(position);
                    BoxR boxR1 = db.BoxDao().getAllFilterNumber(getPosition(position));
                    if (boxR1 != null) {
                        edt1_6.setText(boxR1.fullName);
                        edt1_7.setText(boxR1.mobile);
                        edt1_8.setText(boxR1.address);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        edt1_3.addTextChangedListener(new NumberTextWatcherForThousand(edt1_3));

        if (boxIncome != null) {

            List<BoxR> boxRS2 = db.BoxDao().getAll();
            for (int i = 0; i < boxRS2.size(); i++) {
                if (boxRS2.get(i).number.equals(boxIncome.getnumber())) {
                    spinner.setSelection(i + 1);
                }
            }

            edt1_3.setText(boxIncome.getprice());
            edt1_4.setText(boxIncome.getregisterDate());

            switch (boxIncome.getstatus()) {
                case "1": {
                    radio1.setChecked(true);
                    break;
                }
                case "2": {
                    radio2.setChecked(true);
                    break;
                }
                case "3": {
                    radio3.setChecked(true);
                    break;
                }
            }
        }


        btn1Save = view.findViewById(R.id.btn_1);
        btn1Save.setOnClickListener(v -> {
            BoxIncome boxIncome = new BoxIncome();
            boxIncome.setnumber(numberSelected);
            boxIncome.setprice(NumberTextWatcherForThousand.trimCommaOfString(edt1_3.getText().toString().trim()));
            boxIncome.setregisterDate(edt1_4.getText().toString().trim());
            boxIncome.setstatus(status);
            if (editable)
                boxIncome.setid(this.boxIncome.getid());
            onCallBackBoxIncome1.SaveBoxIncome1(boxIncome, editable);
        });


        calendarBtn.setOnClickListener(v -> {
            DatePicker.Builder builder = new DatePicker
                    .Builder()
                    .theme(R.style.DialogTheme)
                    .future(true);
            Date mDate = new Date();
            builder.date(mDate.getDay(), mDate.getMonth(), mDate.getYear());
            builder.build((id, calendar, day, month, year) -> {

                mDate.setDate(day, month, year);
                edt1_4.setText(year + "/" + month + "/" + day);

            }).show(getActivity().getSupportFragmentManager(), "");

        });


    }

    private String getPosition(int position) {
        AppDatabase db = Room.databaseBuilder(getActivity(),
                AppDatabase.class, "RoomDb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        List<BoxR> boxRS = db.BoxDao().getAll();
        int n = position - 1;
        return boxRS.get(n).number;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (getActivity() instanceof onCallBackBoxIncome1)
            onCallBackBoxIncome1 = (onCallBackBoxIncome1) getActivity();
    }

    private void initView(View view) {
        edt1_1 = view.findViewById(R.id.edt1_1);

        edt1_3 = view.findViewById(R.id.edt1_3);
        edt1_4 = view.findViewById(R.id.edt1_4);
        edt1_6 = view.findViewById(R.id.edt1_6);
        edt1_7 = view.findViewById(R.id.edt1_7);
        edt1_8 = view.findViewById(R.id.edt1_8);
        statusGroup = view.findViewById(R.id.radioButtonGroup);
        radio1 = view.findViewById(R.id.radioButton1);
        radio2 = view.findViewById(R.id.radioButton2);
        radio3 = view.findViewById(R.id.radioButton3);
        calendarBtn = view.findViewById(R.id.calendar);
        spinner = view.findViewById(R.id.spinner2);
    }

    public void afterTextChanged(Editable view) {
        String s = null;
        try {
            // The comma in the format specifier does the trick
            s = String.format("%,d", Long.parseLong(view.toString()));
        } catch (NumberFormatException e) {
        }
        // Set s back to the view after temporarily removing the text change listener
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
