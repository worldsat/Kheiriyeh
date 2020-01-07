package com.atrinfanavaran.kheiriyeh.Fragment;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.alirezaafkar.sundatepicker.DatePicker;
import com.alirezaafkar.sundatepicker.components.DateItem;
import com.atrinfanavaran.kheiriyeh.Domain.Box;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackAddBox;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.AppDatabase;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.RouteR;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class AddBoxFragment extends Fragment {

    private Button btn1Save;
    private onCallBackAddBox onCallBack;
    private EditText edt1_1, edt1_2, edt1_3, edt1_5, edt1_6;
    private Box box;
    private boolean editable = false;
    private Spinner spinner;
    private ImageView calendarBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            box = (Box) bundle.get("Box");
            editable = (boolean) bundle.get("editable");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_box, container, false);

        return rootView;


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        AppDatabase db = Room.databaseBuilder(getActivity(),
                AppDatabase.class, "RoomDb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        List<RouteR> Routes = db.RouteDao().getAll();
        List<String> Routes2 = new ArrayList<>();
        Routes2.add("انتخاب کنید");
        for (int i = 0; i < Routes.size(); i++) {
            Routes2.add(Routes.get(i).code);
        }

        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_blue, Routes2);
        spinner.setAdapter(adapter0);


        if (box != null) {
            edt1_1.setText(box.getFullName());
            edt1_2.setText(box.getNumber());
            edt1_3.setText(box.getMobile());
            edt1_6.setText(box.getAddress());
            edt1_5.setText(box.getRegisterDate());

        }
        if (box != null && box.getCode() != null) {
            for (int i = 0; i < Routes2.size(); i++) {
                if (Routes2.get(i).equals(box.getCode())) {
                    spinner.setSelection(i);
                    break;
                }
            }
        }

        btn1Save.setOnClickListener(v -> {
            BoxR box = new BoxR();
            box.fullName = edt1_1.getText().toString().trim();
            box.number = edt1_2.getText().toString().trim();
            box.mobile = edt1_3.getText().toString().trim();
            box.code = spinner.getSelectedItem().toString();
            box.registerDate = edt1_5.getText().toString().trim();
            box.address = edt1_6.getText().toString().trim();
            if (editable) {
                box.id = this.box.getId();
            } else {
                box.isNew = "true";
            }
            onCallBack.SaveBox(box, editable);
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
                edt1_5.setText(year + "/" + month + "/" + day);

            }).show(getActivity().getSupportFragmentManager(), "");

        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (getActivity() instanceof onCallBackAddBox)
            onCallBack = (onCallBackAddBox) getActivity();
    }

    private void initView(View view) {

        btn1Save = view.findViewById(R.id.btn_1);
        edt1_1 = view.findViewById(R.id.edt1_1);
        edt1_2 = view.findViewById(R.id.edt1_2);
        edt1_3 = view.findViewById(R.id.edt1_3);
        edt1_5 = view.findViewById(R.id.edt1_5);
        edt1_6 = view.findViewById(R.id.edt1_6);
        spinner = view.findViewById(R.id.spinner);
        calendarBtn = view.findViewById(R.id.calendar);
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
