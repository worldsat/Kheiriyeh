package com.atrinfanavaran.kheiriyeh.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.atrinfanavaran.kheiriyeh.Domain.RememberPass;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackOperation;
import com.atrinfanavaran.kheiriyeh.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RememberPasswordActivity extends BaseActivity {
    private EditText mobileEdt;
    private ProgressBar waitingProgressbar;
    private Button loginBtn;
    private TextView rulesBtn;
    private SettingsBll settingsBll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember_password);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        initView();
        setVariable();

    }



    private void setVariable() {
        settingsBll = new SettingsBll(this);

        loginBtn.setOnClickListener(v -> {

            waitingProgressbar.setVisibility(View.VISIBLE);

            JSONObject loginObject = null;
            try {
                loginObject = new JSONObject();
                loginObject.put("userName", mobileEdt.getText().toString());


            } catch (JSONException e) {
                e.printStackTrace();
            }

            controller().Operation("", RememberPass.class, getActivity(), loginObject.toString(), new CallbackOperation() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(RememberPasswordActivity.this, "رمز عبور به شماره همراه شما ارسال خواهد شد", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RememberPasswordActivity.this, LoginActivity.class));
                    waitingProgressbar.setVisibility(View.GONE);
                }

                @Override
                public void onError(String error) {
                    waitingProgressbar.setVisibility(View.GONE);
                    SnakBar(error);
                }
            });

        });


    }

    private void initView() {
        mobileEdt = findViewById(R.id.mobileEdt);
        loginBtn = findViewById(R.id.loginBtn);
        waitingProgressbar = findViewById(R.id.progressBar3);
        rulesBtn = findViewById(R.id.rules);

    }

}
