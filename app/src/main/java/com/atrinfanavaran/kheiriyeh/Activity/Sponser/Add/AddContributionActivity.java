package com.atrinfanavaran.kheiriyeh.Activity.Sponser.Add;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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

import com.atrinfanavaran.kheiriyeh.Activity.Sponser.List.ContributionListItemActivity;
import com.atrinfanavaran.kheiriyeh.Activity.pos.MyBroadCast;
import com.atrinfanavaran.kheiriyeh.Activity.pos.TAGS;
import com.atrinfanavaran.kheiriyeh.Activity.pos.TransactionType;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.NumberTextWatcherForThousand;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.SearchableField;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.Domian.ContributionR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.SponsorR;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class AddContributionActivity extends BaseActivity {


    private Toolbar my_toolbar;

    private TextView title;
    private EditText edt1, edt2, edt3, edt4, edt5;
    private Button saveBtn;
    private TextView titleToolbar;
    private LinearLayout filterIcon, backIcon;
    private SearchableSpinner spin1;
    public static int iTotalPay = 0;
    private int ceremonyTypeId = 0, SponsorId = 0, donatorId = 0, DeceasedNameId = 0, IntroducedId = 0;
    private boolean editable = false;
    private ContributionR object;

    private String donator, deceasedName, flowerCrownType, ceremonyType, Introduced;
    private RadioGroup payGroup;
    private RadioButton radio1, radio2;
    private int payType = 1;
    private MyBroadCast myBroadCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contributaion);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.pec.ThirdCompany");
        myBroadCast = new MyBroadCast();
        registerReceiver(myBroadCast, intentFilter);

        getBundle();
        initView();
        NavigationDrawer();
        setvariable();


    }

    private void getBundle() {
        object = (ContributionR) getIntent().getSerializableExtra("object");
        editable = getIntent().getBooleanExtra("editable", false);

    }

    private void setvariable() {
        titleToolbar.setText("افزودن مشارکت");
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
        edt1.addTextChangedListener(new NumberTextWatcherForThousand(edt1));
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt1.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا مبلغ را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
//                } else if (edt2.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(getActivity(), "لطفا توضیحات را وارد نمائید", Toast.LENGTH_SHORT).show();
//                    return;
                } else if (spin1.getSelectedItem().toString().equals("انتخاب کنید")) {
                    Toast.makeText(getActivity(), "لطفا حامی را انتخاب نمائید", Toast.LENGTH_SHORT).show();
                    return;
                } else if (payType == 2) {
//                    if (edt3.getText().toString().trim().isEmpty()) {
//                        Toast.makeText(getActivity(), "لطفا کد دستگاه را وارد نمائید", Toast.LENGTH_SHORT).show();
//                        return;
//                    } else if (edt4.getText().toString().trim().isEmpty()) {
//                        Toast.makeText(getActivity(), "لطفا کد ترمینال را وارد نمائید", Toast.LENGTH_SHORT).show();
//                        return;
//                    } else if (edt5.getText().toString().trim().isEmpty()) {
//                        Toast.makeText(getActivity(), "لطفا کد دریافتی را وارد نمائید", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                }

                ContributionR obj = new ContributionR();

                obj.SponsorId = SponsorId;
                obj.price = Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(edt1.getText().toString()));
                obj.description = edt2.getText().toString();
//                obj.deviceCode = Integer.parseInt(edt3.getText().toString());
//                obj.terminalCode = Integer.parseInt(edt4.getText().toString());
//                obj.recieverCode = Integer.parseInt(edt5.getText().toString());

                SponsorR sponsorR = db().SponsorDao().getSponsorById(SponsorId);
                obj.fullName = sponsorR.getFullName();
                obj.code = sponsorR.getCode();
                obj.nationalcode = sponsorR.getNationalcode();
                obj.mobile = sponsorR.getMobile();
                obj.phone = sponsorR.getPhone();
                obj.address = sponsorR.getAddress();
                obj.birthDate = sponsorR.getBirthDate();
                obj.payType = payType;


                if (payType == 1) {
                    if (editable) {
                        obj.id = object.getId();
                        db().ContributaionDao().update(obj.price, obj.description, obj.deviceCode, obj.terminalCode, obj.recieverCode, obj.fullName, obj.code,
                                obj.nationalcode, obj.mobile, obj.phone, obj.address, obj.birthDate, obj.id, obj.payType);
                        Toast.makeText(AddContributionActivity.this, "عملیات ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                    } else {
                        obj.isNew = "true";
                        db().ContributaionDao().insertBox(obj);
                        Toast.makeText(AddContributionActivity.this, "عملیات ذخیره با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                    }

                    finish();
                    startActivity(new Intent(AddContributionActivity.this, ContributionListItemActivity.class));
                } else if (payType == 2) {
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("POS", 0);
                    sp.edit().putInt("SponsorId", obj.SponsorId).apply();
                    sp.edit().putInt("price", obj.price).apply();
                    sp.edit().putString("description", obj.description).apply();
                    sp.edit().putString("fullName", obj.fullName).apply();
                    sp.edit().putString("code", obj.code).apply();
                    sp.edit().putString("nationalcode", obj.nationalcode).apply();
                    sp.edit().putString("mobile", obj.mobile).apply();
                    sp.edit().putString("phone", obj.phone).apply();
                    sp.edit().putString("address", obj.address).apply();
                    sp.edit().putString("birthDate", obj.birthDate).apply();
                    sp.edit().putInt("payType", obj.payType).apply();
                    sp.edit().putInt("id", obj.id).apply();
                    sp.edit().putBoolean("AddContributionActivity", true).apply();
                    sp.edit().putBoolean("editable", editable).apply();


                    iTotalPay = obj.price;
                    Intent i = new Intent(TAGS.Action);
                    i.putExtra(TransactionType.transactionType, TransactionType.Sale);
                    i.putExtra(TAGS.CompanyName, obj.fullName);
                    i.putExtra(TAGS.AM, String.valueOf(iTotalPay));
                    i.putExtra("paymentType", "CARD");
                    startActivity(i);
                }
            }
        });

        if (object != null && editable) {
            edt1.setText("" + object.getPrice());
            edt2.setText(object.getDescription());
            edt3.setText("" + object.getDeviceCode());
            edt4.setText("" + object.getTerminalCode());
            edt5.setText("" + object.getRecieverCode());
            if (object.getPayType() == 1) {
                radio1.setChecked(true);
            } else if (object.getPayType() == 2) {
                radio2.setChecked(true);
            }
        }
        filterIcon.setVisibility(View.GONE);
        backIcon.setVisibility(View.VISIBLE);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //************************************
        List<SponsorR> object = new ArrayList<>();
        object.add(new SponsorR(0, "انتخاب کنید"));
        object.addAll(db().SponsorDao().getAll());
        SearchableField.setSpinner(spin1, object);

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SponsorId = ((SponsorR) spin1.getSelectedItem()).getId();
//                flowerCrownType = ((SponsorR) spin1.getSelectedItem()).getTitle();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (editable) {
            try {
                for (int i = 0; i < object.size(); i++) {
                    if (object.get(i).getId() == this.object.getSponsorId()) {
                        spin1.setSelection(i);
                        break;
                    }
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "خطا در بازخانی لیست حامی", Toast.LENGTH_SHORT).show();
            }
        }


    }


    private void initView() {

        my_toolbar = findViewById(R.id.toolbar);
        edt1 = findViewById(R.id.edt1_1);
        edt2 = findViewById(R.id.edt1_2);
        edt3 = findViewById(R.id.edt1_3);
        edt4 = findViewById(R.id.edt1_4);
        edt5 = findViewById(R.id.edt1_5);
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadCast);
    }
}