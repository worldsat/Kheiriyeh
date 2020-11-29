package com.atrinfanavaran.kheiriyeh.Activity.Financial.Add;

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
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinancialAidR obj = new FinancialAidR();

                obj.financialServiceTypeId = FinancialServiceTypeId;
                obj.financialServiceType = FinancialServiceType;
                obj.price = Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(edt2.getText().toString().trim()));
                obj.name = edt1.getText().toString();


                if (editable) {
                    obj.id = object.getId();
                    db().FinancialAidDao().update(obj.name, obj.price, obj.financialServiceTypeId, obj.id);
                    Toast.makeText(AddFinancialAidActivity.this, "عملیات ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                } else {
                    obj.isNew = "true";
                    db().FinancialAidDao().insertBox(obj);
                    Toast.makeText(AddFinancialAidActivity.this, "عملیات ذخیره با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                }
                finish();
                startActivity(new Intent(AddFinancialAidActivity.this, FinancialAidListItemActivity.class));
            }
        });

        if (object != null && editable) {
            edt1.setText(object.getName());
            edt2.setText("" + object.getPrice());

        }
        edt2.addTextChangedListener(new NumberTextWatcherForThousand(edt2));
        filterIcon.setVisibility(View.GONE);
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

    }

    private void NavigationDrawer() {

        NavigationDrawerFragment my_nav = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        my_nav.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), my_toolbar);

    }

}