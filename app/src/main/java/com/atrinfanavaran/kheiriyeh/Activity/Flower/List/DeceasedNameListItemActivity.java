package com.atrinfanavaran.kheiriyeh.Activity.Flower.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.atrinfanavaran.kheiriyeh.Activity.Flower.Add.AddDeceasedNameActivity;
import com.atrinfanavaran.kheiriyeh.Adapter.Flower.DeceasedNameListAdapter;
import com.atrinfanavaran.kheiriyeh.Adapter.Flower.DonatorListAdapter;
import com.atrinfanavaran.kheiriyeh.Domain.DeceasedNameApi;
import com.atrinfanavaran.kheiriyeh.Domain.DonatorApi;
import com.atrinfanavaran.kheiriyeh.Domain.FlowerCrownApi;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.Filter;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.FilteredDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackGetString;
import com.atrinfanavaran.kheiriyeh.Kernel.GenericFilter.GenericFilterDialog;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.Domian.DeceasedNameR;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DeceasedNameListItemActivity extends BaseActivity {


    private Toolbar my_toolbar;
    private RecyclerView.Adapter adapter1;
    private RecyclerView row1;
    private TextView title;
    private FloatingActionButton addBtn;
    private TextView emptyText;
    private ProgressBar progressBar;
    private HashMap<Integer, FilteredDomain> result = new HashMap<>();
    private LinearLayout filterBtn, backButton;
    private List<DeceasedNameApi.Data> list = new ArrayList<>();

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
        title.setText("لیست متوفی");
        addBtn.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(DeceasedNameListItemActivity.this, AddDeceasedNameActivity.class));
        });

        getData(null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DeceasedNameListItemActivity.this);
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

    private void getData(StringBuilder filter) {
        String address = "api/DeceasedName/GetAll";
        if (filter != null) {
            address = "api/DeceasedName/" + filter;
        }
        progressBar.setVisibility(View.VISIBLE);
        controller().GetFromApi2(address, new CallbackGetString() {
            @Override
            public void onSuccess(String resultStr) {

                Gson gson = new Gson();
                DeceasedNameApi response = gson.fromJson(resultStr, DeceasedNameApi.class);

                list.addAll(response.getData());
                if (list.size() > 0) {
                    emptyText.setVisibility(View.GONE);
                    adapter1 = new DeceasedNameListAdapter(list);
                    row1.setAdapter(adapter1);

                } else {
                    emptyText.setVisibility(View.VISIBLE);
                    row1.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(String error) {
                progressBar.setVisibility(View.GONE);
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
        progressBar = findViewById(R.id.progressBarRow3);
    }

    private void NavigationDrawer() {

        NavigationDrawerFragment my_nav = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        my_nav.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), my_toolbar);

    }

    public void showFilterDialog() {
        Class DOMAIN = DeceasedNameApi.class;

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
                    StringBuilder filterStr = filteringDateOnline(filters);
                    if (adapter1 != null) {
                        if (list.size() > 0) {
                            list.clear();
                        }
                    }

//                    list = db().DeceasedNameDao().getfilter(new SimpleSQLiteQuery("SELECT * from DeceasedNameR  " + filterStr));
//                    if (list.size() == 0) {
//                        emptyText.setVisibility(View.VISIBLE);
//                    } else {
//                        emptyText.setVisibility(View.GONE);
//                    }
//
//                    adapter1 = new DeceasedNameListAdapter(list);
//                    row1.setAdapter(adapter1);
                    getData(filterStr);
                });
        filterDialog.show();
    }
}