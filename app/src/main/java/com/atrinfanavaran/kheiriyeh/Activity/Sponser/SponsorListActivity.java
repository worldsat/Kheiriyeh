package com.atrinfanavaran.kheiriyeh.Activity.Sponser;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atrinfanavaran.kheiriyeh.Adapter.MenuListAdapter;
import com.atrinfanavaran.kheiriyeh.Adapter.SponsorListAdapter;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackQuickList;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.R;

import java.util.ArrayList;

public class SponsorListActivity extends BaseActivity {


    private Toolbar my_toolbar;
    private RecyclerView.Adapter adapter1;
    private RecyclerView row1, row2;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        initView();
        NavigationDrawer();
        setvariable();
    }

    private void setvariable() {
        title.setText("مشارکت");

        ArrayList<String> list = new ArrayList<>();
        list.add("لیست فرم مشارکت");
        list.add("حامی");


        adapter1 = new SponsorListAdapter(list, new onCallBackQuickList() {
            @Override
            public void goTo(String page) {

            }
        });
        row1.setAdapter(adapter1);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(SponsorListActivity.this, 2);
        row1.setLayoutManager(linearLayoutManager);
    }


    private void initView() {

        my_toolbar = findViewById(R.id.toolbar);
        row1 = findViewById(R.id.View1);
        title = findViewById(R.id.Title);
    }

    private void NavigationDrawer() {

        NavigationDrawerFragment my_nav = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        my_nav.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), my_toolbar);

    }


}