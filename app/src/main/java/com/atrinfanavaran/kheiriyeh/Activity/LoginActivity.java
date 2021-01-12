package com.atrinfanavaran.kheiriyeh.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.atrinfanavaran.kheiriyeh.Domain.AndroidVersion;
import com.atrinfanavaran.kheiriyeh.Domain.Charity;
import com.atrinfanavaran.kheiriyeh.Domain.Login;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Controller;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackGet;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackGetString;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackOperation;
import com.atrinfanavaran.kheiriyeh.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity {
    private EditText userEdt, passEdt;
    private ProgressBar waitingProgressbar;
    private Button loginBtn;
    private TextView rulesBtn, rememberPassBtn;
    private SettingsBll settingsBll;
    private static final int Time_Between_Two_Back = 2000;
    private long TimeBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        initView();
        setVariable();

    }


    @Override
    public void onBackPressed() {
        if (TimeBackPressed + Time_Between_Two_Back > System.currentTimeMillis()) {
            finishAffinity();
            return;
        } else {
            Toast.makeText(this, "به منظور خروج دوباره کلیک کنید", Toast.LENGTH_SHORT).show();
        }
        TimeBackPressed = System.currentTimeMillis();
    }

    private void setVariable() {
        settingsBll = new SettingsBll(this);

        loginBtn.setOnClickListener(v -> {

            waitingProgressbar.setVisibility(View.VISIBLE);

            if (passEdt.getText().toString().isEmpty()) {

                SnakBar("لطفا رمز عبور را وارد نمائید");
                waitingProgressbar.setVisibility(View.GONE);
            } else {

                JSONObject loginObject = null;
                try {
                    loginObject = new JSONObject();
                    loginObject.put("userName", userEdt.getText().toString());
                    loginObject.put("password", passEdt.getText().toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                controller().LoginApi(this, MainActivity.class, new Login().getApiAddresss(), loginObject.toString(), new CallbackOperation() {
                    @Override
                    public void onSuccess(String result) {
                        settingsBll.setLoging(true);
//                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        checkAccess();
//                        waitingProgressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(String error) {
                        waitingProgressbar.setVisibility(View.GONE);
                        SnakBar(error);
                    }
                });
            }
        });

        rulesBtn.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RulesActivity.class)));
        rememberPassBtn.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RememberPasswordActivity.class)));
    }

    private void checkAccess() {
        controller().GetFromApi2("api/Charity", new CallbackGetString() {
            @Override
            public void onSuccess(String resultStr) {
                Log.i(TAG, "onSuccess1: " + resultStr);
                try {
                    Gson gson = new Gson();

                    Charity charity = gson.fromJson(resultStr, Charity.class);
                    Log.i(TAG, "onSuccess2: " + charity);

                    settingsBll.setAccessBox(charity.isIsAccessBox());
                    settingsBll.setActive(charity.isIsActive());
                    settingsBll.setAccessFinancialAid(charity.isIsAccessFinancialAid());
                    settingsBll.setAccessFlowerCrown(charity.isIsAccessFlowerCrown());
                    settingsBll.setAccessSponsor(charity.isIsAccessSponsor());
                    if (settingsBll.isAccessBox()) {

                        SharedPreferences sp = getApplicationContext().getSharedPreferences("Settings", 0);
                        if (sp != null) {
                            if (settingsBll.getLoging()) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    } else {
                        SnakBar("حساب کاربری شما مسدود می باشد");
                    }

                } catch (Exception e) {
                    Log.i(TAG, "onSuccess4: " + e);
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        userEdt = findViewById(R.id.UserEdt);
        passEdt = findViewById(R.id.passEdt);
        loginBtn = findViewById(R.id.loginBtn);
        waitingProgressbar = findViewById(R.id.progressBar3);
        rulesBtn = findViewById(R.id.rules);
        rememberPassBtn = findViewById(R.id.remember);

    }


}
