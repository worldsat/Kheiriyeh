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
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackFragment2;
import com.atrinfanavaran.kheiriyeh.R;


public class DischargeFragment2 extends Fragment {
    private Button btn2Save;
    private onCallBackFragment2 onCallBackFragment2;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_discharge2, container, false);

        return rootView;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn2Save = view.findViewById(R.id.btn_2);
        btn2Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBackFragment2.Save2();
            }
        });

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (getActivity() instanceof onCallBackFragment2)
            onCallBackFragment2 = (onCallBackFragment2) getActivity();
    }
}
