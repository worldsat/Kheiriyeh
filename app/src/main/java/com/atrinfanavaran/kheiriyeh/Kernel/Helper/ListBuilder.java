package com.atrinfanavaran.kheiriyeh.Kernel.Helper;


import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Module.SnakBar.SnakBar;
import com.atrinfanavaran.kheiriyeh.R;

import java.util.ArrayList;

public class ListBuilder extends BaseDomain {

    private Context context;

    public ListBuilder(Context context) {
        this.context = context;
    }

    public <T> void Builder(ArrayList<T> response, RecyclerView recyclerViewlist, ProgressBar progressBar, TextView warningTxt, RecyclerView.Adapter ad) {


        if (response.size() == 0) {
            if (warningTxt != null)
                warningTxt.setVisibility(View.VISIBLE);

        } else {

            try {
                if (warningTxt != null)
                    warningTxt.setVisibility(View.GONE);

                recyclerViewlist.setVisibility(View.VISIBLE);
                recyclerViewlist.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                recyclerViewlist.setAdapter(ad);

            } catch (Exception e) {
                SnakBar snakBar = new SnakBar();
                snakBar.snakShow(context, context.getString(R.string.errorData));
                Log.i("moh3n", "error: " + e.getMessage());
            }
        }

        progressBar.setVisibility(View.GONE);

    }

}
