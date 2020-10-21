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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.GenericListBll;
import com.atrinfanavaran.kheiriyeh.Kernel.GenericEdit.Interface.OnApplyEditListener;
import com.atrinfanavaran.kheiriyeh.R;

import java.util.ArrayList;
import java.util.HashMap;


public class GenericEditDialog extends Dialog {
    private final Class domain;
    private final OnApplyEditListener listener;
    private Object object;
    private Activity activity;
    private Context context;

    public GenericEditDialog(@NonNull Context context, Context context2,
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
        setContentView(R.layout.alert_edit_dynamic);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);

        initViews();

        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            getWindow().setAttributes(lp);
        }
    }

    private void initViews() {
        TextView apply = findViewById(R.id.apply_filter);
        TextView cancel = findViewById(R.id.cancel_filter);
        TextView empty_tv = findViewById(R.id.empty_tv);

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

//        //id , title , required
//        HashMap<HashMap<String, String>, Boolean> RequiredMap = bll.getRequired(object, editDomains);
//
//        for (Map.Entry<HashMap<String, String>, Boolean> entry : RequiredMap.entrySet()) {
//
//            if (entry.getValue() == true) {
//
//               HashMap<String, String> idTitleMap = entry.getKey();
//
//               for(Map.Entry<String ,String> idTitle : idTitleMap.entrySet()){
//
//                   if(decodedData.get(idTitle.getKey()).equals("null")){
//                       Log.d("nile", idTitle.getValue() + "is required");
//
//                   }
//               }
//            }
//        }


        if (editDomains.size() == 0) {
            apply.setVisibility(View.GONE);
            empty_tv.setVisibility(View.VISIBLE);
        }

        GenericEditAdapter genericEditAdapter = new GenericEditAdapter(activity, editDomains, decodedData);

        //set adapter to recyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_dialog);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setAdapter(genericEditAdapter);


        HashMap<String, String> finalDecodedData = decodedData;
        apply.setOnClickListener(v -> {

//            dismiss();
            listener.onApplyEdit(finalDecodedData);
        });
        cancel.setOnClickListener(v -> dismiss());
    }
}