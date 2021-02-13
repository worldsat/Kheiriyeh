package com.atrinfanavaran.kheiriyeh.Activity.SandoghSadaghat;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.atrinfanavaran.kheiriyeh.Activity.Flower.FlowerCrownListActivity;
import com.atrinfanavaran.kheiriyeh.Adapter.BoxIncomeListAdapter;
import com.atrinfanavaran.kheiriyeh.Adapter.MenuListAdapter;
import com.atrinfanavaran.kheiriyeh.Adapter.SandoghListAdapter;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxIncomeEdit;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackQuickList;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.AppDatabase;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxIncomeR;

import java.util.ArrayList;
import java.util.List;

public class SandoghListActivity extends BaseActivity {


    private Toolbar my_toolbar;
    private RecyclerView.Adapter adapter1;
    private RecyclerView.Adapter adapter2;
    private RecyclerView row1, row2;
    private TextView title;
    private List<BoxIncomeR> list;
    private AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        initView();
        NavigationDrawer();
        setvariable();
        getDate();
    }

    private void setvariable() {
        title.setText("صندوق صدقات");

        ArrayList<String> list = new ArrayList<>();
        list.add("تخلیه صندوق");
        list.add("افزودن صندوق");
        list.add("آدرس ها");
        list.add("افزودن آدرس");

        adapter1 = new SandoghListAdapter(list, new onCallBackQuickList() {
            @Override
            public void goTo(String page) {

            }
        });
        row1.setAdapter(adapter1);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(SandoghListActivity.this, 2);
        row1.setLayoutManager(linearLayoutManager);

        LinearLayout backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(v -> finish());
    }

    private void getDate() {
        db = Room.databaseBuilder(getActivity(),
                AppDatabase.class, "RoomDb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        row2.setAdapter(adapter2);
        LinearLayoutManager linearLayoutManager2 =new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        row2.setLayoutManager(linearLayoutManager2);

        list = db.BoxIncomeDao().getAll();

        if (list.size() == 0) {
            row2.setVisibility(View.GONE);
        } else {
            row2.setVisibility(View.VISIBLE);
        }
        adapter2 = new BoxIncomeListAdapter(true,list, new onCallBackBoxIncomeEdit() {
            @Override
            public void EditBoxIncome(BoxIncomeR boxIncome) {

            }

        });
        row2.setAdapter(adapter2);
    }
    private void initView() {

        my_toolbar = findViewById(R.id.toolbar);
        row1 = findViewById(R.id.View1);
        row2 = findViewById(R.id.View2);
        title = findViewById(R.id.Title);
    }

    private void NavigationDrawer() {

        NavigationDrawerFragment my_nav = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        my_nav.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), my_toolbar);

    }


}