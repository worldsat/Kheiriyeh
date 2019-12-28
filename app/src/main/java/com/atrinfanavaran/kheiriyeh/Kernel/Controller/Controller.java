package com.atrinfanavaran.kheiriyeh.Kernel.Controller;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ApiResponse;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.Filter;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.SpinnerDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallBackSpinner;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackGet;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackGetString;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackOperation;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.IOnResponseListener;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Module.SnakBar.SnakBar;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Module.SnakBar.SnakBarDownload;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Module.Volley.VolleyCall;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.DataBaseHelper;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.DateConverter;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.SHA2;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.UploadFile;
import com.atrinfanavaran.kheiriyeh.R;
import com.bumptech.glide.Glide;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Controller {
    private static String PORT;
    private static String URL;
    private final Context context;
    private final SettingsBll settingsBll;
    private boolean isOnline;
    private Gson gson;
    private ProgressDialog dialog;
    private int count = -1;

    public Controller(Context context) {
        this.context = context;

        //get all settings from SettingsBll which works with sharedPreferences
        settingsBll = new SettingsBll(context);
        URL = settingsBll.getUrlAddress();
        PORT = settingsBll.getPort();
        gson = new Gson();
        isOnline = settingsBll.isOnline();
    }

    public <T> void Get(Class domain, ArrayList<Filter> filter, int take, int skip, boolean allData, CallbackGet callbackGet) {


        try {


            Constructor constructor = domain.getConstructor();
            Object instance = constructor.newInstance();
            Class superClass = domain.getSuperclass();

            Method getTableName = superClass.getDeclaredMethod("getTableName");
            Method getApiAddress = superClass.getDeclaredMethod("getApiAddress");

            String tableName = (String) getTableName.invoke(instance);
            String apiName = (String) getApiAddress.invoke(instance);

            if (isOnline) {
                GetFromApi(domain, apiName, filter, take, skip, allData, callbackGet);
            } else {
                GetFromDatabase(tableName, filter, domain, callbackGet);
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // invokes the method at runtime


    }


    private <T> void GetFromDatabase(String tableName, ArrayList<Filter> filters, Class domain, CallbackGet callbackGet) {
        ArrayList<T> result = new ArrayList<>();
        try {

            StringBuilder filterStr = new StringBuilder();
            filterStr.append(" where 1=1 ");
            if (filters != null && filters.size() > 0) {
                for (Filter filter : filters) {
                    if (filter.getField().endsWith("Id")) {
                        filterStr.append(String.format(" and %s like '%s' ", filter.getField(), filter.getValue()));
                    } else if (filter.getField().endsWith("Date") || filter.getField().endsWith("DateTime")) {
                        DateConverter converter = new DateConverter();
                        if (filter.getValue().contains("-")) {
                            String[] dates = filter.getValue().split("-");
                            if (dates.length == 2) {
                                String[] startDate = dates[0].split("/");

                                int year = Integer.parseInt(startDate[0]);
                                int month = Integer.parseInt(startDate[1]);
                                int day = Integer.parseInt(startDate[2]);
                                converter.persianToGregorian(year, month, day);
                                String startDateEn =
                                        String.format("%02d", converter.getYear()) +
                                                "-" + String.format("%02d", converter.getMonth()) +
                                                "-" + String.format("%02d", converter.getDay());


                                String[] endDate = dates[1].split("/");

                                converter.persianToGregorian(
                                        Integer.parseInt(endDate[0]),
                                        Integer.parseInt(endDate[1]),
                                        Integer.parseInt(endDate[2])
                                );
                                String endDateEn =
                                        String.format("%02d", converter.getYear()) +
                                                "-" + String.format("%02d", converter.getMonth()) +
                                                "-" + String.format("%02d", converter.getDay());

                                filterStr.append(
                                        String.format(" and (%s >= '%s' and %s <= '%s')",
                                                filter.getField(),
                                                startDateEn,
                                                filter.getField(),
                                                endDateEn)
                                );
                            }
                        } else {
                            String[] startDate = filter.getValue().split("/");

                            converter.persianToGregorian(
                                    Integer.parseInt(startDate[0]),
                                    Integer.parseInt(startDate[1]),
                                    Integer.parseInt(startDate[2])
                            );


                            String startDateEn =
                                    String.format("%02d", converter.getYear()) +
                                            "-" + String.format("%02d", converter.getMonth()) +
                                            "-" + String.format("%02d", converter.getDay());


                            filterStr.append(
                                    String.format(" and (%s >= '%s' and %s <= '%s')",
                                            filter.getField(),
                                            startDateEn,
                                            filter.getField(),
                                            startDate)
                            );

                        }

                    } else {
                        filterStr.append(String.format(" and %s like '%%%s%%'", filter.getField(), filter.getValue()));
                    }
                }
            }
            String filterString = filterStr.toString();


            DataBaseHelper dbHelper = new DataBaseHelper(context);
            SQLiteDatabase database = dbHelper.openDataBase();

            String query = "Select * from " + tableName + filterString;
            Log.i("moh3n", "GetFromDatabase: " + query);
            Cursor cursor = database.rawQuery(query, null);
            int columnCount = cursor.getColumnCount();

            Constructor constructor = domain.getConstructor();

            if (cursor.moveToFirst()) {
                do {
                    Object item = constructor.newInstance();
                    for (int i = 0; i < columnCount; i++) {
                        String columnName = cursor.getColumnName(i);
                        Method setMethod = domain.getDeclaredMethod("set" + columnName, String.class);
                        if (cursor.getString(i) == null) {
                            setMethod.invoke(item, "null");
                        } else {
                            setMethod.invoke(item, cursor.getString(i));
                        }
                    }

                    result.add((T) item);
                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        callbackGet.onSuccess(result, result.size());
    }

    private <T> void GetFromApi(Class domain, String ApiAddress, ArrayList<Filter> filters,
                                int take, int skip, boolean allData, CallbackGet callbackGet) throws JSONException {



        Gson gson = new Gson();
        String filterStr = "[]";

        filterStr = gson.toJson(filters);

        VolleyCall volleyCall = new VolleyCall(context);
//        String Address = URL + ":" + PORT + "/" + ApiAddress;
        String Address = URL + "/" + ApiAddress;

        if (allData) {
            if (filters != null) {
                Address = Address + "?filter=" + filterStr;
            }
        } else {
            Address = Address + "?skip=" + String.valueOf(skip) + "&take=" + String.valueOf(take) + "&filter=" + filterStr;
        }

        Log.i("ApiCall", Address);

        volleyCall.Get(Address, new VolleyCall.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                Log.i("response", response);
                ArrayList<T> result = new ArrayList<>();

                JSONObject jsonRootObject = null;
                try {
                    jsonRootObject = new JSONObject(response);
                    count = jsonRootObject.getInt("count");

                    if (jsonRootObject.getString("isError").equals("true")) {
//                        Intent intent = new Intent(context, LoginActivity.class);
//                        context.startActivity(intent);
                    }

                    JSONArray array = jsonRootObject.optJSONArray("data");
                    Method[] declaredMethods = domain.getDeclaredMethods();

                    if (array.length() > 0) {

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject obj = array.getJSONObject(i);


                            int columnCount = obj.length();
                            Constructor constructor = domain.getConstructor();

                            Iterator<String> keys = obj.keys();
                            ArrayList<String> keysList = Lists.newArrayList(keys);

                            Object item = constructor.newInstance();
                            for (int j = 0; j < columnCount; j++) {
                                String columnName = keysList.get(j);
                                // Log.i("columnName", columnName);
                                String setColumnName = "set" + columnName;
                                for (int m = 0; m < declaredMethods.length; m++) {
                                    if (setColumnName.equals(declaredMethods[m].getName())) {
                                        if (setColumnName.equals("setLat1")) {
                                            Log.i("Log", "l");
                                        }
                                        declaredMethods[m].invoke(item, obj.getString(keysList.get(j)));
                                        //Log.i("added Item", item.toString());
                                        break;
                                    }
                                }
                            }

                            result.add((T) item);
                        }
                    }


                } catch (JSONException e) {

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                callbackGet.onSuccess(result, count);
            }

            @Override
            public void onError(String error) {
                callbackGet.onError(error);
            }
        });
    }


    public void GetFromApi2(String ApiAddress, CallbackGetString callbackGet) {

        VolleyCall volleyCall = new VolleyCall(context);
        String Address = URL + "/" + ApiAddress;

        Log.i("ApiCall", Address);

        volleyCall.Get(Address, new VolleyCall.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                Log.i("response", response);
                callbackGet.onSuccess(response);
            }

            @Override
            public void onError(String error) {
                callbackGet.onError(error);
            }
        });
    }


    public void Operation(String Operation, Class domain, Context context, String params, CallbackOperation callbackOperation) {
        try {
            Constructor constructor = domain.getConstructor();
            Object instance = constructor.newInstance();
            Class superClass = domain.getSuperclass();

//            Method getTableName = superClass.getDeclaredMethod("getTableName");
            Method getApiAddress = superClass.getDeclaredMethod("getApiAddress");

//            String tableName = (String) getTableName.invoke(instance);
            String apiName = (String) getApiAddress.invoke(instance);


            if (isOnline) {
                OperationApi(context, Operation, apiName, params, callbackOperation);
            } else {
//                    OperationDatabase(context, params, callbackOperation);
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


    private void OperationApi(Context context, String Operation, String ApiAddress, String params, CallbackOperation callback) {

        String Address = "";
        if (Operation.equals("Manage")) {
//            Address = URL + ":" + PORT + "/" + ApiAddress + "/Manage";
            Address = URL + "/" + ApiAddress + "/Manage";
        } else if (Operation.equals("Delete")) {
//            Address = URL + ":" + PORT + "/" + ApiAddress + "/Delete";
            Address = URL + "/" + ApiAddress + "/Delete";
        } else {
//            Address = URL + ":" + PORT + "/" + ApiAddress;
            Address = URL + "/" + ApiAddress;
        }

        VolleyCall volleyCall = new VolleyCall(context);
        volleyCall.Post(params, Address, new VolleyCall.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {

                Gson gson = new Gson();
                ApiResponse apiResponse = gson.fromJson(response, ApiResponse.class);

                if (!apiResponse.isError) {

                    try {

                        callback.onSuccess(apiResponse.message);
                        Log.i("moh3n", "Message: " + apiResponse.message);
                    } catch (Exception e) {

                        Log.i("moh3n", e.toString());
                    }

                } else callback.onError(apiResponse.message);

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void OperationPostBodyApi(Context context, String Operation, String ApiAddress, Map<String, String> params, CallbackOperation callback) {

        String Address = "";
        if (Operation.equals("Manage")) {
//            Address = URL + ":" + PORT + "/" + ApiAddress + "/Manage";
            Address = URL + "/" + ApiAddress + "/Manage";
        } else if (Operation.equals("Delete")) {
//            Address = URL + ":" + PORT + "/" + ApiAddress + "/Delete";
            Address = URL + "/" + ApiAddress + "/Delete";
        } else {
//            Address = URL + ":" + PORT + "/" + ApiAddress;
            Address = URL + "/" + ApiAddress + Operation;
        }

        VolleyCall volleyCall = new VolleyCall(context);
        volleyCall.Volley_POSTBody(params, Address, new VolleyCall.VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Log.i("moh3n", "onSuccessResponse: " + result);
                try {
                    Gson gson = new Gson();
                    ApiResponse apiResponse = gson.fromJson(result, ApiResponse.class);

                    if (!apiResponse.isError) {

                        try {
                            callback.onSuccess(apiResponse.message);
                            Log.i("moh3n", "Message: " + apiResponse.message);
                        } catch (Exception e) {

                            Log.i("moh3n", e.toString());
                        }

                    } else callback.onError(apiResponse.message);
                } catch (Exception e) {

                    Log.i("moh3n", "errorPostBody: " + e.toString());
                }

            }

            @Override
            public void onError(String error) {

                SnakBar snakBar = new SnakBar();
                snakBar.snakShow(context, "خطا در دریافت اطلاعات");

                Log.i("moh3n", "errorPostBody: " + error);
            }
        });


    }

    public void DownloadFile(Context context, String fileName, String ApiAddress, String params) {

        String Address = URL + "/" + ApiAddress;

        MaterialDialog waiting_dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.alert_waiting, false)
                .autoDismiss(false)
                .backgroundColor(Color.parseColor("#01000000"))
                .show();

        VolleyCall volleyCall = new VolleyCall(context);
        volleyCall.download(params, Address, new VolleyCall.VolleyCallbackByte() {
            @Override
            public void onSuccessResponse(byte[] result) {
                if (result != null) {
                    saveFile(context, fileName, result);
                    waiting_dialog.dismiss();
                }
            }

            @Override
            public void onError(String error) {
                Log.i("moh3n", "onErrorDownload: " + error);
                waiting_dialog.dismiss();
            }
        });

    }

    public void PopulateFilterSpinner(String field, String filter, DomainInfo item, String entryName, String apiAddress, CallBackSpinner callback) {
        VolleyCall volleyCall = new VolleyCall(context);

        String Address = URL + "/" + apiAddress;

        if (filter != null)
            Address = Address + "?filter=" + filter;

        volleyCall.Get(Address, new VolleyCall.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                ArrayList<SpinnerDomain> result = new ArrayList<>();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.optJSONArray("Data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        String id;

                        // Exception for the domains with RegionId and RegionIdParent
                        if (field.equalsIgnoreCase("RegionIdParent")) {
                            id = jsonArray.getJSONObject(i).getString("RegionId");
                        } else {
                            id = jsonArray.getJSONObject(i).getString(field);
                        }


                        String title = jsonArray.getJSONObject(i).getString(entryName);
                        result.add(new SpinnerDomain(field, title, id));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(result);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    public ArrayList<DomainInfo> GetDomainInfo(Class domain) {
        ArrayList<DomainInfo> result = new ArrayList<>();
        Constructor constructor;
        try {
            constructor = domain.getConstructor();
            Object instance = constructor.newInstance();
            Class superclass = domain.getSuperclass();
            Method getDomainInfo = superclass.getDeclaredMethod("getDomainInfo");
            result = (ArrayList<DomainInfo>) getDomainInfo.invoke(instance);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }


    public ArrayList<DomainInfo> GetDomainInfo(Class domain, Object object) {
        ArrayList<DomainInfo> result = new ArrayList<>();
        Constructor constructor;
        try {
            constructor = domain.getConstructor();
            Object instance = constructor.newInstance();
            Class superclass = domain.getSuperclass();
            Method getDomainInfo = superclass.getDeclaredMethod("getDomainInfo");
            result = (ArrayList<DomainInfo>) getDomainInfo.invoke(object);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void LoginApi(Context context, Class IntentClass, String ApiAddress, String params, CallbackOperation callbackOperation) {
//        String Address = URL + ":" + PORT + "/" + ApiAddress;
        String Address = URL + "/" + ApiAddress;
        VolleyCall volleyCall = new VolleyCall(context);

        volleyCall.Post(params, Address, new VolleyCall.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {

                Gson gson = new Gson();
                ApiResponse apiResponse = gson.fromJson(response, ApiResponse.class);

                if (!apiResponse.isError && apiResponse.getmessage().equals("")) {


                    try {

                        String token = new JSONObject(response).getString("token");
                        String fullName = new JSONObject(response).getString("fullName");
                        String UserId = new JSONObject(response).getString("code");
//                        Log.i("moh3n", "onSuccessResponse: " + token);
                        settingsBll.setTicket(token);
                        settingsBll.setName(fullName);
                        settingsBll.setUserId(UserId);

                        callbackOperation.onSuccess(response);
//                        context.startActivity(new Intent(context, IntentClass));

                    } catch (JSONException e) {

                        Log.i("moh3n", e.toString());
                    }


                } else callbackOperation.onError(apiResponse.message);


            }

            @Override
            public void onError(String error) {
                callbackOperation.onError(error);
            }
        });
    }

    public void operationProcess(Context context, String ApiAddress, String params, CallbackOperation callbackOperation) {
//        String Address = URL + ":" + PORT + "/" + ApiAddress;
        String Address = URL + "/" + ApiAddress;
        VolleyCall volleyCall = new VolleyCall(context);

        volleyCall.Post(params, Address, new VolleyCall.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {

                Gson gson = new Gson();
                ApiResponse apiResponse = gson.fromJson(response, ApiResponse.class);

                if (!apiResponse.isError && apiResponse.getmessage() == null) {

                    callbackOperation.onSuccess(apiResponse.message);

                } else callbackOperation.onError(apiResponse.message);
            }

            @Override
            public void onError(String error) {
                callbackOperation.onError(error);
            }
        });
    }


    //    public void uploadFile(Context context, String ApiAddress, final File selectedFile, Map<String, Object> params, IOnResponseListener resonse) {
    public void uploadFile(Context context, String ApiAddress, final File selectedFile, Map<String, Object> params, IOnResponseListener resonse) {

//        String Address = URL + ":" + PORT + "/" + ApiAddress;
        String Address = URL + "/" + ApiAddress;

//        dialog = new ProgressDialog(context);
//        dialog.setCancelable(false);
//        dialog.setMessage("در حال آپلود...");
//        dialog.setIndeterminate(false);
//        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        //dialog.setMax(imagesSize + 1);
//        dialog.setProgress(0);
//        dialog.show();
        // dialog = ProgressDialog.show(context, "", "در حال بارگذاری فایل...", true);
        Log.i("moh3n", "uploadFile: " + Address);
        //set progress dialog
        MaterialDialog progress_dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.alert_progress_dialog, false)
                .autoDismiss(false)
                .backgroundColor(Color.parseColor("#01000000"))
                .build();
        RoundCornerProgressBar progressBar = (RoundCornerProgressBar) progress_dialog.findViewById(R.id.progressBar);
        TextView percent = (TextView) progress_dialog.findViewById(R.id.percent);
        ImageView iView = (ImageView) progress_dialog.findViewById(R.id.loading);
        Glide.with(context)
                .load(R.mipmap.loading_processmaker)
                .into(iView);
        TextView warning_message = (TextView) progress_dialog.findViewById(R.id.warning_alert);
        warning_message.setText("در حال ارسال...");

        progress_dialog.show();

//        if (!selectedFile.isFile()) {
//            progress_dialog.dismiss();
//
//            Toast.makeText(context, "فایل قابل انتقال نمی باشد", Toast.LENGTH_SHORT).show();
//
//        } else {
        try {


            Log.i("moh3n", "params: " + params.toString());
            UploadFile ws = new UploadFile(context);
            ws.post(params, Address, settingsBll.getTicket(), () -> {

                        Log.i("moh3n", "onResponse: UploadOk");
                        percent.setText(100 + "%");
                        progressBar.setProgress(100);
                        progress_dialog.dismiss();

                        resonse.onResponse();

                        Toast.makeText(context, "اتمام فرایند بارگذاری", Toast.LENGTH_SHORT).show();
                    }
            );
        } catch (Exception e) {

            Log.i("moh3n", "uploadFileCatch: " + e);
        }

//        }

    }


    public void uploadFile(Context context, String ApiAddress, final File selectedFile, HashMap<String, Object> key) {

        String Address = URL + ":" + PORT + "/" + ApiAddress;

//        dialog = new ProgressDialog(context);
//        dialog.setCancelable(false);
//        dialog.setMessage("در حال آپلود...");
//        dialog.setIndeterminate(false);
//        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        //dialog.setMax(imagesSize + 1);
//        dialog.setProgress(0);
//        dialog.show();
        // dialog = ProgressDialog.show(context, "", "در حال بارگذاری فایل...", true);

        //set progress dialog
        MaterialDialog progress_dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.alert_progress_dialog, false)
                .autoDismiss(false)
                .backgroundColor(Color.parseColor("#01000000"))
                .build();
        RoundCornerProgressBar progressBar = (RoundCornerProgressBar) progress_dialog.findViewById(R.id.progressBar);
        TextView percent = (TextView) progress_dialog.findViewById(R.id.percent);
        ImageView iView = (ImageView) progress_dialog.findViewById(R.id.loading);
        Glide.with(context)
                .load(R.mipmap.loading_processmaker)
                .into(iView);
        TextView warning_message = (TextView) progress_dialog.findViewById(R.id.warning_alert);
        warning_message.setText("در حال ارسال...");

        progress_dialog.show();

        if (!selectedFile.isFile()) {
            progress_dialog.dismiss();

            Toast.makeText(context, "فایل قابل انتقال نمی باشد", Toast.LENGTH_SHORT).show();

        } else {
            try {

                key.put("uploaded_file", selectedFile);
                UploadFile ws = new UploadFile(context);
                ws.post(key, Address, settingsBll.getTicket(), () -> {

                            Log.i("moh3n", "onResponse: UploadOk");
                            percent.setText(100 + "%");
                            progressBar.setProgress(100);
                            progress_dialog.dismiss();

                            SnakBar snakBar = new SnakBar();
                            snakBar.snakShow(context, "اتمام فرایند بارگذاری");
                        }
                );
            } catch (Exception e) {

                Log.i("moh3n", "uploadFileCatch: " + e);
            }

        }

    }


    public void loginDatabase(String userName, String password, CallbackOperation callbackOperation) {

        String hashedUserName = "";
        String hashedPassword = "";
        try {
//            hashedUserName = SHA2.GenerateHash(userName, "512");
            hashedPassword = SHA2.GenerateHash(password, "512");
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
        }

        String query = "SELECT * FROM t_User WHERE UserUserName LIKE ? AND UserPassword LIKE ? COLLATE NOCASE";
        Log.i("moh3n", "loginDatabase: " + query);
        String[] selectionArgs = {userName, hashedPassword};
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(context);

            SQLiteDatabase database = dbHelper.openDataBase();
            Cursor cursor = database.rawQuery(query, selectionArgs);
            int count = 0;

            // if the returned row size is more than 0 publish the size
            // a successful login must return just 1 row otherwise something has had gone wrong
            if (cursor.moveToFirst())
                count = cursor.getCount();

            cursor.close();
            dbHelper.close();

            callbackOperation.onSuccess(String.valueOf(count));
        } catch (Exception e) {
            e.printStackTrace();
            callbackOperation.onError(e.toString());
        }
    }

    public boolean saveFile(Context context, String fileName, byte[] response) {

//        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
//            return false;
//        }

        boolean success = false;


        File root = new File(Environment.getExternalStorageDirectory() + "/makan", "");
        if (!root.exists()) {
            root.setReadable(true);
            root.setWritable(true);
            root.mkdirs();
        }

        File file = new File(root, fileName);
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            os.write(response);
            success = true;
        } catch (IOException e) {
        } catch (Exception e) {
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }

        SnakBarDownload snakBar = new SnakBarDownload();
        snakBar.snakShow(context, "عملیات با موفقیت انجام شد", "/makan", fileName);


        return success;
    }
}
