package com.atrinfanavaran.kheiriyeh.Fragment;


import androidx.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.atrinfanavaran.kheiriyeh.Activity.AboutActivity;
import com.atrinfanavaran.kheiriyeh.Activity.ActivityDesigner;
import com.atrinfanavaran.kheiriyeh.Activity.ContactUsActivity;
import com.atrinfanavaran.kheiriyeh.Activity.LoginActivity;
import com.atrinfanavaran.kheiriyeh.Activity.RulesActivity;
import com.atrinfanavaran.kheiriyeh.Activity.StirActivity;
import com.atrinfanavaran.kheiriyeh.BuildConfig;
import com.atrinfanavaran.kheiriyeh.Domain.BoxApi;
import com.atrinfanavaran.kheiriyeh.Domain.BoxIncomeApi;
import com.atrinfanavaran.kheiriyeh.Domain.RouteApi;
import com.atrinfanavaran.kheiriyeh.Kernel.Activity.BaseActivity;
import com.atrinfanavaran.kheiriyeh.Kernel.Bll.SettingsBll;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Controller;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackGet;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackOperation;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.AppDatabase;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxIncomeR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.RouteR;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class NavigationDrawerFragment extends Fragment {

    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ImageView drawer_pic1;
    private TextView drawer1, drawer2;
    private ActionBarDrawerToggle drawer_toggle;
    private LinearLayout btn1, btn2, btn3, btn4, btn5, btn6;
    private boolean m_userLearnedDrawer;
    private boolean m_fromSavedInstanceState;
    private AppDatabase db;
    private Controller controller;
    private BaseActivity baseActivity;
    private ImageView imageView;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    public static void saveToPreferences(Context con, String preferenceName, String preferenceValue) {
        SharedPreferences sp = con.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context con, String preferenceName, String preferenceValue) {
        SharedPreferences sp = con.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);

        return sp.getString(preferenceName, preferenceValue);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_userLearnedDrawer = Boolean.valueOf(
                //inja mitan baz ya baste bodan navigation drawer dar zaman startup ra tanzim kard
                readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "true")
        );

        if (savedInstanceState != null) {
            m_fromSavedInstanceState = true;
        }


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SharedPreferences sp = getActivity().getSharedPreferences("Token", 0);

        db = Room.databaseBuilder(getActivity(),
                AppDatabase.class, "RoomDb").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        controller = new Controller(getActivity());
        baseActivity = new BaseActivity();

        SettingsBll settingsBll = new SettingsBll(getActivity());


        TextView name = view.findViewById(R.id.name);
        TextView code = view.findViewById(R.id.code);
        name.setText(settingsBll.getName());
        code.setText("کد: " + settingsBll.getUserId());

        drawer1 = (TextView) view.findViewById(R.id.exit_drawer);
        drawer2 = (TextView) view.findViewById(R.id.getDataBtn);
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        btn4 = view.findViewById(R.id.btn4);
        btn5 = view.findViewById(R.id.btn5);
        btn6 = view.findViewById(R.id.btn6);
        imageView = view.findViewById(R.id.logo);

        btn1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), StirActivity.class);
            startActivity(intent);
        });
        btn2.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RulesActivity.class);
            startActivity(intent);
        });
        btn3.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ContactUsActivity.class);
            startActivity(intent);
        });
        btn4.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutActivity.class);
            startActivity(intent);
        });
        btn5.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ActivityDesigner.class);
            startActivity(intent);
        });
        btn6.setOnClickListener(v -> {
            settingsBll.logout();
            getActivity().finish();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });


        drawer1.setOnClickListener(v -> sendDischargeRoute());
        drawer2.setOnClickListener(v -> getRoutes());

        TextView version = view.findViewById(R.id.version);
        String versionName = BuildConfig.VERSION_NAME;
        version.setText("اپلیکیشن قاصدک نسخه " + versionName);


        setLogo();
    }


    public void setUp(int fragmentId, DrawerLayout dl, final Toolbar toolbar) {
        View container_view = getActivity().findViewById(fragmentId);

        DrawerLayout my_drawer_layout = dl;

        drawer_toggle = new ActionBarDrawerToggle(getActivity(), dl, toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!m_userLearnedDrawer) {
                    m_userLearnedDrawer = true;
                    saveToPreferences(getActivity(), PREF_FILE_NAME,
                            m_userLearnedDrawer + "");
                }

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                getActivity().invalidateOptionsMenu();
            }

        };


        if (!m_userLearnedDrawer && !m_fromSavedInstanceState) {
            my_drawer_layout.openDrawer(container_view);
        }


        my_drawer_layout.setDrawerListener(drawer_toggle);

        my_drawer_layout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        drawer_toggle.setDrawerIndicatorEnabled(true);
                        drawer_toggle.syncState();
                    }
                }
        );
    }

    private void sendDischargeRoute() {
        JSONObject params = null;
        JSONArray params2 = new JSONArray();
        List<RouteR> data = db.RouteDao().getAllNew();

        for (int i = 0; i < data.size(); i++) {
            try {
                params = new JSONObject();
//                params.put("id", data.get(i).id);
                params.put("code", data.get(i).code);
                params.put("day", data.get(i).day);
                params.put("address", data.get(i).address);
                params.put("lat", data.get(i).lat);
                params.put("lon", data.get(i).lon);

                params2.put(params);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (params2 != null) {
            Log.i("moh3n", "sendBoxIncome: " + params2.toString());
        }
        BaseActivity baseActivity = new BaseActivity();
        MaterialDialog wait = baseActivity.alertWaiting2(getActivity(), "در حال ارسال مسیر ها...");
        wait.show();
        controller.Operation("", RouteApi.class, getActivity(), params2.toString(), new CallbackOperation() {
            @Override
            public void onSuccess(String result) {
                Log.i("moh3n", "onSuccess: " + result);
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                db.RouteDao().deleteAll();
                wait.dismiss();
                sendBox();
            }

            @Override
            public void onError(String error) {
                Log.i("moh3n", "onError: " + error);
                wait.dismiss();
                Toast.makeText(getActivity(), "خطا در ارسال اطلاعات", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendBox() {
        JSONObject params = null;
        JSONArray params2 = new JSONArray();
        List<BoxR> data = db.BoxDao().getAllNew();

        for (int i = 0; i < data.size(); i++) {
            try {
                params = new JSONObject();
//                params.put("code", data.get(i).code);
                params.put("number", Integer.valueOf(data.get(i).number));
                params.put("fullName", data.get(i).fullName);
                params.put("mobile", new BigInteger(data.get(i).mobile));
                params.put("assignmentDate", data.get(i).assignmentDate);
                params.put("address", data.get(i).address);
                params.put("lat", Double.valueOf(data.get(i).lat));
                params.put("lon", Double.valueOf(data.get(i).lon));
                params.put("dischargeRouteId", Integer.valueOf(data.get(i).dischargeRouteId));

                params2.put(params);
            } catch (Exception e) {
                Log.i("moh3n", "sendBox: "+e);
                Toast.makeText(baseActivity, "خطا در پارامتر های ارسالی اطلاعات صندوق ها", Toast.LENGTH_SHORT).show();
            }
        }


        MaterialDialog wait = baseActivity.alertWaiting2(getActivity(), "در حال ارسال صندوق ها...");
        wait.show();
        controller.Operation("", BoxApi.class, getActivity(), params2.toString(), new CallbackOperation() {
            @Override
            public void onSuccess(String result) {
                Log.i("moh3n", "onSuccess: " + result);
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                db.BoxDao().deleteAll();
                wait.dismiss();
                sendBoxIncome();
            }

            @Override
            public void onError(String error) {
                Log.i("moh3n", "onError: " + error);
                wait.dismiss();
                Toast.makeText(getActivity(), "خطا در ارسال اطلاعات", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendBoxIncome() {
        JSONObject params = null;
        JSONArray params2 = new JSONArray();
        List<BoxIncomeR> boxIncome = db.BoxIncomeDao().getAll();

        for (int i = 0; i < boxIncome.size(); i++) {
            try {
                params = new JSONObject();

                try {
                    Integer intt = Integer.valueOf(boxIncome.get(i).price);
                    params.put("price", boxIncome.get(i).price);
                } catch (Exception e) {
                    params.put("price", 0);
                }
                try {
                    params.put("number", Integer.valueOf(boxIncome.get(i).number));
                    params.put("status", Integer.valueOf(boxIncome.get(i).status));
                    params.put("lon", Double.valueOf(boxIncome.get(i).lon));
                    params.put("lat", Double.valueOf(boxIncome.get(i).lat));
                    params.put("assignmentDate", boxIncome.get(i).assignmentDate);
                } catch (Exception e) {
                    Toast.makeText(baseActivity, "خطا در پارامتر های ارسالی  اطلاعات تخلیه ها", Toast.LENGTH_SHORT).show();
                }
                params2.put(params);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (params2 != null) {
            Log.i("moh3n", "sendBoxIncome: " + params2.toString());
        }

        MaterialDialog wait = baseActivity.alertWaiting2(getActivity(), "در حال ارسال تخلیه ها...");
        wait.show();
        controller.Operation("", BoxIncomeApi.class, getActivity(), params2.toString(), new CallbackOperation() {
            @Override
            public void onSuccess(String result) {
                Log.i("moh3n", "onSuccessSendBoxIncome: " + result);
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                db.BoxIncomeDao().deleteAll();
                wait.dismiss();
            }

            @Override
            public void onError(String error) {
                Log.i("moh3n", "onError: " + error);
                wait.dismiss();
                Toast.makeText(getActivity(), "خطا در ارسال اطلاعات", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getBoxIncome() {
        MaterialDialog wait = baseActivity.alertWaiting2(getActivity(), "در حال دریافت اطلاعات تخلیه شده ها");
        wait.show();
        controller.Get(BoxIncomeApi.class, null, 0, 0, true, new CallbackGet() {
            @Override
            public <T> void onSuccess(ArrayList<T> result, int count) {

                List<BoxIncomeApi> response = (List<BoxIncomeApi>) result;

                for (int i = 0; i < response.size(); i++) {
                    BoxIncomeR boxIncomeR = new BoxIncomeR();
//                    boxIncomeR.id = Integer.valueOf(response.get(i).getid());
                    boxIncomeR.price = response.get(i).getprice();
                    boxIncomeR.status = response.get(i).getstatus();
                    boxIncomeR.assignmentDate = response.get(i).getassignmentDate();
                    boxIncomeR.lon = response.get(i).getlon();
                    boxIncomeR.lat = response.get(i).getlat();
                    boxIncomeR.number = response.get(i).getnumber();

                    db.BoxIncomeDao().insertBoxIncome(boxIncomeR);

                }
                String Str = "تعداد " + response.size() + " رکورد با موفقیت ذخیره شد";
                Toast.makeText(getActivity(), Str, Toast.LENGTH_SHORT).show();

                wait.dismiss();
            }

            @Override
            public void onError(String error) {
                Log.i("mo3h", "onError: " + error);
                wait.dismiss();
            }
        });
    }

    private void getBox() {
        MaterialDialog wait = baseActivity.alertWaiting2(getActivity(), "در حال دریافت اطلاعات صندوق ها");
        wait.show();
        controller.Get(BoxApi.class, null, 0, 0, true, new CallbackGet() {
            @Override
            public <T> void onSuccess(ArrayList<T> result, int count) {

                List<BoxApi> response = (List<BoxApi>) result;

                for (int i = 0; i < response.size(); i++) {
                    BoxR data = new BoxR();
                    data.id = Integer.valueOf(response.get(i).getid());
                    data.boxId = Integer.valueOf(response.get(i).getid());
                    data.fullName = response.get(i).getfullName();
                    data.mobile = response.get(i).getmobile();
                    data.assignmentDate = response.get(i).getassignmentDate();
                    data.code = response.get(i).getcode();
                    data.number = response.get(i).getnumber();
                    data.address = response.get(i).getaddress();
                    data.lat = response.get(i).getlat();
                    data.lon = response.get(i).getlon();
                    data.dischargeRouteId = response.get(i).getdischargeRouteId();

                    try {
                        db.BoxDao().insertBox(data);
                    } catch (Exception e) {
                        Log.i("moh3n", "errorInsert: " + e);
                    }
                }
                String Str = "تعداد " + response.size() + " رکورد از صندوق ها با موفقیت ذخیره شد";
                Toast.makeText(getActivity(), Str, Toast.LENGTH_SHORT).show();

                wait.dismiss();
            }

            @Override
            public void onError(String error) {
                Log.i("mo3h", "onError: " + error);
                wait.dismiss();
            }
        });
    }

    private void getRoutes() {
        MaterialDialog wait = baseActivity.alertWaiting2(getActivity(), "در حال دریافت اطلاعات مسیرها");
        wait.show();
        controller.Get(RouteApi.class, null, 0, 0, true, new CallbackGet() {
            @Override
            public <T> void onSuccess(ArrayList<T> result, int count) {

                List<RouteApi> response = (List<RouteApi>) result;

                for (int i = 0; i < response.size(); i++) {
                    RouteR data = new RouteR();
                    data.id = Integer.valueOf(response.get(i).getid());
                    data.code = response.get(i).getcode();
                    data.address = response.get(i).getaddress();
                    data.day = response.get(i).getday();
                    try {
                        db.RouteDao().insertBox(data);
                    } catch (Exception e) {
                        Log.i("moh3n", "errorInsert: " + e);
                    }
                }
                String Str = "تعداد " + response.size() + " رکورد از مسیر ها با موفقیت ذخیره شد";
                Toast.makeText(getActivity(), Str, Toast.LENGTH_SHORT).show();

                wait.dismiss();
                getBox();

            }

            @Override
            public void onError(String error) {
                Log.i("mo3h", "onError: " + error);
                wait.dismiss();
            }
        });
    }

    private void setLogo() {

        SettingsBll settingsBll = new SettingsBll(getActivity());

        Glide.with(getActivity())
                .load(settingsBll.getUrlAddress() + "/" + settingsBll.getLogoAddress())
                .into(imageView);

    }
}

