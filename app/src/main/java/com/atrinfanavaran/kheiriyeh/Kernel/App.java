package com.atrinfanavaran.kheiriyeh.Kernel;

import android.app.Application;

import okhttp3.OkHttpClient;

public class App extends Application {
    public OkHttpClient httpClient;

    @Override
    public void onCreate() {
        super.onCreate();

        String font = "fonts/iransans_m.ttf";


    }

}
