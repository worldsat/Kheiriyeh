package com.atrinfanavaran.kheiriyeh.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atrinfanavaran.kheiriyeh.Domain.ContactUs;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackGetString;
import com.atrinfanavaran.kheiriyeh.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class ContactUsActivity extends BaseActivity {
    //    RecyclerView recyclerView;
    ProgressBar progressBar;
    private TextView phone, pageTelegramUrl, pageInstagramUrl, pageTwitterUrl, email, androidVersion;
    private LinearLayout informationLayout;
private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        initView();
        toolbar();
        getData();
        setLogo();
    }

    private void toolbar() {
        TextView title = findViewById(R.id.titleToolbar);
        LinearLayout backIcon = findViewById(R.id.backButton);
        backIcon.setVisibility(View.VISIBLE);
        backIcon.setOnClickListener(v -> finish());
        title.setText("تماس با ما");
    }

    private void initView() {
        progressBar = findViewById(R.id.progressBarRow);
        phone = findViewById(R.id.phone);
        pageTelegramUrl = findViewById(R.id.pageTelegramUrl);
        pageInstagramUrl = findViewById(R.id.pageInstagramUrl);
        pageTwitterUrl = findViewById(R.id.pageTwitterUrl);
        email = findViewById(R.id.email);
        androidVersion = findViewById(R.id.androidVersion);
        informationLayout = findViewById(R.id.information);
        imageView = findViewById(R.id.logo2);
    }
    private void setLogo() {

        SettingsBll settingsBll = new SettingsBll(getActivity());

        Glide.with(getActivity())
                .load(settingsBll.getUrlAddress() + "/" + settingsBll.getLogoAddress())
                .into(imageView);

    }
    private void getData() {
        progressBar.setVisibility(View.VISIBLE);

        controller().GetFromApi2("api/ContactUs", new CallbackGetString() {
                    @Override
                    public void onSuccess(String resultStr) {
                        Gson gson = new Gson();

                        try {
                            String str = new JSONObject(resultStr).getString("data");
                            ContactUs response = gson.fromJson(str, ContactUs.class);
                            if (response.getid() == null) return;

                            informationLayout.setVisibility(View.VISIBLE);
                            phone.setText(response.getphone());
                            pageTelegramUrl.setText(response.getpageTelegramUrl());
                            pageInstagramUrl.setText(response.getpageInstagramUrl());
                            pageTwitterUrl.setText(response.getpageTwitterUrl());
                            email.setText(response.getemail());
                            androidVersion.setText(response.getandroidVersion());


                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        SnakBar(error);
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );

    }
}
