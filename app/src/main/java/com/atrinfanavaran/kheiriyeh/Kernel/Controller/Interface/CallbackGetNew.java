package com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface;

public interface CallbackGetNew {

    <T> void onSuccess(Object result, boolean showNewBtn);

    void onError(String error);

}
