package com.atrinfanavaran.kheiriyeh.Kernel.GenericEdit;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.GenericListBll;
import com.atrinfanavaran.kheiriyeh.Kernel.GenericEdit.Interface.OnApplyEditListener;
import com.atrinfanavaran.kheiriyeh.R;

import java.util.ArrayList;
import java.util.HashMap;


public class GenericNewDialog extends Dialog {
    private final Class domain;
    private final OnApplyEditListener listener;
    private Object object;
    private Context context;
    private Activity activity;
    public GenericNewDialog(@NonNull Context context, Context context2,
                            Class domain,
                            Object data,
                            Activity activity,
                            OnApplyEditListener listener) {
        super(context);
        this.context = context2;
        this.domain = domain;
        this.listener = listener;
        this.activity = activity;
        object = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_new_dynamic);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        initViews();
        setCanceledOnTouchOutside(false);

        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            getWindow().setAttributes(lp);
        }
    }
//
//    private void initViews() {
//        TextView apply = findViewById(R.id.apply_filter);
//
////        //get data
//        GenericListBll bll = new GenericListBll(getContext());
////        ArrayList<DomainInfo> domainInfos = bll.getDomainInfo(domain);
//
//        ArrayList<DomainInfo> domainInfos = null;
//
//        try {
//            Class superClass = domain.getSuperclass();
//            Method methodName = superClass.getDeclaredMethod("getDomainInfo");
//            domainInfos = (ArrayList<DomainInfo>) methodName.invoke(object);
//
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//
//        //filter data where mode is either EDIT or ALL
//        ArrayList<DomainInfo> editDomains = bll.getEditDomains(domainInfos);
//
//
//        // parse a data object and map the stored values
//        HashMap<String, String> decodedData = new HashMap<>();
//        if (object != null) {
//            decodedData = bll.getIds(object, editDomains);
//        }
//
//        GenericEditAdapter genericEditAdapter = new GenericEditAdapter(editDomains, decodedData);
//
//        //set adapter to recyclerView
//        RecyclerView recyclerView = findViewById(R.id.recycler_dialog);
//        recyclerView.setItemViewCacheSize(20);
//        recyclerView.setAdapter(genericEditAdapter);
//
//
//        HashMap<String, String> finalDecodedData = decodedData;
//        apply.setOnClickListener(v -> {
//            dismiss();
//            listener.onApplyEdit(finalDecodedData);
//        });
//
//    }

    private void initViews() {
        TextView apply = findViewById(R.id.apply_filter);

        //get data
        GenericListBll bll = new GenericListBll(getContext());
        ArrayList<DomainInfo> domainInfos = bll.getDomainInfo(domain, object);

        //filter data where mode is either EDIT or ALL
        ArrayList<DomainInfo> editDomains = bll.getEditDomains(domainInfos);


        // parse a data object and map the stored values
        HashMap<String, String> decodedData = new HashMap<>();
        if (object != null) {
            decodedData = bll.getIds(object, editDomains);
        }

        GenericNewAdapter genericNewAdapter = new GenericNewAdapter(activity,editDomains, decodedData);

        //set adapter to recyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_dialog);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setAdapter(genericNewAdapter);


        HashMap<String, String> finalDecodedData = decodedData;
        apply.setOnClickListener(v -> {
            listener.onApplyEdit(finalDecodedData);
        });

    }
}
