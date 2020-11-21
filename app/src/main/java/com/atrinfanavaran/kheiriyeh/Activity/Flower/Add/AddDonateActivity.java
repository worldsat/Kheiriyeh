package com.atrinfanavaran.kheiriyeh.Activity.Flower.Add;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.atrinfanavaran.kheiriyeh.Activity.Flower.List.DonatorListItemActivity;
import com.atrinfanavaran.kheiriyeh.Activity.Flower.List.TajGolListItemActivity;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.Domian.DonatorR;

public class AddDonateActivity extends BaseActivity {


    private Toolbar my_toolbar;
    private EditText edt1, edt2, edt3;
    private Button saveBtn;
    private TextView titleToolbar;
    private LinearLayout filterIcon, backIcon;

    private boolean editable = false;
    private DonatorR object;

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
        object = (DonatorR) getIntent().getSerializableExtra("object");
        editable = getIntent().getBooleanExtra("editable", false);

    }

    private void setvariable() {
        titleToolbar.setText("افزودن اهدا کننده");
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DonatorR donatorR = new DonatorR();
                donatorR.donatorFullName = edt1.getText().toString();
                donatorR.donatorAlias = edt2.getText().toString();
                donatorR.donatorMobile = edt3.getText().toString();
                if (editable) {
                    donatorR.id = object.getId();
                    db().DonatorDao().update(donatorR.donatorFullName, donatorR.donatorAlias, donatorR.donatorMobile, donatorR.id);
                    Toast.makeText(AddDonateActivity.this, "عملیات ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                } else {
                    donatorR.isNew = "true";
                    db().DonatorDao().insertBox(donatorR);
                    Toast.makeText(AddDonateActivity.this, "عملیات ذخیره با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                }
                finish();
                startActivity(new Intent(AddDonateActivity.this, DonatorListItemActivity.class));
            }
        });

        if (object != null && editable) {
            edt1.setText(object.donatorFullName);
            edt2.setText(object.donatorAlias);
            edt3.setText("" + object.donatorMobile);

        }
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