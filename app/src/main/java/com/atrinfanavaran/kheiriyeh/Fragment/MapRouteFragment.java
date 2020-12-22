package com.atrinfanavaran.kheiriyeh.Fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.atrinfanavaran.kheiriyeh.Domain.Route;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxIncome2;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackRoute2;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.Domian.RouteR;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;


public class MapRouteFragment extends Fragment implements LocationListener, GoogleMap.OnMapLoadedCallback {
    private Button btn2Save;
    private onCallBackBoxIncome2 onCallBackBoxIncome2;
    private GoogleMap googlemap;
    private LatLng safecompPOS;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private ProgressBar progressBar;
    private Context context;
    private onCallBackRoute2 onCallBack;
    private MapView mMapView;
    private Route route;
    private boolean editable = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle = getArguments();
        if (bundle != null) {
            route = (Route) bundle.get("Route");
            editable = (boolean) bundle.get("editable");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.map_fragment_route, container, false);

        return rootView;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn2Save = view.findViewById(R.id.btn_2);
        LinearLayout filterBtn= getActivity().findViewById(R.id.filterButton);
        filterBtn.setVisibility(View.GONE);
        LinearLayout refreshBtn = getActivity().findViewById(R.id.refreshBtn);
        refreshBtn.setVisibility(View.GONE);

        btn2Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (route == null) {
                    Toast.makeText(context, "خطا در انجام عملیات", Toast.LENGTH_SHORT).show();

                } else {
                    if (route.getlat() == null) {
                        Toast.makeText(context, "خطا در دریافت موقعیت", Toast.LENGTH_SHORT).show();
                    } else {
                        RouteR routeR = new RouteR();
                        routeR.code = route.getcode();
//                        routeR.day = route.getday();
                        routeR.address = route.getaddress();
                        routeR.lat = route.getlat();
                        routeR.lon = route.getlon();

                        if (editable) {
                            routeR.id = route.getid();
                        } else {
                            routeR.isNew = "true";
                        }
                        onCallBack.SaveRoute2(routeR, editable);
                    }
                }


//                onCallBackBoxIncome2.SaveBoxIncome2();
            }
        });


        context = getActivity();

        String languageToLoad = "fa_IR";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());
        mMapView = (MapView) view.findViewById(R.id.mapFragment);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googlemap = mMap;

                // For showing a move to my location button
                googlemap.setMyLocationEnabled(true);

                googlemap.getUiSettings().setMyLocationButtonEnabled(true);
                googlemap.getUiSettings().setZoomControlsEnabled(true);

                googlemap.setOnMyLocationChangeListener(myLocationChangeListener);

                googlemap.setOnCameraIdleListener(() -> {

                    final LatLng loc = googlemap.getCameraPosition().target;
                    double lat = loc.latitude;
                    double lng = loc.longitude;
                    String latStr = String.valueOf(lat);
                    String lngStr = String.valueOf(lng);
                    route.setlat(latStr);
                    route.setlon(lngStr);

                });

            }
        });

        progressBar = view.findViewById(R.id.progressBar2);

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(connectionListener)
                .addOnConnectionFailedListener(connectionFailedListener)
                .addApi(LocationServices.API)
                .build();

        createLocationRequest();

    }

    GoogleApiClient.ConnectionCallbacks connectionListener = new GoogleApiClient.ConnectionCallbacks() {

        @Override
        public void onConnectionSuspended(int arg0) {
            Toast.makeText(context, "Connection Suspended", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onConnected(Bundle arg0) {

            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                googlemap.setMyLocationEnabled(true);

                if (mLastLocation != null && safecompPOS == null) {

                    safecompPOS = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(safecompPOS, 12));

                    //   map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                }
            }

            safecompPOS = new LatLng(32.656358, 51.669068);
            googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(safecompPOS, 12));

        }
    };

    GoogleApiClient.OnConnectionFailedListener connectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {

        @Override
        public void onConnectionFailed(ConnectionResult arg0) {

        }
    };


    void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapLoaded() {
        //TODO: Hide your progress indicator
        // wait.dismiss();
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        Log.i("moh3n", "onLocationChanged: " + mLastLocation);
        safecompPOS = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(safecompPOS, 12));
    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
//            mMarker = mMap.addMarker(new MarkerOptions().position(loc));
            if (googlemap != null) {
                googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (getActivity() instanceof onCallBackRoute2)
            onCallBack = (onCallBackRoute2) getActivity();
    }


}
