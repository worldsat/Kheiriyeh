package com.atrinfanavaran.kheiriyeh.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atrinfanavaran.kheiriyeh.R;


public class NavigationDrawerFragment extends Fragment {

    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ImageView drawer_pic1;
    private TextView drawer1, drawer2;
    private ActionBarDrawerToggle drawer_toggle;

    private boolean m_userLearnedDrawer;
    private boolean m_fromSavedInstanceState;


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

        drawer1 = (TextView) view.findViewById(R.id.exit_drawer);



        drawer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sp.edit().clear().apply();

                Toast.makeText(getActivity(), "از حساب خود با موفقیت خارج شدید", Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(getActivity(), LoginCartableActivity.class);
//                startActivity(intent);

            }
        });
//        drawer2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                Intent intent = new Intent(getActivity(), LoginKnowledgeActivity.class);
////                startActivity(intent);
//
//            }
//        });
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
}

