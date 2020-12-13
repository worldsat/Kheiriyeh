package com.atrinfanavaran.kheiriyeh.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.atrinfanavaran.kheiriyeh.Adapter.RouteListAdapter;
import com.atrinfanavaran.kheiriyeh.Domain.Route;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackAddRouteNew;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackRouteEdit;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.Filter;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.FilteredDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.GenericFilter.GenericFilterDialog;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.AppDatabase;
import com.atrinfanavaran.kheiriyeh.Room.Domian.RouteR;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class RouteListFragment extends Fragment {

    private RecyclerView recyclerView;
    private AppDatabase db;
    private RecyclerView.Adapter adapter;
    private onCallBackAddRouteNew onCallBack;
    private onCallBackRouteEdit onCallBackRouteEdit;
    private FloatingActionButton floatingActionButton1;
    private TextView titleToolbar;
    private TextView emptyText;
    private List<RouteR> list;
    private HashMap<Integer, FilteredDomain> result = new HashMap<>();
    private LinearLayout filterBtn;

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

        LinearLayout refreshBtn = getActivity().findViewById(R.id.refreshBtn);
        refreshBtn.setVisibility(View.VISIBLE);
        refreshBtn.setOnClickListener(v -> {
            if (list.size() > 0) {
                list.clear();
            }
            getDate();
            Toast.makeText(getActivity(), "لیست برورسانی شد", Toast.LENGTH_SHORT).show();
        });

        titleToolbar.setText("افزودن آدرس");
        db = Room.databaseBuilder(getActivity(),
                AppDatabase.class, "RoomDb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        getDate();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        floatingActionButton1.setOnClickListener(v -> {
            onCallBack.btnNewRoute();
        });
        filterBtn.setVisibility(View.VISIBLE);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });


    }

    private void getDate() {
        list = db.RouteDao().getAll();
        if (list.size() == 0) {
            emptyText.setVisibility(View.VISIBLE);
        }else{
            emptyText.setVisibility(View.GONE);
        }
        adapter = new RouteListAdapter(list, new onCallBackRouteEdit() {
            @Override
            public void EditRoute(RouteR routerR) {
                onCallBackRouteEdit.EditRoute(routerR);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void showFilterDialog() {
        Class DOMAIN = Route.class;

        GenericFilterDialog filterDialog = new GenericFilterDialog(
                getContext(),
                getContext(),
                DOMAIN,
                result,
                domainInfos -> {
                    result = domainInfos;
                    HashMap<String, String> filter = new HashMap<>();
                    ArrayList<Integer> keys = new ArrayList<>(domainInfos.keySet());

                    for (int i = 0; i < domainInfos.size(); i++) {
                        FilteredDomain item = domainInfos.get(keys.get(i));
                        filter.put(Objects.requireNonNull(item).getId(), item.getValue());
                    }

                    ArrayList<Filter> filters = new ArrayList<>();

                    if (filter != null && !filter.isEmpty()) {
                        for (Map.Entry<String, String> entry : filter.entrySet()) {
                            filters.add(new Filter(entry.getKey(), entry.getValue()));
                        }
                    }
                    StringBuilder filterStr = new StringBuilder();
                    if (filters != null && filters.size() > 0) {
                        filterStr.append(" where 1=1 ");
                        for (int i = 0; i < filters.size(); i++) {
                            filterStr.append(String.format(" and %s like '%%%s%%'", filters.get(i).getField(), filters.get(i).getValue()));
                        }
                    }

                    if (adapter != null) {
                        if (list.size() > 0) {
                            list.clear();
                        }
                    }

                    list = db.RouteDao().getfilter(new SimpleSQLiteQuery("SELECT * FROM RouteR " + filterStr));
                    if (list.size() == 0) {
                        emptyText.setVisibility(View.VISIBLE);
                    }

                    adapter = new RouteListAdapter(list, new onCallBackRouteEdit() {
                        @Override
                        public void EditRoute(RouteR routerR) {
                            onCallBackRouteEdit.EditRoute(routerR);
                        }
                    });
                    recyclerView.setAdapter(adapter);

                });
        filterDialog.show();
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
        titleToolbar = getActivity().findViewById(R.id.titleToolbar);
        filterBtn = getActivity().findViewById(R.id.filterButton);
        emptyText = view.findViewById(R.id.EmptyWarning);
    }
}
