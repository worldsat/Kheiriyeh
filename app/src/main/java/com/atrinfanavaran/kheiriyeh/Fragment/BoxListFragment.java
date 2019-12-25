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
import android.widget.TextView;

import com.atrinfanavaran.kheiriyeh.Adapter.BoxListAdapter;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackAddBoxNew;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxEdit;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.AppDatabase;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxR;

import java.util.List;


public class BoxListFragment extends Fragment {

    private RecyclerView recyclerView;
    private AppDatabase db;
    private RecyclerView.Adapter adapter;
    private onCallBackAddBoxNew onCallBackAddBoxNew;
    private onCallBackBoxEdit onCallBackBoxEdit;
    private FloatingActionButton floatingActionButton1;
    private TextView titleToolbar;
    private TextView emptyText;
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
        titleToolbar.setText("افزودن صندوق");

        db = Room.databaseBuilder(getActivity(),
                AppDatabase.class, "RoomDb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        List<BoxR> list = db.BoxDao().getAll();
        if (list.size() == 0) {
            emptyText.setVisibility(View.VISIBLE);
        }
        adapter = new BoxListAdapter(list, new onCallBackBoxEdit() {
            @Override
            public void EditBox(BoxR boxR) {
                onCallBackBoxEdit.EditBox(boxR);
            }
        });
        recyclerView.setAdapter(adapter);

        floatingActionButton1.setOnClickListener(v -> {
            onCallBackAddBoxNew.btnNewBox();

        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (getActivity() instanceof onCallBackAddBoxNew)
            onCallBackAddBoxNew = (onCallBackAddBoxNew) getActivity();
        if (getActivity() instanceof onCallBackBoxEdit)
            onCallBackBoxEdit = (onCallBackBoxEdit) getActivity();
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.view);
        floatingActionButton1 = view.findViewById(R.id.floatingActionButton);
        titleToolbar = getActivity().findViewById(R.id.titleToolbar);
        emptyText = view.findViewById(R.id.EmptyWarning);
    }
}
