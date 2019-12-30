package com.atrinfanavaran.kheiriyeh.Activity;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.atrinfanavaran.kheiriyeh.Domain.Box;
import com.atrinfanavaran.kheiriyeh.Domain.BoxIncome;
import com.atrinfanavaran.kheiriyeh.Domain.Route;
import com.atrinfanavaran.kheiriyeh.Fragment.AddBoxFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.AddBoxIncomeFragment1;
import com.atrinfanavaran.kheiriyeh.Fragment.AddBoxIncomeFragment2;
import com.atrinfanavaran.kheiriyeh.Fragment.AddRouteFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.BoxIncomeListFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.BoxListFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.FirstFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.MapFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.MapRouteFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Fragment.RouteListFragment;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackAddBox;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackAddBoxNew;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackAddRouteNew;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxEdit;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxIncome1;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxIncome2;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxIncomeEdit;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackNewDischarge;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackQuickList;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackRoute1;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackRoute2;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackRouteEdit;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.AppDatabase;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxIncomeR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.RouteR;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends BaseActivity implements onCallBackBoxIncome1, onCallBackBoxIncome2, onCallBackQuickList, onCallBackRouteEdit, onCallBackBoxIncomeEdit, onCallBackBoxEdit, onCallBackRoute1, onCallBackRoute2, onCallBackAddRouteNew, onCallBackNewDischarge, onCallBackAddBoxNew, onCallBackAddBox {

    private static final int Time_Between_Two_Back = 2000;
    private long TimeBackPressed;
    private BottomNavigationView bottomNavigation;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private Toolbar my_toolbar;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "RoomDb").fallbackToDestructiveMigration().allowMainThreadQueries().build();

//        getApplicationContext().deleteDatabase("RoomDb");
        RunPermissionDownload();
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
//                    fragment = new AddBoxIncomeFragment1();
                    fragment = new BoxIncomeListFragment();
                    bottomNavigation.getMenu().getItem(0).setCheckable(true);
                    bottomNavigation.getMenu().getItem(1).setCheckable(true);

                    break;
                case R.id.action_add:
                    fragment = new BoxListFragment();

                    bottomNavigation.getMenu().getItem(0).setCheckable(true);
                    bottomNavigation.getMenu().getItem(1).setCheckable(true);
                    break;
                case R.id.action_address:
                    fragment = new MapFragment();

                    bottomNavigation.getMenu().getItem(0).setCheckable(true);
                    bottomNavigation.getMenu().getItem(1).setCheckable(true);
                    break;
                case R.id.action_addAdress:
                    fragment = new RouteListFragment();

                    bottomNavigation.getMenu().getItem(0).setCheckable(true);
                    bottomNavigation.getMenu().getItem(1).setCheckable(true);
                    break;
            }
            bottomNavigation.getMenu().getItem(0).setCheckable(true);
            bottomNavigation.getMenu().getItem(1).setCheckable(true);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment, "mainPage").addToBackStack(null).commit();
            return true;
        });


        fragment = new FirstFragment();

        bottomNavigation.getMenu().getItem(0).setCheckable(false);
        bottomNavigation.getMenu().getItem(1).setCheckable(false);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment, "mainPage").addToBackStack(null).commit();

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
    public void onBackPressed() {

        FragmentManager fm = getSupportFragmentManager();
        Fragment hm = getSupportFragmentManager().findFragmentByTag("subPage");

        if (hm != null) {
            if (hm.isVisible()) {
                if (fm.getBackStackEntryCount() > 1) {
                    fm.popBackStack();
                }
            }
        } else {
            if (TimeBackPressed + Time_Between_Two_Back > System.currentTimeMillis()) {
                finishAffinity();
                return;
            } else {
                Toast.makeText(MainActivity.this, "به منظور خروج دوباره کلیک کنید", Toast.LENGTH_SHORT).show();
            }
            TimeBackPressed = System.currentTimeMillis();
        }
    }


    @Override
    public void SaveBoxIncome2(BoxIncome boxIncome, boolean editable) {
//        Toast.makeText(this, boxIncome.getlat(), Toast.LENGTH_SHORT).show();
        BoxIncomeR boxIncomeR = new BoxIncomeR();
        boxIncomeR.factorNumber = boxIncome.getfactorNumber();
        boxIncomeR.lat = boxIncome.getlat();
        boxIncomeR.lon = boxIncome.getlon();
        boxIncomeR.number = boxIncome.getnumber();
        boxIncomeR.price = boxIncome.getprice();
        boxIncomeR.registerDate = boxIncome.getregisterDate();
        boxIncomeR.status = boxIncome.getstatus();

        if (editable) {
            db.BoxIncomeDao().update(boxIncome.getfactorNumber(), boxIncome.getlat(), boxIncome.getlon(), boxIncome.getnumber()
                    , boxIncome.getprice(), boxIncome.getregisterDate(), boxIncome.getstatus(), boxIncome.getid()
            );
            Toast.makeText(this, "عملیات ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
        } else {
            db.BoxIncomeDao().insertBoxIncome(boxIncomeR);
            Toast.makeText(this, "عملیات ذخیره با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
        }
        List<BoxIncomeR> boxIncomes = db.BoxIncomeDao().getAll();
        for (int i = 0; i < boxIncomes.size(); i++) {
            Log.i(TAG, "SaveBoxIncome2: " + boxIncomes.get(i).price);
        }
        fragment = new BoxIncomeListFragment();

        setFragment();
    }


    @Override
    public void SaveBoxIncome1(BoxIncome boxIncome, boolean editable) {
        fragment = new AddBoxIncomeFragment2();
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("BoxIncome", boxIncome);
        bundle1.putBoolean("editable", editable);
        fragment.setArguments(bundle1);

        setFragment();
    }

    @Override
    public void btnNewDischarge() {
        fragment = new AddBoxIncomeFragment1();
        setFragment();
    }

    @Override
    public void btnNewBox() {
        fragment = new AddBoxFragment();
        setFragment();
    }

    @Override
    public void SaveBox(BoxR boxR, boolean editable) {
        if (editable) {
            db.BoxDao().update(boxR.fullName, boxR.number, boxR.mobile, boxR.code, boxR.registerDate, boxR.id);
            Toast.makeText(this, "عملیات ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
        } else {
            db.BoxDao().insertBox(boxR);
            Toast.makeText(this, "عملیات ذخیره با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
        }
        fragment = new BoxListFragment();
        setFragment();
    }

    @Override
    public void btnNewRoute() {
        fragment = new AddRouteFragment();
        setFragment();
    }

    @Override
    public void SaveRoute1(RouteR router, boolean editable) {
        fragment = new MapRouteFragment();
        Bundle bundle1 = new Bundle();
        Route route = new Route();
        route.setaddress(router.address);
        route.setcode(router.code);
        route.setday(router.day);
        route.setid(router.id);

        bundle1.putSerializable("Route", route);
        bundle1.putBoolean("editable", editable);
        fragment.setArguments(bundle1);

        setFragment();
    }

    @Override
    public void SaveRoute2(RouteR routeR, boolean editable) {
        if (editable) {
            db.RouteDao().update(routeR.code, routeR.day, routeR.address, routeR.id, routeR.lat, routeR.lon);
            Toast.makeText(this, "عملیات ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
        } else {
            db.RouteDao().insertBox(routeR);
            Toast.makeText(this, "عملیات ذخیره با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
        }

        fragment = new RouteListFragment();
        setFragment();
    }

    @Override
    public void EditBoxIncome(BoxIncomeR boxIncome) {
        fragment = new AddBoxIncomeFragment1();

        BoxIncome boxIncome1 = new BoxIncome();
        boxIncome1.setid(boxIncome.id);
        boxIncome1.setfactorNumber(boxIncome.factorNumber);
        boxIncome1.setlat(boxIncome.lat);
        boxIncome1.setlon(boxIncome.lon);
        boxIncome1.setnumber(boxIncome.number);
        boxIncome1.setprice(boxIncome.price);
        boxIncome1.setregisterDate(boxIncome.registerDate);
        boxIncome1.setstatus(boxIncome.status);

        Bundle bundle = new Bundle();
        bundle.putSerializable("BoxIncome", boxIncome1);
        bundle.putBoolean("editable", true);
        fragment.setArguments(bundle);

        setFragment();
    }


    private void setFragment() {
        bottomNavigation.getMenu().getItem(0).setCheckable(true);
        bottomNavigation.getMenu().getItem(1).setCheckable(true);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment, "subPage").addToBackStack(null).commit();

    }

    @Override
    public void EditBox(BoxR boxR) {
        fragment = new AddBoxFragment();

        Box box = new Box();
        box.setRegisterDate(boxR.registerDate);
        box.setCode(boxR.code);
        box.setFullName(boxR.fullName);
        box.setMobile(boxR.mobile);
        box.setNumber(boxR.number);
        box.setId(boxR.id);

        Bundle bundle = new Bundle();
        bundle.putSerializable("Box", box);
        bundle.putBoolean("editable", true);
        fragment.setArguments(bundle);

        setFragment();
    }

    @Override
    public void EditRoute(RouteR routerR) {
        fragment = new AddRouteFragment();

        Route route = new Route();
        route.setaddress(routerR.address);
        route.setcode(routerR.code);
        route.setday(routerR.day);
        route.setid(routerR.id);
        route.setlat(routerR.lat);
        route.setlon(routerR.lon);

        Bundle bundle = new Bundle();
        bundle.putSerializable("Route", route);
        bundle.putBoolean("editable", true);
        fragment.setArguments(bundle);

        setFragment();
    }

    @Override
    public void goTo(String page) {
        switch (page) {
            case "0": {
                fragment = new BoxIncomeListFragment();
                break;
            }
            case "1": {
                fragment = new BoxListFragment();
                break;
            }
            case "2": {
                fragment = new MapFragment();
                break;
            }
            case "3": {
                fragment = new RouteListFragment();
                break;
            }

        }
        setFragment();
    }


    private void RunPermissionDownload() {

        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_NETWORK_STATE
                        , Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION

                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread().check();

    }

    private void checkRunTimePermission() {
        String[] permissionArrays = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.ACCESS_NETWORK_STATE
                , Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.ACCESS_COARSE_LOCATION
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionArrays, 1);
        } else {

        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    checkRunTimePermission();
                }
                return;
            }
        }
    }


}