package com.atrinfanavaran.kheiriyeh.Activity.pos;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.room.Room;

import com.atrinfanavaran.kheiriyeh.Activity.Financial.List.FinancialAidListItemActivity;
import com.atrinfanavaran.kheiriyeh.Activity.MainActivity;
import com.atrinfanavaran.kheiriyeh.Activity.Sponser.Add.AddContributionActivity;
import com.atrinfanavaran.kheiriyeh.Activity.Sponser.List.ContributionListItemActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.NumberTextWatcherForThousand;
import com.atrinfanavaran.kheiriyeh.Room.AppDatabase;
import com.atrinfanavaran.kheiriyeh.Room.Domian.ContributionR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.FinancialAidR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.SponsorR;

import java.util.List;

import static com.atrinfanavaran.kheiriyeh.Activity.Sponser.Add.AddContributionActivity.iTotalPay;


public class MyBroadCast extends BroadcastReceiver {


    @SuppressLint("WrongConstant")
    @Override
    public void onReceive(Context context, Intent intent) {
        String str = "Hi I got your message : \n";
//        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        Response response = new Response();
        response.ER = intent.getStringExtra(Response.Error);
        response.TO = intent.getStringExtra(Response.TimeOut);
        response.SC = intent.getStringExtra(Response.SwipeCard);
        response.BR = intent.getStringExtra(Response.BarCodeStatus);
        response.CP = intent.getStringExtra(Response.CheckPaper);
        response.PS = intent.getStringExtra(Response.PrintStatus);
        response.MI = intent.getStringExtra(Response.MifareResult);
        response.RS = intent.getStringExtra(Response.ResponseCode);
        response.GI = intent.getStringArrayExtra(Response.GetInfo);
        response.PO = intent.getStringArrayExtra(Response.GetPositionInfo);

        boolean b = true;
        if (response.ER != null) {
            str = str + "Error : " + response.ER;
        } else if (response.SC != null) {
            str = str + "SwipeCard : " + response.SC;
            b = false;
        } else if (response.TO != null) str = str + "TimeOut : " + response.TO;
        else if (response.BR != null) str = str + "BarCodeStatus : " + response.BR;
        else if (response.CP != null) str = str + "CheckPaper : " + response.CP;
        else if (response.PS != null) str = str + "PrintStatus : " + response.PS;
        else if (response.GI != null) str = str + "MerchantInfo : \n"
                + "merchantName :" + response.GI[0] + "\n"
                + "merchantTell :" + response.GI[1] + "\n"
                + "terminalID :" + response.GI[2] + "\n"
                + "serialNumber :" + response.GI[3];

        else if (response.PO != null) str = str + "PositionInfo : \n"
                + "Latitude :" + response.PO[0] + "\n"
                + "Longitude :" + response.PO[1];
        else if (response.MI != null) {
            List<Mifare> mifareList = Mifare.getMifare(response.MI);
            int len = mifareList.size();
            str = str + "MifareResult len : " + len + "\n";
            for (int i = 0; i < len; i++) str = str + mifareList.get(i).toString() + "\n";
        } else if (response.RS != null) {
            str = str + "ResponseCode : " + response.RS + "\n";
            if (response.RS.equals("05")) {
                String qrResult = intent.getStringExtra("QR_Result");
                if (qrResult != null) {
                    if (qrResult.equals("success")) {
                        int qrAM = intent.getIntExtra("QR_AM", 0);
                        response._RRN = intent.getStringExtra(Response.RRN);
                        response.SN = intent.getStringExtra(Response.SerialNumber);
                        response.DT = intent.getStringExtra(Response.DateTime);
                        if (qrAM == iTotalPay) {
                            str = str + "پرداخت QR موفق بوده است." + "\n" +
                                    "شماره مرجع : " + response._RRN + "\n" +
                                    "شماره سریال : " + response.SN + "\n" +
                                    "تاریخ-زمان : " + response.DT + "\n";
                        } else str = str + "عدم تطابق مبلغ پرداختی و مبلغ استعلام شده";
                    } else if (qrResult.equals("failed")) str = str + "پرداخت QR ناموفق بوده است.";
                } else str = str + "Transaction Cancelled";
            } else {
                response.trxType = intent.getStringExtra(Response.TransactionType);
                response.CN = intent.getStringExtra(Response.CardNumber);
                response._RRN = intent.getStringExtra(Response.RRN);
                response.SN = intent.getStringExtra(Response.SerialNumber);
                response.DT = intent.getStringExtra(Response.DateTime);
                response.TN = intent.getStringExtra(Response.TerminalNumber);
                switch (response.trxType) {
                    case "Sale":
                    case "BillPayment":
                        response.AM = intent.getStringExtra(Response.Amount);
                        break;
                }
                str = str + response.toString();
            }
        }
        Log.e("AAAAAA", "******* " + response.toString());
        if (b) {
//            MainActivity.sc.setVisibility(View.GONE);
//            MainActivity.txt.setText(str);
//            MainActivity.scTxt.setVisibility(View.VISIBLE);
            AppDatabase db = Room.databaseBuilder(context,
                    AppDatabase.class, "RoomDb")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
            SharedPreferences sp = context.getSharedPreferences("POS", 0);
            if (sp.getBoolean("AddContributionActivity", false)) {

                ContributionR obj = new ContributionR();

                obj.SponsorId = sp.getInt("SponsorId", 0);
                obj.price = sp.getInt("price", 0);
                obj.description = sp.getString("description", "");
                obj.deviceCode = Integer.parseInt(response.SN);
                obj.terminalCode = Integer.parseInt(response.TN);
                obj.recieverCode = Integer.parseInt(response.RS);
                obj.fullName = sp.getString("fullName", "");
                obj.code = sp.getString("code", "");
                obj.nationalcode = sp.getString("nationalcode", "");
                obj.mobile = sp.getString("mobile", "");
                obj.phone = sp.getString("phone", "");
                obj.address = sp.getString("address", "");
                obj.birthDate = sp.getString("birthDate", "");
                obj.payType = sp.getInt("payType", 0);


                if (sp.getBoolean("editable", false)) {
                    obj.id = sp.getInt("id", 0);
                    db.ContributaionDao().update(obj.price, obj.description, obj.deviceCode, obj.terminalCode, obj.recieverCode, obj.fullName, obj.code,
                            obj.nationalcode, obj.mobile, obj.phone, obj.address, obj.birthDate, obj.id, obj.payType);
                    Toast.makeText(context, "عملیات ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                } else {
                    obj.isNew = "true";
                    db.ContributaionDao().insertBox(obj);
                    Toast.makeText(context, "عملیات Pos با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                }

                ComponentName cName = new ComponentName("com.pec.smartpos", "com.pec.smartpos.cpsdk.PecService");
                intent.setComponent(cName);
                intent.putExtra(PrintType.printType, PrintType.receiptSale);
                intent.putExtra(TAGS.RRN, response._RRN);
                intent.putExtra(TAGS.respCode, response.RS);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent);
                }

                sp.edit().clear().apply();
                context.startActivity(new Intent(context, ContributionListItemActivity.class));

            } else if (sp.getBoolean("AddFinancialAidActivity", false)) {
                FinancialAidR obj = new FinancialAidR();

                obj.financialServiceTypeId = sp.getInt("financialServiceTypeId", 0);
                obj.price = sp.getInt("price", 0);
                obj.name = sp.getString("name", "");
                obj.id = sp.getInt("id", 0);
                obj.payType = sp.getInt("payType", 0);


                if (sp.getBoolean("editable", false)) {
                    obj.id = sp.getInt("id", 0);
                    db.FinancialAidDao().update(obj.name, obj.price, obj.financialServiceTypeId, obj.id,  obj.payType);
                    Toast.makeText(context, "عملیات ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                } else {
                    obj.isNew = "true";
                    db.FinancialAidDao().insertBox(obj);
                    Toast.makeText(context, "عملیات Pos با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                }

                ComponentName cName = new ComponentName("com.pec.smartpos", "com.pec.smartpos.cpsdk.PecService");
                intent.setComponent(cName);
                intent.putExtra(PrintType.printType, PrintType.receiptSale);
                intent.putExtra(TAGS.RRN, response._RRN);
                intent.putExtra(TAGS.respCode, response.RS);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent);
                }

                sp.edit().clear().apply();
                context.startActivity(new Intent(context, FinancialAidListItemActivity.class));
            }
        }
    }
}

