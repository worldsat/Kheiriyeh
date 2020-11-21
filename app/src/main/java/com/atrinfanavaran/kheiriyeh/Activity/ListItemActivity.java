package com.atrinfanavaran.kheiriyeh.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atrinfanavaran.kheiriyeh.Adapter.MenuListAdapter;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackQuickList;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListItemActivity extends BaseActivity {


    private Toolbar my_toolbar;
    private RecyclerView.Adapter adapter1;
    private RecyclerView row1, row2;
    private TextView title;


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

        title.setText("لیست");

        ArrayList<String> list = new ArrayList<>();
        list.add("ثبت درآمد");
        list.add("افزودن خیرین و حامیان");
        list.add("گزارش خیرین");
        list.add("گزارش درآمدها");
        list.add("دریافت اطلاعات");
        list.add("ارسال اطلاعات");
        adapter1 = new MenuListAdapter(list, new onCallBackQuickList() {
            @Override
            public void goTo(String page) {

            }
        });
        row1.setAdapter(adapter1);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(ListItemActivity.this, 2);
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