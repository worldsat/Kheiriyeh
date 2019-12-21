package com.atrinfanavaran.kheiriyeh.Activity;

import android.os.Bundle;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.atrinfanavaran.kheiriyeh.Fragment.ContractFeaturePersoanTaskFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.DischargeFragment1;
import com.atrinfanavaran.kheiriyeh.Fragment.DischargeFragment2;
import com.atrinfanavaran.kheiriyeh.Fragment.FirstFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.MapFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackFragment1;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackFragment2;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackFragment3;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.R;

public class MainActivity extends BaseActivity implements onCallBackFragment1, onCallBackFragment2 {


    private BottomNavigationView bottomNavigation;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private Toolbar my_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        initView();
        BottomNavigation();
        NavigationDrawer();
    }


    private void BottomNavigation() {

        bottomNavigation.setSelected(true);
        fragmentManager = getSupportFragmentManager();

        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.action_main:

                    fragment = new FirstFragment();
                    //off or on selected button color
                    bottomNavigation.getMenu().getItem(0).setCheckable(true);
                    bottomNavigation.getMenu().getItem(1).setCheckable(true);

                    break;
                case R.id.action_export:
                    fragment = new DischargeFragment1();
                    bottomNavigation.getMenu().getItem(0).setCheckable(true);
                    bottomNavigation.getMenu().getItem(1).setCheckable(true);

                    break;
                case R.id.action_add:
                    fragment = new ContractFeaturePersoanTaskFragment();

                    bottomNavigation.getMenu().getItem(0).setCheckable(true);
                    bottomNavigation.getMenu().getItem(1).setCheckable(true);
                    break;
                case R.id.action_address:
                    fragment = new MapFragment();

                    bottomNavigation.getMenu().getItem(0).setCheckable(true);
                    bottomNavigation.getMenu().getItem(1).setCheckable(true);
                    break;
                case R.id.action_addAdress:
                    fragment = new ContractFeaturePersoanTaskFragment();

                    bottomNavigation.getMenu().getItem(0).setCheckable(true);
                    bottomNavigation.getMenu().getItem(1).setCheckable(true);
                    break;
            }
            bottomNavigation.getMenu().getItem(0).setCheckable(true);
            bottomNavigation.getMenu().getItem(1).setCheckable(true);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment).commit();
            return true;
        });



        fragment = new FirstFragment();

        bottomNavigation.getMenu().getItem(0).setCheckable(false);
        bottomNavigation.getMenu().getItem(1).setCheckable(false);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();

        //end set default


        //set Icon size
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 23, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 23, displayMetrics);
            iconView.setLayoutParams(layoutParams);

        }
    }


    private void initView() {
        bottomNavigation = findViewById(R.id.bottomnav);
        my_toolbar = findViewById(R.id.toolbar);
    }
    private void NavigationDrawer() {

        NavigationDrawerFragment my_nav = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        my_nav.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), my_toolbar);

    }



    @Override
    public void Save1() {
        fragment = new DischargeFragment2();
        bottomNavigation.getMenu().getItem(0).setCheckable(true);
        bottomNavigation.getMenu().getItem(1).setCheckable(true);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();
    }

    @Override
    public void Save2() {

    }


}