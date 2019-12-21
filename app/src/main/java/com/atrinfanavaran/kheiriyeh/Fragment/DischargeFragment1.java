package com.atrinfanavaran.kheiriyeh.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.atrinfanavaran.kheiriyeh.Interface.onCallBackFragment1;
import com.atrinfanavaran.kheiriyeh.R;


public class DischargeFragment1 extends Fragment {

    private Button btn1Save;
    private onCallBackFragment1 onCallBackFragment1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        btn1Save = view.findViewById(R.id.btn_1);
        btn1Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBackFragment1.Save1();
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (getActivity() instanceof onCallBackFragment1)
            onCallBackFragment1 = (onCallBackFragment1) getActivity();
    }
}
