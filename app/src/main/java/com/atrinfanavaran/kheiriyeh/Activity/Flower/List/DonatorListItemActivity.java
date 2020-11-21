package com.atrinfanavaran.kheiriyeh.Activity.Flower.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.atrinfanavaran.kheiriyeh.Activity.Flower.Add.AddDonateActivity;
import com.atrinfanavaran.kheiriyeh.Adapter.Flower.DonatorListAdapter;
import com.atrinfanavaran.kheiriyeh.Domain.DonatorApi;
import com.atrinfanavaran.kheiriyeh.Domain.FlowerCrownApi;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.Filter;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.FilteredDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.GenericFilter.GenericFilterDialog;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.Domian.DonatorR;
import com.google.android.gms.common.api.Api;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DonatorListItemActivity extends BaseActivity {


    private Toolbar my_toolbar;
    private RecyclerView.Adapter adapter1;
    private RecyclerView row1;
    private TextView title;
    private FloatingActionButton addBtn;
    private TextView emptyText;

    private HashMap<Integer, FilteredDomain> result = new HashMap<>();
    private LinearLayout filterBtn, backButton;
    private List<DonatorR> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        initView();
        NavigationDrawer();
        setvariable();
    }

    private void setvariable() {
        title.setText("لیست اهدا کننده");
        addBtn.setOnClickListener(v -> startActivity(new Intent(DonatorListItemActivity.this, AddDonateActivity.class)));


        list.addAll(db().DonatorDao().getAll());
        if (list.size() > 0) {
            emptyText.setVisibility(View.GONE);
            adapter1 = new DonatorListAdapter(list);
            row1.setAdapter(adapter1);

        } else {
            emptyText.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DonatorListItemActivity.this);
        row1.setLayoutManager(linearLayoutManager);

        filterBtn.setVisibility(View.VISIBLE);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void initView() {

        my_toolbar = findViewById(R.id.toolbar);
        row1 = findViewById(R.id.View1);
        title = findViewById(R.id.titleToolbar);
        addBtn = findViewById(R.id.floatingActionButton2);
        emptyText = findViewById(R.id.EmptyWarning);
        filterBtn = findViewById(R.id.filterButton);
        backButton = findViewById(R.id.backButton);
    }

    private void NavigationDrawer() {

        NavigationDrawerFragment my_nav = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        my_nav.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), my_toolbar);

    }

    public void showFilterDialog() {
        Class DOMAIN = DonatorApi.class;

        GenericFilterDialog filterDialog = new GenericFilterDialog(
                this,
                this,
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
                            if (filters.get(i).getField().equals("assignmentDateEn")) {
                                String[] dates = filters.get(i).getValue().split("__");
                                String str = " and " + filters.get(i).getField() + " BETWEEN '" + dates[0] + "' and '" + dates[1] + "'";
                                filterStr.append(str);
                            } else {
                                filterStr.append(String.format(" and %s like '%%%s%%'", filters.get(i).getField(), filters.get(i).getValue()));
                            }
                        }
                    }

                    if (adapter1 != null) {
                        list.clear();
                    }

                    list = db().DonatorDao().getfilter(new SimpleSQLiteQuery("SELECT * from DonatorR  " + filterStr));
                    if (list.size() == 0) {
                        emptyText.setVisibility(View.VISIBLE);
                    }else{
                        emptyText.setVisibility(View.GONE);
                    }

                    adapter1 = new DonatorListAdapter(list);
                    row1.setAdapter(adapter1);

                });
        filterDialog.show();
    }
}