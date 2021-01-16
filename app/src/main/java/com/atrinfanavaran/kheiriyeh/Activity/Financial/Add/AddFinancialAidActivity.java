package com.atrinfanavaran.kheiriyeh.Activity.Financial.Add;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.atrinfanavaran.kheiriyeh.Activity.Financial.List.FinancialAidListItemActivity;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.NumberTextWatcherForThousand;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.SearchableField;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.Domian.FinancialAidR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.FinancialServiceTypeR;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class AddFinancialAidActivity extends BaseActivity {


    private Toolbar my_toolbar;

    private TextView title;
    private EditText edt1, edt2, edt3, edt4, edt5;
    private Button saveBtn;
    private TextView titleToolbar;
    private LinearLayout filterIcon, backIcon;
    private SearchableSpinner spin1;

    private int FinancialServiceTypeId = 0;
    private boolean editable = false;
    private FinancialAidR object;

    private String FinancialServiceType;
    private RadioGroup payGroup;
    private RadioButton radio1, radio2;
    private int payType =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_financial_aid);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        getBundle();
        initView();
        NavigationDrawer();
        setvariable();


    }

    private void getBundle() {
        object = (FinancialAidR) getIntent().getSerializableExtra("object");
        editable = getIntent().getBooleanExtra("editable", false);

    }

    private void setvariable() {
        titleToolbar.setText("افزودن کمک نقدی");
        payGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioButton1:
                    payType = 1;
                    break;
                case R.id.radioButton2:
                    payType = 2;
                    break;

            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt1.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا نام را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                } else if (edt2.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا مبلغ را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                } else if (spin1.getSelectedItem().toString().equals("انتخاب کنید")) {
                    Toast.makeText(getActivity(), "لطفا نوع را انتخاب نمائید", Toast.LENGTH_SHORT).show();
                    return;
                }

                FinancialAidR obj = new FinancialAidR();

                obj.financialServiceTypeId = FinancialServiceTypeId;
                obj.financialServiceType = FinancialServiceType;
                obj.price = Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(edt2.getText().toString().trim()));
                obj.name = edt1.getText().toString();
                obj.payType = payType;


                if (editable) {
                    obj.id = object.getId();
                    db().FinancialAidDao().update(obj.name, obj.price, obj.financialServiceTypeId, obj.id,  obj.payType);
                    Toast.makeText(AddFinancialAidActivity.this, "عملیات ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                } else {
                    obj.isNew = "true";
                    db().FinancialAidDao().insertBox(obj);
                    Toast.makeText(AddFinancialAidActivity.this, "عملیات ذخیره با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(AddFinancialAidActivity.this, FinancialAidListItemActivity.class));
                finish();
            }
        });

        if (object != null && editable) {
            edt1.setText(object.getName());
            edt2.setText("" + object.getPrice());
            if (object.getPayType() == 1) {
                radio1.setChecked(true);
            } else if (object.getPayType() == 2) {
                radio2.setChecked(true);
            }
        }
        edt2.addTextChangedListener(new NumberTextWatcherForThousand(edt2));
        filterIcon.setVisibility(View.GONE);
        backIcon.setVisibility(View.VISIBLE);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //************************************
        List<FinancialServiceTypeR> object = new ArrayList<>();
        object.add(new FinancialServiceTypeR(0, "انتخاب کنید"));
        object.addAll(db().FinancialServiceTypeDao().getAll());
        SearchableField.setSpinner(spin1, object);

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FinancialServiceTypeId = ((FinancialServiceTypeR) spin1.getSelectedItem()).getId();
                FinancialServiceType = ((FinancialServiceTypeR) spin1.getSelectedItem()).getTitle();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (editable) {
            try {
                for (int i = 0; i < object.size(); i++) {
                    if (object.get(i).getId() == this.object.financialServiceTypeId) {
                        spin1.setSelection(i);
                        break;
                    }
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "خطا در بازخانی لیست خدمت", Toast.LENGTH_SHORT).show();
            }
        }


    }


    private void initView() {

        my_toolbar = findViewById(R.id.toolbar);
        edt1 = findViewById(R.id.edt1_1);
        edt2 = findViewById(R.id.edt1_2);

        saveBtn = findViewById(R.id.btn_1);
        titleToolbar = findViewById(R.id.titleToolbar);
        filterIcon = findViewById(R.id.filterButton);
        backIcon = findViewById(R.id.backButton);
        spin1 = findViewById(R.id.spinner1);
        payGroup = findViewById(R.id.radioButtonGroup);
        radio1 = findViewById(R.id.radioButton1);
        radio2 = findViewById(R.id.radioButton2);
    }

    private void NavigationDrawer() {

        NavigationDrawerFragment my_nav = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        my_nav.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), my_toolbar);

    }

}