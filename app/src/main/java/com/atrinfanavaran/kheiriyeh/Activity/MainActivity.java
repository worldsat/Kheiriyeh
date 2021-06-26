package com.atrinfanavaran.kheiriyeh.Activity;

import android.Manifest;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.atrinfanavaran.kheiriyeh.Activity.Financial.List.FinancialAidListItemActivity;
import com.atrinfanavaran.kheiriyeh.Activity.Flower.FlowerCrownListActivity;
import com.atrinfanavaran.kheiriyeh.Activity.SandoghSadaghat.SandoghListActivity;
import com.atrinfanavaran.kheiriyeh.Activity.Sponser.SponsorListActivity;
import com.atrinfanavaran.kheiriyeh.Activity.pos.MyBroadCast;
import com.atrinfanavaran.kheiriyeh.Domain.AndroidVersion;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Controller;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackGet;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Module.SnakBar.SnakBar;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.DownloadFileUrl;
import com.atrinfanavaran.kheiriyeh.Kernel.Interface.OnFinishedDownloadCallback;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.appcompat.widget.Toolbar;

import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends BaseActivity implements onCallBackBoxIncome1, onCallBackBoxIncome2, onCallBackQuickList, onCallBackRouteEdit, onCallBackBoxIncomeEdit, onCallBackBoxEdit, onCallBackRoute1, onCallBackAddRouteNew, onCallBackNewDischarge, onCallBackAddBoxNew, onCallBackAddBox, onCallBackAddBox2 {

    private static final int Time_Between_Two_Back = 2000;
    private long TimeBackPressed;
    private BottomNavigationView bottomNavigation;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private Toolbar my_toolbar;
    private AppDatabase db;
    private ImageView imageView;
    private LinearLayout filterIcon;
    private String sandogh;

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
        getSetting();
//        setFilter();
        checkVersion();
        getSandogh();
    }

    private void getSandogh() {
        sandogh = getIntent().getStringExtra("page");
        if (sandogh == null) return;
        switch (sandogh) {
            case "sandogh1": {
                fragment = new BoxIncomeListFragment();
                setFragment();
                break;
            }
            case "sandogh2": {
                fragment = new BoxListFragment();
                setFragment();
                break;
            }
            case "sandogh3": {
                fragment = new MapFragment();
                setFragment();
                break;
            }
            case "sandogh4": {
                fragment = new RouteListFragment();
                setFragment();
                break;
            }
        }

    }

    private void checkVersion() {

        SettingsBll settingsBll = new SettingsBll(this);
        String Url = settingsBll.getUrlAddress();
        Controller controller = new Controller(this);
        controller.GetFromApi2("/api/AndroidVersion", new CallbackGetString() {
            @Override
            public void onSuccess(String resultStr) {
                try {
                    Log.i("moh3n", "version: " + resultStr);
                    Gson gson = new Gson();
                    AndroidVersion androidVersion = gson.fromJson(resultStr, AndroidVersion.class);
//                    alertQuestion(MainActivity.this, "link", false);

                    int lastVerisionCode = Integer.parseInt(androidVersion.getData().getCurrVersion());
                    String link = Url + "/" + androidVersion.getData().getAppAndroidUrl();

                    PackageInfo phoneVersion = MainActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0);

                    if (phoneVersion.versionCode < lastVerisionCode) {
                        if (androidVersion.getData().isIsMandatory()) {
                            alertQuestion(MainActivity.this, link, true);
                        } else {
                            alertQuestion(MainActivity.this, link, false);
                        }
                    }

                    Log.i("moh3n", phoneVersion.versionCode + " " + lastVerisionCode);

                } catch (Exception e) {
                    Log.i("moh3n", "error versionConde:" + e);
                }

            }

            @Override
            public void onError(String error) {

            }
        });
        controller.Get(AndroidVersion.class, null, 0, 0, true, new CallbackGet() {
            @Override
            public <T> void onSuccess(ArrayList<T> result, int count) {

            }

            @Override
            public void onError(String error) {

            }
        });

    }

    private void alertQuestion(final Context context, String link, boolean forcible) {
        final MaterialDialog question_dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.alert_warning_update, false)
                .autoDismiss(false)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .backgroundColor(Color.parseColor("#01000000"))
                .show();

        TextView ok_btn = (TextView) question_dialog.findViewById(R.id.ok);
        TextView cancel_btn = (TextView) question_dialog.findViewById(R.id.cancel);
        final TextView warningTxt = (TextView) question_dialog.findViewById(R.id.warning_alert);


        warningTxt.setText("نسخه جدید از نرم افزار موجود است،آیا مایل به بروزرسانی میباشید؟");

        ok_btn.setOnClickListener(v -> {
            question_dialog.dismiss();
            String Address = link;
            Log.i("moh3n", "onClick: " + Address);
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(Address));
//            startActivity(i);
            new DownloadFileUrl(context, Address, "ghasedak.apk", null, forcible, new OnFinishedDownloadCallback() {
                @Override
                public void onFinish() {

                }

                @Override
                public void onCancel() {
                    SnakBar snakBar = new SnakBar();
                    snakBar.snakShow(context, "دریافت فایل لغو شد");

                }
            });

        });
        cancel_btn.setOnClickListener(v -> {
            if (forcible) {
                finishAffinity();

            } else {
                question_dialog.dismiss();
            }
        });

    }

    private void setFilter() {
        filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
            final View iconView = menuView.getChildAt(i).findViewById(R.id.icon);
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
        filterIcon = findViewById(R.id.filterButton);
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
        if (sandogh != null) {
          finish();
        }
        if (hm != null) {
            if (hm.isVisible()) {
                if (fm.getBackStackEntryCount() > 1) {
                    fm.popBackStack();
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

        boxIncomeR.lat = boxIncome.getlat();
        boxIncomeR.lon = boxIncome.getlon();
        boxIncomeR.number = boxIncome.getnumber();
        boxIncomeR.price = boxIncome.getprice();
        boxIncomeR.assignmentDate = boxIncome.getassignmentDate();
        boxIncomeR.assignmentDateEn = boxIncome.getassignmentDateEn();
        boxIncomeR.status = boxIncome.getstatus();
        boxIncomeR.guidBox = boxIncome.getGuidBox();

        if (editable) {
            db.BoxIncomeDao().update(boxIncome.getlat(), boxIncome.getlon(), boxIncome.getnumber()
                    , boxIncome.getprice(), boxIncome.getassignmentDate(), boxIncome.getassignmentDateEn(), boxIncome.getstatus(), boxIncome.getid(), boxIncome.getGuidBox()
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
        fragment.getFragmentManager().popBackStack();
        fragment = new BoxIncomeListFragment();

        setFragment();
    }


    @Override
    public void SaveBoxIncome1(BoxIncome boxIncome, boolean editable) {
        fragment.getFragmentManager().popBackStack();
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

        fragment.getFragmentManager().popBackStack();
        fragment = new MapBoxFragment();
        Bundle bundle1 = new Bundle();
        Box box = new Box();
        box.setId(boxR.id);
        box.setNumber(boxR.number);
        box.setMobile(boxR.mobile);
        box.setFullName(boxR.fullName);
        box.setassignmentDate(boxR.assignmentDate);
        box.setCode(boxR.code);
        box.setAddress(boxR.address);
        box.setDischargeRouteId(boxR.dischargeRouteId);
        box.setguidDischargeRoute(boxR.guidDischargeRoute);
        box.setguidBox(boxR.guidBox);
        box.setBoxId(boxR.boxId);
        box.setday(boxR.day);
        box.setboxKind(boxR.boxKind);

        bundle1.putSerializable("Box", box);
        bundle1.putBoolean("editable", editable);
        fragment.setArguments(bundle1);

        setFragment();
    }

    @Override
    public void SaveBox2(BoxR boxR, boolean editable) {
        if (editable) {
            db.BoxDao().update(boxR.fullName, boxR.day, boxR.number, boxR.mobile, boxR.code, boxR.assignmentDate, boxR.id, boxR.address, boxR.lat, boxR.lon, boxR.guidDischargeRoute, boxR.boxKind, boxR.dischargeRouteId);
            Toast.makeText(this, "عملیات ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
        } else {

            db.BoxDao().insertBox(boxR);
            Toast.makeText(this, "عملیات ذخیره با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
        }
        fragment.getFragmentManager().popBackStack();
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
        if (editable) {
            db.RouteDao().update(router.code, router.address, router.id, router.isNew);
            Toast.makeText(this, "عملیات ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
        } else {
            db.RouteDao().insertBox(router);
            Toast.makeText(this, "عملیات ذخیره با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
        }
        fragment.getFragmentManager().popBackStack();
        fragment = new RouteListFragment();
        setFragment();
    }


    @Override
    public void EditBoxIncome(BoxIncomeR boxIncome) {
        fragment = new AddBoxIncomeFragment1();

        BoxIncome boxIncome1 = new BoxIncome();
        boxIncome1.setid(boxIncome.id);
        boxIncome1.setlat(boxIncome.lat);
        boxIncome1.setlon(boxIncome.lon);
        boxIncome1.setnumber(boxIncome.number);
        boxIncome1.setprice(boxIncome.price);
        boxIncome1.setassignmentDate(boxIncome.assignmentDate);
        boxIncome1.setstatus(boxIncome.status);
        boxIncome1.setGuidBox(boxIncome.guidBox);

        Bundle bundle = new Bundle();
        bundle.putSerializable("BoxIncome", boxIncome1);
        bundle.putBoolean("editable", true);
        fragment.setArguments(bundle);

        setFragment();
    }


    private void setFragment() {
        try {
            bottomNavigation.getMenu().getItem(0).setCheckable(true);
            bottomNavigation.getMenu().getItem(1).setCheckable(true);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment, "subPage").addToBackStack(null).commit();

        } catch (Exception e) {

        }

    }

    @Override
    public void EditBox(BoxR boxR) {
        fragment = new AddBoxFragment();

        Box box = new Box();
        box.setassignmentDate(boxR.assignmentDate);
        box.setCode(boxR.code);
        box.setFullName(boxR.fullName);
        box.setMobile(boxR.mobile);
        box.setNumber(boxR.number);
        box.setId(boxR.id);
        box.setAddress(boxR.address);
        box.setDischargeRouteId(boxR.dischargeRouteId);
        box.setguidDischargeRoute(boxR.guidDischargeRoute);
        box.setBoxId(boxR.boxId);
        box.setguidBox(boxR.guidBox);
        box.setboxKind(boxR.boxKind);
        box.setday(boxR.day);

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
//        route.setday(routerR.day);
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
//                fragment = new BoxIncomeListFragment();
//                setFragment();
                Intent intent = new Intent(MainActivity.this, SandoghListActivity.class);
                startActivity(intent);
                break;
            }
//            case "1": {
//                fragment = new BoxListFragment();
//                setFragment();
//                break;
//            }
//            case "2": {
//                fragment = new MapFragment();
//                setFragment();
//                break;
//            }
//            case "3": {
//                fragment = new RouteListFragment();
//                setFragment();
//                break;
//            }
            case "1": {
                Intent intent = new Intent(MainActivity.this, FlowerCrownListActivity.class);
                startActivity(intent);
                break;
            }
            case "2": {
                Intent intent = new Intent(MainActivity.this, SponsorListActivity.class);
                startActivity(intent);
                break;
            }
            case "3": {
                Intent intent = new Intent(MainActivity.this, FinancialAidListItemActivity.class);
                startActivity(intent);
                break;
            }

        }

    }


    private void RunPermissionDownload() {

        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_NETWORK_STATE
                        , Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.REQUEST_INSTALL_PACKAGES
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
                , Manifest.permission.READ_EXTERNAL_STORAGE
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

    private void getSetting() {

        controller().GetFromApi2("api/Setting", new CallbackGetString() {
            @Override
            public void onSuccess(String resultStr) {
                Gson gson = new Gson();

                try {
                    String str = new JSONObject(resultStr).getString("data");
                    Setting response = gson.fromJson(str, Setting.class);
                    if (response.getid() == null) return;

                    SettingsBll settingsBll = new SettingsBll(getActivity());
                    settingsBll.setLogoAddress(response.getlogoName());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });

    }

}