package com.atrinfanavaran.kheiriyeh.Activity.Flower;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atrinfanavaran.kheiriyeh.Adapter.Flower.TajGolListAdapter;
import com.atrinfanavaran.kheiriyeh.Adapter.LastDischargeListAdapter;
import com.atrinfanavaran.kheiriyeh.Adapter.MenuListAdapter;
import com.atrinfanavaran.kheiriyeh.Domain.FlowerCrownApi;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackQuickList;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackGetString;
import com.atrinfanavaran.kheiriyeh.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FlowerCrownListActivity extends BaseActivity {


    private Toolbar my_toolbar;
    private RecyclerView.Adapter adapter1;
    private RecyclerView.Adapter adapter2;
    private RecyclerView row1, row2;
    private TextView title;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        initView();
        NavigationDrawer();
        setvariable();
        getData();
    }

    private void setvariable() {
        title.setText("تاج گل");

        ArrayList<String> list = new ArrayList<>();
        list.add("لیست تاج گل");
        list.add(" اهدا کننده");
        list.add(" متوفی");

        adapter1 = new MenuListAdapter(list, new onCallBackQuickList() {
            @Override
            public void goTo(String page) {

            }
        });

        row1.setAdapter(adapter1);
        row2.setAdapter(adapter2);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(FlowerCrownListActivity.this,2);
        LinearLayoutManager linearLayoutManager2 =new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        row1.setLayoutManager(linearLayoutManager);
        row2.setLayoutManager(linearLayoutManager2);


        LinearLayout backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(v -> finish());
    }

    private void getData() {
        String address = "api/FlowerCrown/GetAll?page=1";

        progressBar.setVisibility(View.VISIBLE);
        controller().GetFromApi2(address, new CallbackGetString() {
            @Override
            public void onSuccess(String resultStr) {

                Gson gson = new Gson();
                FlowerCrownApi response = gson.fromJson(resultStr, FlowerCrownApi.class);
                List<FlowerCrownApi.Data> list = new ArrayList<>();
                list.addAll(response.getData());
                if (list.size() > 0) {

                    adapter2 = new TajGolListAdapter(list);
                    row2.setAdapter(adapter2);

                } else {

                    row2.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(String error) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void initView() {

        my_toolbar = findViewById(R.id.toolbar);
        row1 = findViewById(R.id.View1);
        row2 = findViewById(R.id.View2);
        title = findViewById(R.id.Title);
        progressBar = findViewById(R.id.progressBarRow4);
    }

    private void NavigationDrawer() {

        NavigationDrawerFragment my_nav = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        my_nav.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), my_toolbar);

    }


}