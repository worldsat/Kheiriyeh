package com.atrinfanavaran.kheiriyeh.Activity.Flower.Add;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alirezaafkar.sundatepicker.DatePicker;
import com.alirezaafkar.sundatepicker.components.DateItem;
import com.atrinfanavaran.kheiriyeh.Activity.Flower.List.DonatorListItemActivity;
import com.atrinfanavaran.kheiriyeh.Activity.Flower.List.TajGolListItemActivity;
import com.atrinfanavaran.kheiriyeh.Adapter.Flower.DonatorListAdapter;
import com.atrinfanavaran.kheiriyeh.Domain.CeremonyType;
import com.atrinfanavaran.kheiriyeh.Domain.DeceasedNameApi;
import com.atrinfanavaran.kheiriyeh.Domain.DonatorApi;
import com.atrinfanavaran.kheiriyeh.Domain.FlowerCrownApi;
import com.atrinfanavaran.kheiriyeh.Domain.FlowerCrownTypeApi;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackGetString;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackOperation;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.NumberTextWatcherForThousand;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.SearchableField;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.roozh;
import com.atrinfanavaran.kheiriyeh.R;

import com.atrinfanavaran.kheiriyeh.Room.Domian.DeceasedNameR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.DonatorR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.FlowerCrownR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.FlowerCrownTypeR;
import com.google.gson.Gson;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class AddTajGolActivity extends BaseActivity {


    private Toolbar my_toolbar;

    private TextView title;
    private EditText edt1, edt2;
    private Button saveBtn;
    private TextView titleToolbar;
    private LinearLayout filterIcon, backIcon;
    private SearchableSpinner CeremonySpinner, donatorSpinner, deceasedNameSpinner, FlowerCrownTypeSpinner, inturducedSpinner;
    private ImageView calendarBtn;
    private int ceremonyTypeId = 0, FlowerCrownTypeId = 0, donatorId = 0, DeceasedNameId = 0, IntroducedId = 0;
    private String donatorGuId, DeceasedNameGuId, IntroducedGuId;
    private boolean editable = false;
    private FlowerCrownR object;

    private String donator, deceasedName, flowerCrownType, ceremonyType, Introduced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_taj_gol);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        getBundle();
        initView();
        NavigationDrawer();
        setvariable();


    }

    private void getBundle() {
        object = (FlowerCrownR) getIntent().getSerializableExtra("object");
        editable = getIntent().getBooleanExtra("editable", false);

    }

    private void setvariable() {
        titleToolbar.setText("افزودن تاج گل");
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (donatorSpinner.getSelectedItem().toString().equals("انتخاب کنید")) {
                    Toast.makeText(getActivity(), "لطفا اهدا کننده را انتخاب نمائید", Toast.LENGTH_SHORT).show();
                    return;
//                } else if (inturducedSpinner.getSelectedItem().toString().equals("انتخاب کنید")) {
//                    Toast.makeText(getActivity(), "لطفا معرف را انتخاب نمائید", Toast.LENGTH_SHORT).show();
//                    return;
                } else if (deceasedNameSpinner.getSelectedItem().toString().equals("انتخاب کنید")) {
                    Toast.makeText(getActivity(), "لطفا نام متوفی را انتخاب نمائید", Toast.LENGTH_SHORT).show();
                    return;
                } else if (CeremonySpinner.getSelectedItem().toString().equals("انتخاب کنید")) {
                    Toast.makeText(getActivity(), "لطفا مراسم را انتخاب نمائید", Toast.LENGTH_SHORT).show();
                    return;
                } else if (FlowerCrownTypeSpinner.getSelectedItem().toString().equals("انتخاب کنید")) {
                    Toast.makeText(getActivity(), "لطفا نوع تاج گل را انتخاب نمائید", Toast.LENGTH_SHORT).show();
                    return;
                } else if (edt1.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا مبلغ را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                } else if (edt2.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا تاریخ ثبت را وارد نمائید", Toast.LENGTH_SHORT).show();
                    return;
                }
//
//                FlowerCrownR flowerCrownR = new FlowerCrownR();
//                flowerCrownR.flowerCrownTypeId = FlowerCrownTypeId;
//                flowerCrownR.flowerCrownType = flowerCrownType;
//                flowerCrownR.ceremonyType = ceremonyType;
//                flowerCrownR.ceremonyTypeId = ceremonyTypeId;
//                flowerCrownR.price = Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(edt1.getText().toString()));
//                flowerCrownR.registerDate = edt2.getText().toString();
//                flowerCrownR.registerDateEn = shamsiToMiladi(edt2.getText().toString());
//                flowerCrownR.charityId = settingsBll().getCharityId();
//                flowerCrownR.charity = settingsBll().getCharity();
//                flowerCrownR.donator = donator;
//                flowerCrownR.donatorId = donatorId;
//                flowerCrownR.guidDonator = donatorGuId;
//                flowerCrownR.deceasedNameId = DeceasedNameId;
//                flowerCrownR.guidDeceasedName = DeceasedNameGuId;
//                flowerCrownR.deceasedName = deceasedName;
//                flowerCrownR.guidIntroduced = IntroducedGuId;
//                flowerCrownR.IntroducedId = IntroducedId;
//                flowerCrownR.Introduced = Introduced;
//
//
//                if (editable) {
//                    flowerCrownR.id = object.getId();
//                    db().FlowerCrownDao().update(flowerCrownR.guidIntroduced, flowerCrownR.guidDeceasedName, flowerCrownR.guidDonator, flowerCrownR.flowerCrownType, flowerCrownR.deceasedName, flowerCrownR.charity, flowerCrownR.donator, flowerCrownR.donatorId, flowerCrownR.ceremonyTypeId, flowerCrownR.ceremonyType,
//                            flowerCrownR.price, flowerCrownR.registerDate, flowerCrownR.registerDateEn, flowerCrownR.flowerCrownTypeId, flowerCrownR.deceasedNameId, flowerCrownR.id, flowerCrownR.IntroducedId, flowerCrownR.Introduced);
//                    Toast.makeText(AddTajGolActivity.this, "عملیات ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
//                } else {
//                    flowerCrownR.isNew = "true";
//                    db().FlowerCrownDao().insertFlowerCrown(flowerCrownR);
//                    Toast.makeText(AddTajGolActivity.this, "عملیات ذخیره با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
//                }
//                finish();
//                startActivity(new Intent(AddTajGolActivity.this, TajGolListItemActivity.class));


                JSONObject params = new JSONObject();

                try {

                    params.put("price", Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(edt1.getText().toString())));
                    params.put("CeremonyType", ceremonyTypeId);
                    params.put("registerDate", edt2.getText().toString());
                    params.put("flowerCrownTypeId", FlowerCrownTypeId);
                    params.put("guidDonator", donatorGuId);
                    params.put("guidIntroduced", IntroducedGuId);
                    params.put("guidDeceasedName", DeceasedNameGuId);

                } catch (Exception e) {
                    Toast.makeText(AddTajGolActivity.this, "خطا در پارامتر های ارسالی ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (params != null) {
                    Log.i("moh3n", "sendFlowerCrown: " + params.toString());
                }

                MaterialDialog wait = alertWaiting2(getActivity(), "در حال ارسال  اطلاعات...");
                wait.show();
                controller().Operation("", FlowerCrownApi.class, getActivity(), params.toString(), new CallbackOperation() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("moh3n", "sendFlowerCrown: " + result);
                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

                        wait.dismiss();
                        finish();
                        startActivity(new Intent(AddTajGolActivity.this, TajGolListItemActivity.class));
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
            edt1.setText("" + object.getPrice());
            edt2.setText(object.getRegisterDate());


        }
        edt1.addTextChangedListener(new NumberTextWatcherForThousand(edt1));
        filterIcon.setVisibility(View.GONE);
        backIcon.setVisibility(View.VISIBLE);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try {
            roozh jCal = new roozh();
            Calendar cal = Calendar.getInstance();
            int dayOfMonth1 = cal.get(Calendar.DAY_OF_MONTH);
            int month1 = cal.get(Calendar.MONTH);
            int year1 = cal.get(Calendar.YEAR);
            jCal.GregorianToPersian(year1, month1, dayOfMonth1);
            edt2.setText(jCal.getYear() + "/" + jCal.getMonth() + "/" + jCal.getDay());
        }catch (Exception e){

        }
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
                edt2.setText(year + "/" + month + "/" + day);

            }).show(getActivity().getSupportFragmentManager(), "");

        });
        edt2.setKeyListener(null);
        edt2.setOnClickListener(v -> {
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
                edt2.setText(year + "/" + month + "/" + day);

            }).show(getActivity().getSupportFragmentManager(), "");

        });

        //************************************************************************
        ArrayList<CeremonyType> CeremonyTypeItems = new ArrayList<>();
        CeremonyTypeItems.add(new CeremonyType("انتخاب کنید", 0));
        CeremonyTypeItems.add(new CeremonyType("ترحیم", 1));
        CeremonyTypeItems.add(new CeremonyType("هفته", 2));
        CeremonyTypeItems.add(new CeremonyType("چهلم", 3));
        CeremonyTypeItems.add(new CeremonyType("سال", 4));
        SearchableField.setSpinner(CeremonySpinner, CeremonyTypeItems);

        CeremonySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ceremonyTypeId = ((CeremonyType) CeremonySpinner.getSelectedItem()).getId();
                ceremonyType = ((CeremonyType) CeremonySpinner.getSelectedItem()).getName();
//                Toast.makeText(AddTajGolActivity.this, "" + ceremonyTypeId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (editable) {
            try {
                for (int i = 0; i < CeremonyTypeItems.size(); i++) {
                    if (CeremonyTypeItems.get(i).getId() == object.getCeremonyTypeId()) {
                        CeremonySpinner.setSelection(i);
                        break;
                    }
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "خطا در بازخانی نوع مراسم", Toast.LENGTH_SHORT).show();
            }
        }
        //************************************

        MaterialDialog wait3 = alertWaiting2(getActivity(), "در حال دریافت اطلاعات");
        wait3.show();
        SettingsBll settingsBll = new SettingsBll(getActivity());
        controller().GetFromApi2("api/FlowerCrownType/" + settingsBll.getCharityId(), new CallbackGetString() {
            @Override
            public void onSuccess(String resultStr) {

                Gson gson = new Gson();
                FlowerCrownTypeApi response = gson.fromJson(resultStr, FlowerCrownTypeApi.class);

                List<FlowerCrownTypeApi.Data> flowerCrownTypeApi = new ArrayList<>();
                flowerCrownTypeApi.add(new FlowerCrownTypeApi.Data(0, "انتخاب کنید"));
                flowerCrownTypeApi.addAll(response.getData());
                SearchableField.setSpinner(FlowerCrownTypeSpinner, flowerCrownTypeApi);


                FlowerCrownTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        FlowerCrownTypeId = ((FlowerCrownTypeApi.Data) FlowerCrownTypeSpinner.getSelectedItem()).getId();
                        flowerCrownType = ((FlowerCrownTypeApi.Data) FlowerCrownTypeSpinner.getSelectedItem()).getTitle();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                if (editable) {
                    try {
                        for (int i = 0; i < flowerCrownTypeApi.size(); i++) {
                            if (flowerCrownTypeApi.get(i).getId() == object.getFlowerCrownTypeId()) {
                                FlowerCrownTypeSpinner.setSelection(i);
                                break;
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "خطا در بازخانی نوع تاج گل", Toast.LENGTH_SHORT).show();
                    }
                }
                wait3.dismiss();
            }

            @Override
            public void onError(String error) {
                wait3.dismiss();
                Toast.makeText(AddTajGolActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });


        //****************************************
        MaterialDialog wait = alertWaiting2(getActivity(), "در حال دریافت اطلاعات");
        wait.show();
        controller().GetFromApi2("api/Donator/GetAll", new CallbackGetString() {
            @Override
            public void onSuccess(String resultStr) {

                Gson gson = new Gson();
                DonatorApi response = gson.fromJson(resultStr, DonatorApi.class);

                wait.dismiss();

                List<DonatorApi.Data> donatorApi = new ArrayList<>();
                donatorApi.add(new DonatorApi.Data(0, "انتخاب کنید"));
                donatorApi.addAll(response.getData());
                SearchableField.setSpinner(donatorSpinner, donatorApi);
                SearchableField.setSpinner(inturducedSpinner, donatorApi);


                donatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        donatorGuId = ((DonatorApi.Data) donatorSpinner.getSelectedItem()).getGuidDonator();
                        donator = ((DonatorApi.Data) donatorSpinner.getSelectedItem()).getDonatorFullName();
                        donatorId = ((DonatorApi.Data) donatorSpinner.getSelectedItem()).getId();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                if (editable) {
                    try {
                        for (int i = 0; i < donatorApi.size(); i++) {
                            if (donatorApi.get(i).getId() == object.getDonatorId()) {
                                donatorSpinner.setSelection(i);
                                break;
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "خطا در بازخانی اهدا کننده", Toast.LENGTH_SHORT).show();
                    }
                }

                inturducedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        IntroducedGuId = ((DonatorApi.Data) inturducedSpinner.getSelectedItem()).getGuidDonator();
                        Introduced = ((DonatorApi.Data) inturducedSpinner.getSelectedItem()).getDonatorFullName();
                        IntroducedId = ((DonatorApi.Data) inturducedSpinner.getSelectedItem()).getId();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                if (editable) {
                    try {
                        for (int i = 0; i < donatorApi.size(); i++) {
                            if (donatorApi.get(i).getId() == object.getIntroducedId()) {
                                inturducedSpinner.setSelection(i);
                                break;
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "خطا در بازخانی معرف", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(String error) {
                wait.dismiss();
                Toast.makeText(AddTajGolActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });

        //****************************************

        MaterialDialog wait2 = alertWaiting2(getActivity(), "در حال دریافت اطلاعات");
        wait2.show();
        controller().GetFromApi2("api/DeceasedName/GetAll", new CallbackGetString() {
            @Override
            public void onSuccess(String resultStr) {

                Gson gson = new Gson();
                DeceasedNameApi response = gson.fromJson(resultStr, DeceasedNameApi.class);

                List<DeceasedNameApi.Data> DeceasedName = new ArrayList<>();
                DeceasedName.add(new DeceasedNameApi.Data(0, "انتخاب کنید"));
                DeceasedName.addAll(response.getData());
                SearchableField.setSpinner(deceasedNameSpinner, DeceasedName);


                deceasedNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        DeceasedNameGuId = ((DeceasedNameApi.Data) deceasedNameSpinner.getSelectedItem()).getGuidDeceasedName();
                        deceasedName = ((DeceasedNameApi.Data) deceasedNameSpinner.getSelectedItem()).getDeceasedFullName();
                        DeceasedNameId = ((DeceasedNameApi.Data) deceasedNameSpinner.getSelectedItem()).getId();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                if (editable) {
                    try {
                        for (int i = 0; i < DeceasedName.size(); i++) {
                            if (DeceasedName.get(i).getId() == object.getDeceasedNameId()) {
                                deceasedNameSpinner.setSelection(i);
                                break;
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "خطا در بازخانی نام متوفی", Toast.LENGTH_SHORT).show();
                    }
                }
                wait2.dismiss();
            }

            @Override
            public void onError(String error) {
                wait2.dismiss();
                Toast.makeText(AddTajGolActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void initView() {

        my_toolbar = findViewById(R.id.toolbar);
        edt1 = findViewById(R.id.edt1_1);
        edt2 = findViewById(R.id.edt1_2);
        CeremonySpinner = findViewById(R.id.spinner3);
        donatorSpinner = findViewById(R.id.spinner1);
        deceasedNameSpinner = findViewById(R.id.spinner2);
        saveBtn = findViewById(R.id.btn_1);
        titleToolbar = findViewById(R.id.titleToolbar);
        filterIcon = findViewById(R.id.filterButton);
        backIcon = findViewById(R.id.backButton);
        calendarBtn = findViewById(R.id.calendar);
        FlowerCrownTypeSpinner = findViewById(R.id.spinner4);
        inturducedSpinner = findViewById(R.id.spinner5);
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