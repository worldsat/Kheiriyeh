package com.atrinfanavaran.kheiriyeh.Activity.Flower.Add;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.atrinfanavaran.kheiriyeh.Activity.Flower.List.DonatorListItemActivity;
import com.atrinfanavaran.kheiriyeh.Activity.Flower.List.TajGolListItemActivity;
import com.atrinfanavaran.kheiriyeh.Domain.DonatorApi;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackOperation;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.Domian.DonatorR;

import org.json.JSONObject;

import java.util.UUID;

public class AddDonateActivity extends BaseActivity {


    private Toolbar my_toolbar;
    private EditText edt1, edt2, edt3;
    private Button saveBtn;
    private TextView titleToolbar;
    private LinearLayout filterIcon, backIcon;

    private boolean editable = false;
    private DonatorApi.Data object;
    private CheckBox checkBox;
    private String donator, deceasedName, flowerCrownType, ceremonyType, Introduced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donate);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        getBundle();
        initView();
        NavigationDrawer();
        setvariable();


    }

    private void getBundle() {
        object = (DonatorApi.Data) getIntent().getSerializableExtra("object");
        editable = getIntent().getBooleanExtra("editable", false);

    }

    private void setvariable() {
        titleToolbar.setText("افزودن اهدا کننده");
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt1.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا نام و نام خانوادگی را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                } else if (edt2.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا نام مستعار را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                } else if (edt3.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا موبایل را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                }

//                DonatorR donatorR = new DonatorR();
//                donatorR.donatorFullName = edt1.getText().toString();
//                donatorR.donatorAlias = edt2.getText().toString();
//                donatorR.donatorMobile = edt3.getText().toString();
//                if (editable) {
//                    donatorR.id = object.getId();
//
//                    db().DonatorDao().update(donatorR.donatorFullName, donatorR.donatorAlias, donatorR.donatorMobile, donatorR.id);
//                    Toast.makeText(AddDonateActivity.this, "عملیات ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
//                } else {
//                    donatorR.isNew = "true";
//                    donatorR.guidDonator = UUID.randomUUID().toString();
//                    db().DonatorDao().insertBox(donatorR);
//                    Toast.makeText(AddDonateActivity.this, "عملیات ذخیره با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
//                }
//                finish();
//                startActivity(new Intent(AddDonateActivity.this, DonatorListItemActivity.class));

                JSONObject params = new JSONObject();

                try {
                    if (editable) {
                        params.put("id", object.getId());
                    } else {
                        params.put("guidDonator", UUID.randomUUID().toString());
                    }
                    params.put("donatorFullName", edt1.getText().toString());
                    params.put("donatorAlias", edt2.getText().toString());
                    params.put("donatorMobile", edt3.getText().toString());
                    params.put("isSendMessage", checkBox.isChecked());


                } catch (Exception e) {
                    Toast.makeText(AddDonateActivity.this, "خطا در پارامتر های ارسالی اهدا کننده", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (params != null) {
                    Log.i("moh3n", "sendDonator: " + params.toString());
                }

                MaterialDialog wait = alertWaiting2(getActivity(), "در حال ارسال  اطلاعات اهدا کننده...");
                wait.show();
                controller().Operation("", DonatorApi.class, getActivity(), params.toString(), new CallbackOperation() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("moh3n", "sendDonator: " + result);
                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

                        wait.dismiss();
                        finish();
                        startActivity(new Intent(AddDonateActivity.this, DonatorListItemActivity.class));
                    }

                    @Override
                    public void onError(String error) {
                        Log.i("moh3n", "onError: " + error);
                        wait.dismiss();
                        Toast.makeText(getActivity(), "خطا در ارسال اطلاعات", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        if (object != null && editable) {
            edt1.setText(object.getDonatorFullName());
            edt2.setText(object.getDonatorAlias());
            edt3.setText("" + object.getDonatorMobile());
            checkBox.setChecked(object.isIsSendMessage());
        }
        filterIcon.setVisibility(View.GONE);
        backIcon.setVisibility(View.VISIBLE);
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
        checkBox = findViewById(R.id.checkBox);
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