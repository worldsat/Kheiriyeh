package com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface;


import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.SpinnerDomain;

import java.util.ArrayList;

public interface CallBackSpinner {
    void onSuccess(ArrayList<SpinnerDomain> result);

    void onError(String error);
}
