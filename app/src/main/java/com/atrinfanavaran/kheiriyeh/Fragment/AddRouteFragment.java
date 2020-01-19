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

import com.atrinfanavaran.kheiriyeh.Domain.Route;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackRoute1;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.Domian.RouteR;


public class AddRouteFragment extends Fragment {

    private Button btn1Save;
    private onCallBackRoute1 onCallBack;
    private EditText edt1_1, edt1_2, edt1_3;
    private boolean editable = false;
    private Route route;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            route = (Route) bundle.get("Route");
            editable = (boolean) bundle.get("editable");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_route, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        if (route != null) {
            edt1_1.setText(route.getcode());
            edt1_2.setText(route.getday());
            edt1_3.setText(route.getaddress());
        }

        btn1Save = view.findViewById(R.id.btn_1);
        btn1Save.setOnClickListener(v -> {
            RouteR routeR = new RouteR();
            routeR.code = edt1_1.getText().toString().trim();
            routeR.day = edt1_2.getText().toString().trim();
            routeR.address = edt1_3.getText().toString().trim();
            if (editable) {
                routeR.id = this.route.getid();
            } else {
                routeR.isNew="true";
            }
            onCallBack.SaveRoute1(routeR, editable);
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (getActivity() instanceof onCallBackRoute1)
            onCallBack = (onCallBackRoute1) getActivity();
    }

    private void initView(View view) {
        edt1_1 = view.findViewById(R.id.edt1_1);
        edt1_2 = view.findViewById(R.id.edt1_2);
        edt1_3 = view.findViewById(R.id.edt1_3);

    }
}
