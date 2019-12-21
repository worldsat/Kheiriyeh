package com.atrinfanavaran.kheiriyeh.Kernel;

import android.content.Context;

import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Controller;

public class DownloadFile {

    public static  void download(Context context,String attachId,String attachName){
        SettingsBll settingsBll = new SettingsBll(context);
        String Api = "api/pgAttach/DownLoadOnline" + "?token=" + settingsBll.getTicket() + "&AttachId=" + attachId;
        Controller controller = new Controller(context);
        controller.DownloadFile(context, attachName, Api, null);
    }
}
