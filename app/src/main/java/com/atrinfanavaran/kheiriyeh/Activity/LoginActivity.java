package com.atrinfanavaran.kheiriyeh.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.atrinfanavaran.kheiriyeh.Domain.AndroidVersion;
import com.atrinfanavaran.kheiriyeh.Domain.Login;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Controller;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackGet;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackOperation;
import com.atrinfanavaran.kheiriyeh.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity {
    private EditText userEdt, passEdt;
    private ProgressBar waitingProgressbar;
    private Button loginBtn;
    private TextView rulesBtn;
    private SettingsBll settingsBll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        initView();
        setVariable();
        RunPermissionDownload();
        checkVersion();
    }


    @Override
    public void onBackPressed() {

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

                controller().LoginApi(this, MainActivity.class, new Login().getApiAddress(), loginObject.toString(), new CallbackOperation() {
                    @Override
                    public void onSuccess(String result) {
                        settingsBll.setLoging(true);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        waitingProgressbar.setVisibility(View.GONE);
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
    }

    private void initView() {
        userEdt = findViewById(R.id.UserEdt);
        passEdt = findViewById(R.id.passEdt);
        loginBtn = findViewById(R.id.loginBtn);
        waitingProgressbar = findViewById(R.id.progressBar3);
        rulesBtn = findViewById(R.id.rules);

    }

    private void RunPermissionDownload() {

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.ACCESS_NETWORK_STATE
                        , Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread().check();

    }

    private void checkRunTimePermission() {
        String[] permissionArrays = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.ACCESS_NETWORK_STATE
                , Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.ACCESS_COARSE_LOCATION
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionArrays, 1);
        } else {

        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    checkRunTimePermission();
                }
                return;
            }
        }
    }

    private void checkVersion() {


        Controller controller = new Controller(this);
        controller.Get(AndroidVersion.class, null, 0, 0, true, new CallbackGet() {
            @Override
            public <T> void onSuccess(ArrayList<T> result, int count) {
                Log.i("moh3n", "version: " + result);
                ArrayList<AndroidVersion> response = (ArrayList<AndroidVersion>) result;

                if (response.size() > 0) {

                    int lastVerisionCode = Integer.valueOf(response.get(response.size() - 1).getcurrVersion());
                    String link = response.get(response.size() - 1).getappAndroidUrl();

                    try {
                        PackageInfo phoneVersion = LoginActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0);

                        if (phoneVersion.versionCode < lastVerisionCode) {
                            alertQuestion(LoginActivity.this, link);
                        }
                        Log.i("moh3n", phoneVersion.versionCode + " " + lastVerisionCode);
                    } catch (Exception e) {
                        Log.i("moh3n", "error versionConde:" + e);
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    private void alertQuestion(final Context context, String link) {
        final MaterialDialog question_dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.alert_warning_update, false)
                .autoDismiss(false)
                .backgroundColor(Color.parseColor("#01000000"))
                .show();

        TextView ok_btn = (TextView) question_dialog.findViewById(R.id.ok);
        TextView cancel_btn = (TextView) question_dialog.findViewById(R.id.cancel);
        final TextView warningTxt = (TextView) question_dialog.findViewById(R.id.warning_alert);


        warningTxt.setText("نسخه جدید از نرم افزار موجود است،آیا مایل به بروزرسانی میباشید؟");

        ok_btn.setOnClickListener(v -> {

            String Address = link;
            Log.i("moh3n", "onClick: " + Address);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(Address));
            startActivity(i);

        });
        cancel_btn.setOnClickListener(v -> question_dialog.dismiss());

    }


}
