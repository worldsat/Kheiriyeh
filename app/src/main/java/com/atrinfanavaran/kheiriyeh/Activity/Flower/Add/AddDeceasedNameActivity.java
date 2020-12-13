package com.atrinfanavaran.kheiriyeh.Activity.Flower.Add;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.atrinfanavaran.kheiriyeh.Activity.Flower.List.DeceasedNameListItemActivity;
import com.atrinfanavaran.kheiriyeh.Activity.Flower.List.DonatorListItemActivity;
import com.atrinfanavaran.kheiriyeh.Activity.Flower.List.TajGolListItemActivity;
import com.atrinfanavaran.kheiriyeh.Domain.DeceasedNameApi;
import com.atrinfanavaran.kheiriyeh.Domain.DonatorApi;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackOperation;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.SearchableField;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.Domian.DeceasedNameR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.DeceasedNameR2;
import com.atrinfanavaran.kheiriyeh.Room.Domian.FlowerCrownR;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class AddDeceasedNameActivity extends BaseActivity {


    private Toolbar my_toolbar;

    private TextView title;
    private EditText edt1, edt2;
    private Button saveBtn;
    private TextView titleToolbar;
    private LinearLayout filterIcon, backIcon;
    private SearchableSpinner spin1;

    private boolean deceasedSex = true;
    private boolean editable = false;
    private DeceasedNameApi.Data object;

    private String deceasedSexStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deceased_name);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        getBundle();
        initView();
        NavigationDrawer();
        setvariable();


    }

    private void getBundle() {
        object = (DeceasedNameApi.Data) getIntent().getSerializableExtra("object");
        editable = getIntent().getBooleanExtra("editable", false);

    }

    private void setvariable() {
        titleToolbar.setText("افزودن متوفی");
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt1.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا نام و نام خانوادگی را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                } else if (edt2.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا نام مستعار را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;

                } else if (spin1.getSelectedItem().toString().equals("انتخاب کنید")) {
                    Toast.makeText(getActivity(), "لطفا جنسیت را انتخاب نمائید", Toast.LENGTH_SHORT).show();
                    return;
                }

//                DeceasedNameR deceasedNameR = new DeceasedNameR();
//                deceasedNameR.setDeceasedSex(deceasedSex);
//                deceasedNameR.setDeceasedFullName(edt1.getText().toString());
//                deceasedNameR.deceaseAalias = edt2.getText().toString();
//                if (editable) {
//                    deceasedNameR.id = object.getId();
////                    db().DeceasedNameDao().update(deceasedNameR.deceasedSex, deceasedNameR.deceasedFullName, deceasedNameR.deceaseAalias, deceasedNameR.id);
//                    Toast.makeText(AddDeceasedNameActivity.this, "عملیات ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
//                } else {
//                    deceasedNameR.isNew = "true";
//                    deceasedNameR.guidDeceasedName = UUID.randomUUID().toString();
////                    db().DeceasedNameDao().insertBox(deceasedNameR);
//                    Toast.makeText(AddDeceasedNameActivity.this, "عملیات ذخیره با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
//                }
//                finish();
//                startActivity(new Intent(AddDeceasedNameActivity.this, DeceasedNameListItemActivity.class));


                JSONObject params = new JSONObject();

                try {
                    if (editable) {
                        params.put("id", object.getId());
                    }else{
                        params.put("guidDeceasedName", UUID.randomUUID().toString());
                    }
                    params.put("deceasedFullName", edt1.getText().toString());
                    params.put("deceaseAalias", edt2.getText().toString());
                    params.put("deceasedSex",deceasedSex);



                } catch (Exception e) {
                    Toast.makeText(AddDeceasedNameActivity.this, "خطا در پارامتر های ارسالی", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (params != null) {
                    Log.i("moh3n", "sendDeceasedName: " + params.toString());
                }

                MaterialDialog wait = alertWaiting2(getActivity(), "در حال ارسال ...");
                wait.show();
                controller().Operation("", DeceasedNameApi.class, getActivity(), params.toString(), new CallbackOperation() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("moh3n", "sendDeceasedName: " + result);
                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

                        wait.dismiss();
                        finish();
                        startActivity(new Intent(AddDeceasedNameActivity.this, DeceasedNameListItemActivity.class));
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
            edt1.setText(object.getDeceasedFullName());
            edt2.setText(object.getDeceaseAalias());
        }

        filterIcon.setVisibility(View.GONE);
        backIcon.setVisibility(View.VISIBLE);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //************************************************************************
        ArrayList<DeceasedNameR2> DeceasedNameItems = new ArrayList<>();
        DeceasedNameItems.add(new DeceasedNameR2(false, "مرد"));
        DeceasedNameItems.add(new DeceasedNameR2(true, "زن"));

        SearchableField.setSpinner(spin1, DeceasedNameItems);

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deceasedSex = ((DeceasedNameR2) spin1.getSelectedItem()).deceasedSex;
                deceasedSexStr = ((DeceasedNameR2) spin1.getSelectedItem()).getDeceasedSexStr();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (editable) {
            try {
                for (int i = 0; i < DeceasedNameItems.size(); i++) {
                    if (DeceasedNameItems.get(i).isDeceasedSex() == object.isDeceasedSex()) {
                        spin1.setSelection(i);
                        break;
                    }
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "خطا در بازخانی نوع جنسبت", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void initView() {

        my_toolbar = findViewById(R.id.toolbar);
        edt1 = findViewById(R.id.edt1_1);
        edt2 = findViewById(R.id.edt1_2);
        spin1 = findViewById(R.id.spinner1);

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