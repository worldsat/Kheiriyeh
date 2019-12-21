package com.atrinfanavaran.kheiriyeh.Kernel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Controller;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackOperation;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Module.SnakBar.SnakBar;
import com.atrinfanavaran.kheiriyeh.Kernel.GenericEdit.Interface.OnEditFinishedListener;
import com.atrinfanavaran.kheiriyeh.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DashboardProcess {


    public static void StartProcessDashboard(final Context context, String ElementPrimaryKey, String DashboardTitleId, String dashboardTitleStartMessage, OnEditFinishedListener refreshListener) {
        MaterialDialog question_dialog = new MaterialDialog.Builder(((Activity) context))
                .customView(R.layout.alert_question, false)
                .autoDismiss(false)
                .backgroundColor(Color.parseColor("#01000000"))
                .show();

        TextView ok_btn = (TextView) question_dialog.findViewById(R.id.ok);
        TextView cancel_btn = (TextView) question_dialog.findViewById(R.id.cancel);
        final TextView warningTxt = (TextView) question_dialog.findViewById(R.id.warning_alert);

        if (dashboardTitleStartMessage.isEmpty()) {
            warningTxt.setText("آیا از شروع این فرایند اطمینان دارید؟");
        } else {
            warningTxt.setText(dashboardTitleStartMessage);
        }

        ok_btn.setOnClickListener(v -> {

            BaseActivity baseActivity = new BaseActivity();
            MaterialDialog waiting = baseActivity.alertWaiting(context);
            waiting.show();

            question_dialog.dismiss();
            JSONObject params = null;
            try {
                params = new JSONObject();

                if (ElementPrimaryKey != null) {

                    if (!ElementPrimaryKey.equals("null")) {
                        params.put("ElementPrimaryKey", Integer.valueOf(ElementPrimaryKey));
                    }
                } else {
                    params.put("ElementPrimaryKey", "null");
                }


//                if (object.getElementPrimaryKey_Related() != null) {
//                    if (!object.getExecutionFileId().equals("null")) {
//                        params.put("ElementPrimaryKey_Related", Integer.valueOf(object.getExecutionFileId()));
//                    }
//                } else {
                params.put("ElementPrimaryKey_Related", "null");
//                }

                if (DashboardTitleId != null) {
                    if (!DashboardTitleId.equals("null")) {
                        params.put("DashboardTitleId", Integer.valueOf(DashboardTitleId));
                    } else {
                        params.put("DashboardTitleId", "null");
                    }
                } else {
                    params.put("DashboardTitleId", "null");
                }


            } catch (JSONException e) {
                Log.i("moh3n", "StartProcessDashboardError: " + e);
                SnakBar snakBar = new SnakBar();
                snakBar.snakShow(context, "خطا در انجام عملیات پردازش");
            }
            String str = params.toString().replace("\"null\"", "null");
            Controller controller = new Controller(context);
            controller.operationProcess(context, "api/pgDashboardProcess/StartDashboardProcess",str, new CallbackOperation() {
                @Override
                public void onSuccess(String result) {
                    waiting.dismiss();

                    Toast.makeText(context, result, Toast.LENGTH_LONG).show();
//                    Toast.makeText(context, "لطفا صفحه را به روزرسانی کنید", Toast.LENGTH_LONG).show();
                    refreshListener.onFinish();
                }

                @Override
                public void onError(String error) {
                    waiting.dismiss();

                    Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                    refreshListener.onFinish();

                }
            });
        });
        cancel_btn.setOnClickListener(v -> question_dialog.dismiss());

    }

}
