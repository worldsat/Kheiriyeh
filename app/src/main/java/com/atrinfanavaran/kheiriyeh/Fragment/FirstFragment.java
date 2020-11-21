package com.atrinfanavaran.kheiriyeh.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atrinfanavaran.kheiriyeh.Adapter.BoxIncomeListHorizontalAdapter;
import com.atrinfanavaran.kheiriyeh.Adapter.QuickListAdapter;
import com.atrinfanavaran.kheiriyeh.Domain.Sliders;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackQuickList;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Controller;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackGet;
import com.atrinfanavaran.kheiriyeh.Kernel.Helper.TinyDB;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.AppDatabase;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxIncomeR;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class FirstFragment extends Fragment {
    private onCallBackQuickList onCallBackQuickList;
    private static final int TAKE = 100;
    private int SKIP = 0;
    private RecyclerView row1, row2;
    private ProgressBar progressBar, progressBar2;
    private TextView warningTxt;
    private SliderLayout sliderShow;
    private ArrayList<Sliders> response = new ArrayList<>();
    private ArrayList<Sliders> responseLastDischarge = new ArrayList<>();
    private RecyclerView.Adapter adapter1, adapter2;
    private Controller controller;
    private TinyDB tinydb;
    private AppDatabase db;
    private TextView titleToolbar;
    private LinearLayout filterBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_first_page, container, false);

        return rootView;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sliderShow = view.findViewById(R.id.slider2);
        row1 = view.findViewById(R.id.View1);
        row2 = view.findViewById(R.id.View2);
        progressBar = view.findViewById(R.id.progressBarRow);
        progressBar2 = view.findViewById(R.id.progressBarRow2);
        warningTxt = view.findViewById(R.id.warninTxt1);
        titleToolbar = getActivity().findViewById(R.id.titleToolbar);
        tinydb = new TinyDB(getActivity());
        controller = new Controller(getActivity());
        filterBtn = getActivity().findViewById(R.id.filterButton);

        titleToolbar.setText("قاصدک");
        db = Room.databaseBuilder(getActivity(),
                AppDatabase.class, "RoomDb").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        filterBtn.setVisibility(View.GONE);

        getSlider();
        quickList();
        lastDischarge();


    }

    private void lastDischarge() {
        progressBar2.setVisibility(View.VISIBLE);


//                responseLastDischarge.addAll((Collection<? extends Sliders>) result);
        List<BoxIncomeR> routes = db.BoxIncomeDao().getAll();
        adapter2 = new BoxIncomeListHorizontalAdapter(routes);
        row2.setAdapter(adapter2);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        row2.setLayoutManager(linearLayoutManager);

        progressBar2.setVisibility(View.GONE);


    }


    private void getSlider() {
        SettingsBll settingsBll = new SettingsBll(getActivity());
        Controller controller = new Controller(getActivity());
        controller.Get(Sliders.class, null, TAKE, SKIP, false, new CallbackGet() {
            @Override
            public <T> void onSuccess(ArrayList<T> result, int count) {

                response.addAll((Collection<? extends Sliders>) result);

                for (int i = 0; i < response.size(); i++) {
                    String Url = "https://" + response.get(i).getlink();
                    DefaultSliderView DefaultSliderView = new DefaultSliderView(getActivity());
                    DefaultSliderView
                            .setOnSliderClickListener(slider -> {
                                Intent i1 = new Intent(Intent.ACTION_VIEW);
                                i1.setData(Uri.parse(Url));
                                startActivity(i1);
                            })
                            .image(settingsBll.getUrlAddress() + "/" + response.get(i).getimage())
                            .setScaleType(BaseSliderView.ScaleType.Fit);

                    sliderShow.addSlider(DefaultSliderView);
                }
                if (response.size() == 1) {
                    sliderShow.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
                } else {
                    sliderShow.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
                }
                sliderShow.setDuration(3000);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (getActivity() instanceof onCallBackQuickList)
            onCallBackQuickList = (onCallBackQuickList) getActivity();
    }

    private void quickList() {
        SettingsBll settingsBll = new SettingsBll(getActivity());
        ArrayList<String> list = new ArrayList<>();
//        if (settingsBll.isAccessBox()) {
            list.add("تخلیه صندوق");
            list.add("افزودن صندوق");
            list.add("آدرس ها");
            list.add("افزودن آدرس");
//        }
//        if (settingsBll.isAccessFlowerCrown()) {
            list.add("تاج گل");
//        }
//        if (settingsBll.isAccessFinancialAid()) {
            list.add("مشارکت");
//        }
//        if (settingsBll.isAccessSponsor()) {
            list.add("کمک نقدی");
//        }
        adapter1 = new QuickListAdapter(list, new onCallBackQuickList() {
            @Override
            public void goTo(String page) {
                onCallBackQuickList.goTo(page);
            }
        });
        row1.setAdapter(adapter1);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        row1.setLayoutManager(linearLayoutManager);

    }

}
