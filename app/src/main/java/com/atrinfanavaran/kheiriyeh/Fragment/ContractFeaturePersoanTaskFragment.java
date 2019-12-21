package com.atrinfanavaran.kheiriyeh.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.atrinfanavaran.kheiriyeh.Bll.ContractFutureListBll;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.Filter;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackGet;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.EndlessRecyclerViewScrollListener;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.TinyDB;
import com.atrinfanavaran.kheiriyeh.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;


public class ContractFeaturePersoanTaskFragment extends Fragment {

    private static final int TAKE = 100;
    private int SKIP = 0;
    private RecyclerView recyclerViewlist;
    private ProgressBar progressBar;
    private TextView warningTxt;

    private ArrayList<String> response = new ArrayList<>();
    private RecyclerView.Adapter adapter;


    private EndlessRecyclerViewScrollListener scrollListener;

    private boolean isScrollListenerAdded = true;

    Properties properties = new Properties();
    private TinyDB tinydb;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_contract_feature, container, false);

        return rootView;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tinydb = new TinyDB(getActivity());
        recyclerViewlist = view.findViewById(R.id.View);
        progressBar = view.findViewById(R.id.progressBarRow);
        warningTxt = view.findViewById(R.id.warninTxt1);
//        row = getActivity().findViewById(R.id.row);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewlist.setLayoutManager(linearLayoutManager);

        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list

                getDataApi(false);
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerViewlist.addOnScrollListener(scrollListener);


        getDataApi(false);


    }


    private void resetAll() {
        // ----------------------- reset the list
        if (adapter != null) {
            response.clear();
            adapter.notifyDataSetChanged();
            scrollListener.resetState();
            SKIP = 0;

            // listen for scroll changes in RecyclerView
            if (!isScrollListenerAdded)
                recyclerViewlist.addOnScrollListener(scrollListener);
        }
    }

    private void getDataApi(boolean notify) {

        ContractFutureListBll contractFutureListBll = new ContractFutureListBll(getContext());

        ArrayList<Filter> filter = new ArrayList<>();
        filter.add(new Filter("ContractFeatureStatusId", "6"));


        warningTxt.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        BaseActivity baseActivity = new BaseActivity();
        MaterialDialog waiting = baseActivity.alertWaiting(getActivity());
        if (notify) {
            waiting.show();
        }
        contractFutureListBll.Get(filter, TAKE, SKIP, false, new CallbackGet() {
            @Override
            public <T> void onSuccess(ArrayList<T> result, int count) {

                int previousResponseSize = SKIP;
                if (notify) {
                    response.clear();
                }
                response.addAll((Collection<? extends String>) result);
                SKIP = response.size();


                // check if the server response if not error if so show a text
                if (response.size() == 0) {
                    warningTxt.setVisibility(View.VISIBLE);
                } else {
                    warningTxt.setVisibility(View.GONE);
                    // check if the response count is less or equal to the total available data
                    // remove the scroll listener
                    if (notify) {
                        progressBar.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                        waiting.dismiss();
                        return;
                    }
                    if (count != -1 && SKIP == count) {
                        recyclerViewlist.clearOnScrollListeners();
                        isScrollListenerAdded = false;
                    }
                    if (adapter == null) {
                        recyclerViewlist.setVisibility(View.VISIBLE);
//                        adapter = new ContractFutureListAdapter3(response, new OnEditFinishedListener() {
//                            @Override
//                            public void onFinish() {
//                                SKIP = 0;
//                                getDataApi(null, true);
//                            }
//                        });
                        recyclerViewlist.setAdapter(adapter);
                    } else {
                        if (recyclerViewlist.getVisibility() == View.GONE)
                            recyclerViewlist.setVisibility(View.VISIBLE);

                        adapter.notifyItemRangeInserted(previousResponseSize, SKIP);
                    }

                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(String error) {

            }
        });
    }


}
