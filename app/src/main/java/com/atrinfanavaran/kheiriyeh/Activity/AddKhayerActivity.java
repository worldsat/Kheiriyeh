package com.atrinfanavaran.kheiriyeh.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.ArrayList;

public class AddKhayerActivity extends BaseActivity {


    private Toolbar my_toolbar;

    private TextView title;
    private EditText edt1, edt2, edt3, edt4, edt5, edt6, edt7, edt8;
    private Button saveBtn;
    private TextView titleToolbar;
    private LinearLayout filterIcon, backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_khayer);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        initView();
        NavigationDrawer();
        setvariable();
    }

    private void setvariable() {
        titleToolbar.setText("افزودن خیرین");
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        filterIcon.setVisibility(View.GONE);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initView() {

        my_toolbar = findViewById(R.id.toolbar);
        edt1 = findViewById(R.id.edt1_1);
        edt2 = findViewById(R.id.edt1_2);
        edt3 = findViewById(R.id.edt1_3);
        edt4 = findViewById(R.id.edt1_4);
        edt5 = findViewById(R.id.edt1_5);
        edt6 = findViewById(R.id.edt1_6);
        edt7 = findViewById(R.id.edt1_7);
        edt8 = findViewById(R.id.edt1_8);
        saveBtn = findViewById(R.id.btn_1);
        titleToolbar = findViewById(R.id.titleToolbar);
        filterIcon = findViewById(R.id.filterButton);
        backIcon = findViewById(R.id.backButton);
    }

    private void NavigationDrawer() {

        NavigationDrawerFragment my_nav = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        my_nav.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), my_toolbar);

    }


}