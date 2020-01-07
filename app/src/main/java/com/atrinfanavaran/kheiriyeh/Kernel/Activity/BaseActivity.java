package com.atrinfanavaran.kheiriyeh.Kernel.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.FnValidColumnListBll;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Controller;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.Filter;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackGet;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Module.SnakBar.SnakBar;
import com.atrinfanavaran.kheiriyeh.Kernel.Domain.FnValidColumnList;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.ExceptionHandler;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.ListBuilder;
import com.atrinfanavaran.kheiriyeh.R;
import com.bumptech.glide.Glide;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

public class BaseActivity extends AppCompatActivity {

    public static final String TAG = "moh3n";

    {
//        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentTimeStamp(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(date);
    }


    public static MaterialDialog alertWaiting(Context context) {

        MaterialDialog waiting_dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.alert_waiting, false)
                .autoDismiss(false)
                .backgroundColor(Color.parseColor("#01000000"))
                .build();

        ImageView loading_circle = (ImageView) waiting_dialog.findViewById(R.id.loading_circle_alert);

        Glide.with(context)
                .load(R.mipmap.loading)
                .into(loading_circle);
        return waiting_dialog;
    }

    public static MaterialDialog alertWaiting2(Context context, String str) {

        MaterialDialog waiting_dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.alert_waiting, false)
                .autoDismiss(false)
                .backgroundColor(Color.parseColor("#01000000"))
                .build();
        TextView warning = (TextView) waiting_dialog.findViewById(R.id.ok);
        warning.setText(str);
        ImageView loading_circle = (ImageView) waiting_dialog.findViewById(R.id.loading_circle_alert);

        Glide.with(context)
                .load(R.mipmap.loading)
                .into(loading_circle);
        return waiting_dialog;
    }

    protected <T extends BaseActivity> T getActivity() {
        return (T) this;
    }

    public void SnakBar(String str) {
        SnakBar snakBar = new SnakBar();
        snakBar.snakShow(BaseActivity.this, str);
    }

    public Controller controller() {

        return new Controller(BaseActivity.this);
    }


    public ListBuilder listBuilder() {
        return new ListBuilder(BaseActivity.this);
    }


    public Boolean isONLINE(Context context) {
        SettingsBll settingsBll = new SettingsBll(context);
        return settingsBll.isOnline();
    }

    public void getFnValidColumnList(String SchemaName, String TableName, String UserId, CallbackGet callbackGet) {

        FnValidColumnListBll fnValidColumnListBll = new FnValidColumnListBll(this);
        ArrayList<String> fnValidColumnListsInsertion = new ArrayList<>();
        ArrayList<FnValidColumnList> fnValidColumnLists = new ArrayList<>();
        ArrayList<Filter> filter = new ArrayList<>();
        filter.add(new Filter("SchemaName", SchemaName));
        filter.add(new Filter("TableName", TableName));
        filter.add(new Filter("UserId", UserId));

//        warningTxt.setVisibility(View.GONE);
        fnValidColumnListBll.Get(filter, 0, 0, true, new CallbackGet() {
            @Override
            public <T> void onSuccess(ArrayList<T> result, int count) {

                fnValidColumnLists.addAll((Collection<? extends FnValidColumnList>) result);

                for (int i = 0; i < fnValidColumnLists.size(); i++) {
                    if (fnValidColumnLists.get(i).getSoftwareOperationId().equals("1")) {
                        fnValidColumnListsInsertion.add(fnValidColumnLists.get(i).getColumnName());
                    }
                }
                callbackGet.onSuccess(fnValidColumnListsInsertion, fnValidColumnListsInsertion.size());
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public String changeNumber(String num) {
        num = num.replaceAll("۰", "0");
        num = num.replaceAll("۱", "1");
        num = num.replaceAll("۲", "2");
        num = num.replaceAll("۳", "3");
        num = num.replaceAll("۴", "4");
        num = num.replaceAll("۵", "5");
        num = num.replaceAll("۶", "6");
        num = num.replaceAll("۷", "7");
        num = num.replaceAll("۸", "8");
        num = num.replaceAll("۹", "9");
        num = num.replaceAll("٫", ".");
        return num;
    }
}
