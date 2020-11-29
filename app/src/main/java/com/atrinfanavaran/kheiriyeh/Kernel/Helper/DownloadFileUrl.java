package com.atrinfanavaran.kheiriyeh.Kernel.Helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.afollestad.materialdialogs.MaterialDialog;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Module.SnakBar.SnakBarDownload;
import com.atrinfanavaran.kheiriyeh.Kernel.Interface.OnFinishedDownloadCallback;
import com.atrinfanavaran.kheiriyeh.R;
import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class DownloadFileUrl extends BaseActivity {
    private Context context;
    private String Url;
    // Progress Dialog
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    private String fileName;
    private HashMap<String, String> params;
    private OnFinishedDownloadCallback onFinishedCallback;
    private MaterialDialog progress_dialog;
    private RoundCornerProgressBar progressBar;
    private TextView percentTxt;
    private TextView cancel;
    private TextView sizeTxt;
    private TextView speedTxt;
    private ImageView iView;
    private TextView warning_message;
    private HttpURLConnection connection;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private boolean force;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public DownloadFileUrl(Context context, String Url, String fileName, HashMap<String, String> params, boolean force, OnFinishedDownloadCallback onFinishedCallback) {
        this.Url = Url;
        this.context = context;
        this.fileName = fileName;
        this.params = params;
        this.onFinishedCallback = onFinishedCallback;
        this.force = force;

        materialDialogBuild();

        ActivityCompat.requestPermissions(((Activity) context), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        if (checkWriteExternalPermission()) {
            new DownloadFileFromURL().execute(Url);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new DownloadFileFromURL().execute(Url);
                } else {
                    Toast.makeText(context, "دسترسی توسط کاربر ایجاد نشد", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private boolean checkWriteExternalPermission() {
        String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private void materialDialogBuild() {
        progress_dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.alert_progress_dialog, false)
                .autoDismiss(false)
                .canceledOnTouchOutside(false)
                .backgroundColor(Color.parseColor("#01000000"))
                .build();
        progressBar = (RoundCornerProgressBar) progress_dialog.findViewById(R.id.progressBar);
        percentTxt = (TextView) progress_dialog.findViewById(R.id.percent);
        cancel = (TextView) progress_dialog.findViewById(R.id.cancel);
        sizeTxt = (TextView) progress_dialog.findViewById(R.id.size);
        speedTxt = (TextView) progress_dialog.findViewById(R.id.speed);
        iView = (ImageView) progress_dialog.findViewById(R.id.loading);

        Glide.with(context)
                .load(R.mipmap.loading_processmaker)
                .into(iView);
        warning_message = (TextView) progress_dialog.findViewById(R.id.warning_alert);
        warning_message.setText("لطفا منتظر بمانید...");

        cancel.setOnClickListener(v -> {
            if (force) {
                finishAffinity();
            } else {
                progress_dialog.dismiss();
            }
        });

        cancel.setText("لغو دریافت");
//        progress_dialog.show();
    }


    public class DownloadFileFromURL extends AsyncTask<String, String, String> {
        private DownloadFileFromURL updateTask = null;

        /**
         * Before starting background thread Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            updateTask = this;
            progress_dialog.show();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
//                URL url = new URL(f_url[0]);
//                URLConnection connection = url.openConnection();

                HttpsTrustManager.allowAllSSL();

                SettingsBll settingsBll = new SettingsBll(context);
                URL url = new URL(Url);

                if (settingsBll.getUrlAddress().startsWith("https://")) {
                    connection = (HttpsURLConnection) url.openConnection();
                } else {
                    connection = (HttpURLConnection) url.openConnection();
                }
                connection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = connection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                // Output stream
                File root = new File(Environment.getExternalStorageDirectory() + "/ghasedakn/files/", "");
                if (!root.exists()) {
                    root.setReadable(true);
                    root.setWritable(true);
                    root.mkdirs();
                }

                String mPath = Environment.getExternalStorageDirectory().toString() + "/ghasedakn/files/" + fileName;
                File imageFile = new File(mPath);
                FileOutputStream output = new FileOutputStream(imageFile);
                byte data[] = new byte[1024];

                long total = 0;

                cancel.setOnClickListener(v -> {
                    Thread t = new Thread(() -> {
                        progress_dialog.dismiss();
                        connection.disconnect();
                        updateTask.cancel(true);
                        try {
                            output.close();
                            input.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    });
                    t.start();

                });

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile), "" + total, "" + lenghtOfFile);

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                return "cancel";
            }

            return "ok";
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
//            pDialog.setProgress(Integer.parseInt(progress[0]));
            percentTxt.setText(progress[0] + "%");
            progressBar.setProgress(Integer.valueOf(progress[0]));
            warning_message.setText("در حال دریافت ...");

            int total = (Integer.valueOf(progress[2]) / 1024);
            int getSize = (Integer.valueOf(progress[1]) / 1024);
            sizeTxt.setText("حجم فایل: KB " + getSize + "/" + total);

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded


            Log.i(TAG, "onPostExecute: " + file_url);
            if (file_url.equals("ok")) {
                progress_dialog.dismiss();
//                SnakBarDownload snakBar = new SnakBarDownload();
//                snakBar.snakShow(context, context.getString(R.string.saveDone), "/ghasedak/files/", fileName);
                File root = new File(Environment.getExternalStorageDirectory() + "/ghasedak/files/" , fileName);

                try {
                    openFile(context, root);
                } catch (ActivityNotFoundException e) {
                    Log.i("moh3n", "onClick: " + e.toString());
                }
                onFinishedCallback.onFinish();
            } else if (file_url.equals("cancel")) {
                Toast.makeText(context, "خطا در دریافت فایل", Toast.LENGTH_SHORT).show();
                progress_dialog.dismiss();
            }
        }

        @Override
        protected void onCancelled(String s) {
            onFinishedCallback.onCancel();
        }
    }
    private void openFile(Context context, File url) {

        try {

            Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".my.package.name.provider", url);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.toString().contains("..xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav");
            } else if (url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") ||
                    url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                intent.setDataAndType(uri, "*/*");
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.i("moh3n", "openFile: " + e);
            Toast.makeText(context, "برنامه ای برای باز کردن فایل مورد نظر یافت نشد ", Toast.LENGTH_SHORT).show();
        }
    }
}
