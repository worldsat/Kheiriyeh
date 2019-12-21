package com.atrinfanavaran.kheiriyeh.Kernel.GenericFilter;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.FilteredDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.GenericListBll;
import com.atrinfanavaran.kheiriyeh.R;

import java.util.ArrayList;
import java.util.HashMap;

public class GenericFilterDialog extends Dialog {
    private final Class domain;
    private final OnApplyFilterListener onApply;
    private HashMap<Integer, FilteredDomain> filteredDomain;
    private Context context;

    public GenericFilterDialog(@NonNull Context context, Context context2, Class domain, HashMap<Integer, FilteredDomain> filteredMap, OnApplyFilterListener listener) {
        super(context);
        this.domain = domain;
        this.onApply = listener;
        this.filteredDomain = filteredMap;
        this.context = context2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_filter_dynamic);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
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

    private void initViews() {

        TextView apply = findViewById(R.id.apply_filter);
        TextView cancel = findViewById(R.id.cancel_filter);
        TextView title = findViewById(R.id.textView13);
        title.setText("فیلتر");
        //create adapter
        GenericListBll bll = new GenericListBll(getContext());
        ArrayList<DomainInfo> domainInfos = bll.getDomainInfo(domain);

        //filter data where mode is either FILTER or ALL
        ArrayList<DomainInfo> filterDomains = bll.getFilterDomains(domainInfos);

        GenericFilterAdapter genericFilterAdapter = new GenericFilterAdapter(
                filterDomains,
                filteredDomain,
                () -> {
                    dismiss();
                    onApply.onApply(filteredDomain);
                });

        //set adapter to recyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_dialog);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setAdapter(genericFilterAdapter);


        apply.setOnClickListener(genericFilterAdapter);
        cancel.setOnClickListener(v -> dismiss());
    }

}
