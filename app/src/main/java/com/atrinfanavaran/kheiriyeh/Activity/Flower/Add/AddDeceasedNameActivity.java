package com.atrinfanavaran.kheiriyeh.Activity.Flower.Add;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.atrinfanavaran.kheiriyeh.Activity.Flower.List.DeceasedNameListItemActivity;
import com.atrinfanavaran.kheiriyeh.Activity.Flower.List.TajGolListItemActivity;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.SearchableField;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.Domian.DeceasedNameR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.DeceasedNameR2;
import com.atrinfanavaran.kheiriyeh.Room.Domian.FlowerCrownR;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

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
    private DeceasedNameR object;

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
        object = (DeceasedNameR) getIntent().getSerializableExtra("object");
        editable = getIntent().getBooleanExtra("editable", false);

    }

    private void setvariable() {
        titleToolbar.setText("افزودن متوفی");
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DeceasedNameR deceasedNameR = new DeceasedNameR();
                deceasedNameR.setDeceasedSex(deceasedSex);
                deceasedNameR.setDeceasedFullName(edt1.getText().toString());
                deceasedNameR.deceaseAalias = edt2.getText().toString();
                if (editable) {
                    deceasedNameR.id = object.getId();
                    db().DeceasedNameDao().update(deceasedNameR.deceasedSex, deceasedNameR.deceasedFullName, deceasedNameR.deceaseAalias, deceasedNameR.id);
                    Toast.makeText(AddDeceasedNameActivity.this, "عملیات ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                } else {
                    deceasedNameR.isNew = "true";
                    deceasedNameR.guidDeceasedName = UUID.randomUUID().toString();
                    db().DeceasedNameDao().insertBox(deceasedNameR);
                    Toast.makeText(AddDeceasedNameActivity.this, "عملیات ذخیره با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                }
                finish();
                startActivity(new Intent(AddDeceasedNameActivity.this, DeceasedNameListItemActivity.class));
            }
        });

        if (object != null && editable) {
            edt1.setText(object.deceasedFullName);
            edt2.setText(object.deceaseAalias);
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