package com.atrinfanavaran.kheiriyeh.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.atrinfanavaran.kheiriyeh.Domain.BoxIncome;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxIncome1;
import com.atrinfanavaran.kheiriyeh.R;


public class AddBoxIncomeFragment1 extends Fragment {

    private Button btn1Save;
    private onCallBackBoxIncome1 onCallBackBoxIncome1;
    private EditText edt1_1, edt1_2, edt1_3, edt1_4;
    private RadioGroup statusGroup;
    private RadioButton radio1, radio2, radio3;
    private String status;
    private BoxIncome boxIncome;
    private boolean editable = false;

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
        if (boxIncome != null) {
            edt1_1.setText(boxIncome.getfactorNumber());
            edt1_2.setText(boxIncome.getnumber());
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
            boxIncome.setfactorNumber(edt1_1.getText().toString().trim());
            boxIncome.setnumber(edt1_2.getText().toString().trim());
            boxIncome.setprice(edt1_3.getText().toString().trim());
            boxIncome.setregisterDate(edt1_4.getText().toString().trim());
            boxIncome.setstatus(status);
            if (editable)
                boxIncome.setid(this.boxIncome.getid());
            onCallBackBoxIncome1.SaveBoxIncome1(boxIncome, editable);
        });

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
        edt1_2 = view.findViewById(R.id.edt1_2);
        edt1_3 = view.findViewById(R.id.edt1_3);
        edt1_4 = view.findViewById(R.id.edt1_4);
        statusGroup = view.findViewById(R.id.radioButtonGroup);
        radio1 = view.findViewById(R.id.radioButton1);
        radio2 = view.findViewById(R.id.radioButton2);
        radio3 = view.findViewById(R.id.radioButton3);
    }
}
