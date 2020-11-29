package com.atrinfanavaran.kheiriyeh.Activity.Flower.Add;

import android.content.Intent;
import android.os.Bundle;
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

import com.alirezaafkar.sundatepicker.DatePicker;
import com.alirezaafkar.sundatepicker.components.DateItem;
import com.atrinfanavaran.kheiriyeh.Activity.Flower.List.TajGolListItemActivity;
import com.atrinfanavaran.kheiriyeh.Domain.CeremonyType;
import com.atrinfanavaran.kheiriyeh.Fragment.NavigationDrawerFragment;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.NumberTextWatcherForThousand;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.SearchableField;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.Domian.DeceasedNameR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.DonatorR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.FlowerCrownR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.FlowerCrownTypeR;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
                FlowerCrownR flowerCrownR = new FlowerCrownR();
                flowerCrownR.flowerCrownTypeId = FlowerCrownTypeId;
                flowerCrownR.flowerCrownType = flowerCrownType;
                flowerCrownR.ceremonyType = ceremonyType;
                flowerCrownR.ceremonyTypeId = ceremonyTypeId;
                flowerCrownR.price = Integer.parseInt(NumberTextWatcherForThousand.trimCommaOfString(edt1.getText().toString()));
                flowerCrownR.registerDate = edt2.getText().toString();
                flowerCrownR.registerDateEn = shamsiToMiladi(edt2.getText().toString());
                flowerCrownR.charityId = settingsBll().getCharityId();
                flowerCrownR.charity = settingsBll().getCharity();
                flowerCrownR.donator = donator;
                flowerCrownR.donatorId = donatorId;
                flowerCrownR.guidDonator = donatorGuId;
                flowerCrownR.deceasedNameId = DeceasedNameId;
                flowerCrownR.guidDeceasedName = DeceasedNameGuId;
                flowerCrownR.deceasedName = deceasedName;
                flowerCrownR.guidIntroduced = IntroducedGuId;
                flowerCrownR.IntroducedId = IntroducedId;
                flowerCrownR.Introduced = Introduced;


                if (editable) {
                    flowerCrownR.id = object.getId();
                    db().FlowerCrownDao().update(flowerCrownR.guidIntroduced, flowerCrownR.guidDeceasedName, flowerCrownR.guidDonator, flowerCrownR.flowerCrownType, flowerCrownR.deceasedName, flowerCrownR.charity, flowerCrownR.donator, flowerCrownR.donatorId, flowerCrownR.ceremonyTypeId, flowerCrownR.ceremonyType,
                            flowerCrownR.price, flowerCrownR.registerDate, flowerCrownR.registerDateEn, flowerCrownR.flowerCrownTypeId, flowerCrownR.deceasedNameId, flowerCrownR.id, flowerCrownR.IntroducedId, flowerCrownR.Introduced);
                    Toast.makeText(AddTajGolActivity.this, "عملیات ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                } else {
                    flowerCrownR.isNew = "true";
                    db().FlowerCrownDao().insertFlowerCrown(flowerCrownR);
                    Toast.makeText(AddTajGolActivity.this, "عملیات ذخیره با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                }
                finish();
                startActivity(new Intent(AddTajGolActivity.this, TajGolListItemActivity.class));
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

        //************************************************************************
        ArrayList<CeremonyType> CeremonyTypeItems = new ArrayList<>();
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
        List<FlowerCrownTypeR> flowerCrownTypeR = new ArrayList<>();
        flowerCrownTypeR.add(new FlowerCrownTypeR(0, "انتخاب کنید"));
        flowerCrownTypeR.addAll(db().FlowerCrownTypeDao().getAll());
        SearchableField.setSpinner(FlowerCrownTypeSpinner, flowerCrownTypeR);


        FlowerCrownTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FlowerCrownTypeId = ((FlowerCrownTypeR) FlowerCrownTypeSpinner.getSelectedItem()).getId();
                flowerCrownType = ((FlowerCrownTypeR) FlowerCrownTypeSpinner.getSelectedItem()).getTitle();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (editable) {
            try {
                for (int i = 0; i < flowerCrownTypeR.size(); i++) {
                    if (flowerCrownTypeR.get(i).getId() == object.getFlowerCrownTypeId()) {
                        FlowerCrownTypeSpinner.setSelection(i);
                        break;
                    }
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "خطا در بازخانی نوع تاج گل", Toast.LENGTH_SHORT).show();
            }
        }

        //****************************************
        List<DonatorR> donatorR = new ArrayList<>();
        donatorR.add(new DonatorR(0, "انتخاب کنید"));
        donatorR.addAll(db().DonatorDao().getAll());
        SearchableField.setSpinner(donatorSpinner, donatorR);
        SearchableField.setSpinner(inturducedSpinner, donatorR);


        donatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                donatorGuId = ((DonatorR) donatorSpinner.getSelectedItem()).getGuidDonator();
                donator = ((DonatorR) donatorSpinner.getSelectedItem()).getDonatorFullName();
                donatorId = ((DonatorR) donatorSpinner.getSelectedItem()).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (editable) {
            try {
                for (int i = 0; i < donatorR.size(); i++) {
                    if (donatorR.get(i).getId() == object.getDonatorId()) {
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
                IntroducedGuId = ((DonatorR) inturducedSpinner.getSelectedItem()).getGuidDonator();
                Introduced = ((DonatorR) inturducedSpinner.getSelectedItem()).getDonatorFullName();
                IntroducedId = ((DonatorR) inturducedSpinner.getSelectedItem()).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (editable) {
            try {
                for (int i = 0; i < donatorR.size(); i++) {
                    if (donatorR.get(i).getId() == object.getIntroducedId()) {
                        inturducedSpinner.setSelection(i);
                        break;
                    }
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "خطا در بازخانی معرف", Toast.LENGTH_SHORT).show();
            }
        }
        //****************************************
        List<DeceasedNameR> DeceasedName = new ArrayList<>();
        DeceasedName.add(new DeceasedNameR(0, "انتخاب کنید"));
        DeceasedName.addAll(db().DeceasedNameDao().getAll());
        SearchableField.setSpinner(deceasedNameSpinner, DeceasedName);


        deceasedNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DeceasedNameGuId = ((DeceasedNameR) deceasedNameSpinner.getSelectedItem()).getGuidDeceasedName();
                deceasedName = ((DeceasedNameR) deceasedNameSpinner.getSelectedItem()).getDeceasedFullName();
                DeceasedNameId = ((DeceasedNameR) deceasedNameSpinner.getSelectedItem()).getId();

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