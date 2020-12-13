package com.atrinfanavaran.kheiriyeh.Activity.Sponser.Add;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.alirezaafkar.sundatepicker.DatePicker;
import com.alirezaafkar.sundatepicker.components.DateItem;
import com.atrinfanavaran.kheiriyeh.Activity.Flower.Add.AddTajGolActivity;
import com.atrinfanavaran.kheiriyeh.Activity.Flower.List.DonatorListItemActivity;
import com.atrinfanavaran.kheiriyeh.Activity.Sponser.List.SponsorListItemActivity;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.Domian.DonatorR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.SponsorR;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddSponsorActivity extends BaseActivity {


    private Toolbar my_toolbar;
    private EditText edt1, edt2, edt3, edt4, edt5, edt6, edt7;
    private Button saveBtn;
    private TextView titleToolbar;
    private LinearLayout filterIcon, backIcon;

    private boolean editable = false;
    private SponsorR object;
    private ImageView calendarBtn;
    private String donator, deceasedName, flowerCrownType, ceremonyType, Introduced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sponser);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        getBundle();
        initView();
        NavigationDrawer();
        setvariable();


    }

    private void getBundle() {
        object = (SponsorR) getIntent().getSerializableExtra("object");
        editable = getIntent().getBooleanExtra("editable", false);

    }

    private void setvariable() {
        titleToolbar.setText("افزودن حامی");
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt1.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا نام و نام خانوادگی را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                } else if (edt2.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا کد را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                } else if (edt3.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا کد ملی را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                } else if (edt4.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا همراه را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                } else if (edt5.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا تلفن را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                } else if (edt6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا آدرس را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                } else if (edt7.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا تاریخ ثبت را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                }

                SponsorR sponsorR = new SponsorR();
                sponsorR.fullName = edt1.getText().toString();
                sponsorR.code = edt2.getText().toString();
                sponsorR.nationalcode = edt3.getText().toString();
                sponsorR.mobile = edt4.getText().toString();
                sponsorR.phone = edt5.getText().toString();
                sponsorR.address = edt6.getText().toString();
                sponsorR.birthDate = edt7.getText().toString();
                if (editable) {
                    sponsorR.id = object.id;
                    db().SponsorDao().update(sponsorR.fullName, sponsorR.code, sponsorR.nationalcode, sponsorR.mobile
                            , sponsorR.phone, sponsorR.address, sponsorR.birthDate, sponsorR.id);
                    Toast.makeText(AddSponsorActivity.this, "عملیات ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                } else {
                    sponsorR.isNew = "true";
                    db().SponsorDao().insertBox(sponsorR);
                    Toast.makeText(AddSponsorActivity.this, "عملیات ذخیره با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                }
                finish();
                startActivity(new Intent(AddSponsorActivity.this, SponsorListItemActivity.class));
            }
        });

        if (object != null && editable) {
            edt1.setText(object.fullName);
            edt2.setText(object.code);
            edt3.setText("" + object.nationalcode);
            edt4.setText("" + object.mobile);
            edt5.setText("" + object.phone);
            edt6.setText("" + object.address);
            edt7.setText("" + object.birthDate);

        }
        filterIcon.setVisibility(View.GONE);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        calendarBtn.setOnClickListener(v -> {
            DatePicker.Builder builder = new DatePicker
                    .Builder()
                    .theme(R.style.DialogTheme)
                    .future(true);
            Date mDate = new Date();
            builder.date(mDate.getDay(), mDate.getMonth(), mDate.getYear());
            builder.build((id, calendar, day, month, year) -> {
                DateFormat df = new SimpleDateFormat("hh:mm:ss a", Locale.US);
                java.util.Date d = new java.util.Date();
                String dt = df.format(d);

                mDate.setDate(day, month, year);
                edt7.setText(year + "-" + month + "-" + day);

            }).show(getActivity().getSupportFragmentManager(), "");

        });
        edt7.setKeyListener(null);
        edt7.setOnClickListener(v -> {
            DatePicker.Builder builder = new DatePicker
                    .Builder()
                    .theme(R.style.DialogTheme)
                    .future(true);
            Date mDate = new Date();
            builder.date(mDate.getDay(), mDate.getMonth(), mDate.getYear());
            builder.build((id, calendar, day, month, year) -> {
                DateFormat df = new SimpleDateFormat("hh:mm:ss a", Locale.US);
                java.util.Date d = new java.util.Date();
                String dt = df.format(d);

                mDate.setDate(day, month, year);
                edt7.setText(year + "-" + month + "-" + day);

            }).show(getActivity().getSupportFragmentManager(), "");

        });
    }


    private void initView() {

        my_toolbar = findViewById(R.id.toolbar);
        edt1 = findViewById(R.id.edt1_1);
        edt2 = findViewById(R.id.edt1_2);
        edt3 = findViewById(R.id.edt1_3);
        edt4 = findViewById(R.id.edt1_4);
        edt5 = findViewById(R.id.edt1_5);
        edt6 = findViewById(R.id.edt1_6);
        edt7 = findViewById(R.id.edt1_7);

        saveBtn = findViewById(R.id.btn_1);
        titleToolbar = findViewById(R.id.titleToolbar);
        filterIcon = findViewById(R.id.filterButton);
        backIcon = findViewById(R.id.backButton);
        calendarBtn = findViewById(R.id.calendar);
    }

    private void NavigationDrawer() {

        NavigationDrawerFragment my_nav = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        my_nav.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), my_toolbar);

    }

    class Date extends DateItem {
        String getDate() {
            Calendar calendar = getCalendar();
            return String.format(Locale.US,
                    "%d/%d/%d",
                    getYear(), getMonth(), getDay(),
                    calendar.get(Calendar.YEAR),
                    +calendar.get(Calendar.MONTH) + 1,
                    +calendar.get(Calendar.DAY_OF_MONTH));
        }
    }
}