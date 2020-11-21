package com.atrinfanavaran.kheiriyeh.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.atrinfanavaran.kheiriyeh.Adapter.MenuListAdapter;
import com.atrinfanavaran.kheiriyeh.Adapter.QuickListAdapter;
import com.atrinfanavaran.kheiriyeh.Domain.Box;
import com.atrinfanavaran.kheiriyeh.Domain.BoxIncome;
import com.atrinfanavaran.kheiriyeh.Domain.Route;
import com.atrinfanavaran.kheiriyeh.Domain.Setting;
import com.atrinfanavaran.kheiriyeh.Fragment.AddBoxFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.AddBoxIncomeFragment1;
import com.atrinfanavaran.kheiriyeh.Fragment.AddBoxIncomeFragment2;
import com.atrinfanavaran.kheiriyeh.Fragment.AddRouteFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.BoxIncomeListFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.BoxListFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.FirstFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.MapBoxFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.MapFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.RouteListFragment;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackAddBox;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackAddBox2;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackAddBoxNew;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackAddRouteNew;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxEdit;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxIncome1;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxIncome2;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxIncomeEdit;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackNewDischarge;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackQuickList;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackRoute1;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackRouteEdit;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackGetString;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.AppDatabase;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxIncomeR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.RouteR;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends BaseActivity {


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
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(ListActivity.this, 2);
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