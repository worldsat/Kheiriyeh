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

import com.atrinfanavaran.kheiriyeh.Domain.Box;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackAddBox;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxR;


public class AddBoxFragment extends Fragment {

    private Button btn1Save;
    private onCallBackAddBox onCallBack;
    private EditText edt1_1, edt1_2, edt1_3, edt1_4, edt1_5;
    private Box box;
    private boolean editable = false;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        if (box != null) {
            edt1_1.setText(box.getFullName());
            edt1_2.setText(box.getNumber());
            edt1_3.setText(box.getMobile());
            edt1_4.setText(box.getCode());
            edt1_5.setText(box.getRegisterDate());

        }
        btn1Save.setOnClickListener(v -> {
            BoxR box = new BoxR();
            box.fullName = edt1_1.getText().toString().trim();
            box.number = edt1_2.getText().toString().trim();
            box.mobile = edt1_3.getText().toString().trim();
            box.code = edt1_4.getText().toString().trim();
            box.registerDate = edt1_5.getText().toString().trim();
            if (editable)
                box.id = this.box.getId();
            onCallBack.SaveBox(box,editable);
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
        edt1_4 = view.findViewById(R.id.edt1_4);
        edt1_5 = view.findViewById(R.id.edt1_5);

    }
}
