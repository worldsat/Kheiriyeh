package com.atrinfanavaran.kheiriyeh.Fragment;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atrinfanavaran.kheiriyeh.Adapter.RouteListAdapter;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackAddRouteNew;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackRouteEdit;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.AppDatabase;
import com.atrinfanavaran.kheiriyeh.Room.Domian.RouteR;

import java.util.List;


public class RouteListFragment extends Fragment {

    private RecyclerView recyclerView;
    private AppDatabase db;
    private RecyclerView.Adapter adapter;
    private onCallBackAddRouteNew onCallBack;
    private onCallBackRouteEdit onCallBackRouteEdit;
    private FloatingActionButton floatingActionButton1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_boxincome_list, container, false);

        return rootView;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        db = Room.databaseBuilder(getActivity(),
                AppDatabase.class, "RoomDb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        List<RouteR> list = db.RouteDao().getAll();

        adapter = new RouteListAdapter(list, new onCallBackRouteEdit() {
            @Override
            public void EditRoute(RouteR routerR) {
                onCallBackRouteEdit.EditRoute(routerR);
            }
        });
        recyclerView.setAdapter(adapter);

        floatingActionButton1.setOnClickListener(v -> {
            onCallBack.btnNewRoute();
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (getActivity() instanceof onCallBackAddRouteNew)
            onCallBack = (onCallBackAddRouteNew) getActivity();
        if (getActivity() instanceof onCallBackRouteEdit)
            onCallBackRouteEdit = (onCallBackRouteEdit) getActivity();
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.view);
        floatingActionButton1 = view.findViewById(R.id.floatingActionButton);
    }
}
