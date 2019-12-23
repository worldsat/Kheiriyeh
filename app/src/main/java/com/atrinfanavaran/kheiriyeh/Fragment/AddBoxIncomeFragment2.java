package com.atrinfanavaran.kheiriyeh.Fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.atrinfanavaran.kheiriyeh.Domain.BoxIncome;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxIncome2;
import com.atrinfanavaran.kheiriyeh.R;
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


public class AddBoxIncomeFragment2 extends Fragment implements LocationListener, GoogleMap.OnMapLoadedCallback {
    private Button btn2Save;
    private onCallBackBoxIncome2 onCallBackBoxIncome2;
    private GoogleMap googlemap;
    private LatLng safecompPOS;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private ProgressBar progressBar;
    private Context context;
    private MapView mMapView;
    private BoxIncome boxIncome;
    private boolean editable = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle = getArguments();
        if (bundle != null) {
            boxIncome = (BoxIncome) bundle.get("BoxIncome");
            editable = (boolean) bundle.get("editable");

            if (boxIncome != null) {
                Toast.makeText(getActivity(), boxIncome.getprice(), Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_discharge2, container, false);

        return rootView;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn2Save = view.findViewById(R.id.btn_2);
        btn2Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boxIncome == null) {
                    Toast.makeText(context, "خطا در انجام عملیات", Toast.LENGTH_SHORT).show();

                } else {
                    if (boxIncome.getlat() == null) {
                        Toast.makeText(context, "خطا در دریافت موقعیت", Toast.LENGTH_SHORT).show();
                    } else {
                        onCallBackBoxIncome2.SaveBoxIncome2(boxIncome,editable);
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

                googlemap.setOnCameraMoveStartedListener(i -> {
//                    googlemap.setOnMapLoadedCallback(this);
//                    progressBar.setVisibility(View.VISIBLE);
                });
                googlemap.getUiSettings().setMyLocationButtonEnabled(true);
                googlemap.getUiSettings().setZoomControlsEnabled(true);
//                googlemap.setOnMyLocationChangeListener(myLocationChangeListener);
//                googlemap.setOnCameraIdleListener(() -> {
//                    Log.i("moh3n", "onCameraIdle: ");

//                });
                GoogleMap.OnMyLocationChangeListener myLocationChangeListener = location -> {
                    LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                    double lat = loc.latitude;
                    double lng = loc.longitude;
                    String latStr = String.valueOf(lat);
                    String lngStr = String.valueOf(lng);
                    boxIncome.setlat(latStr);
                    boxIncome.setlon(lngStr);

                    googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));

                    Log.i("moh3n", "onMyLocationChange: ");
                };
                googlemap.setOnMyLocationChangeListener(myLocationChangeListener);
                // For dropping a marker at a point on the Map
//                LatLng sydney = new LatLng(-34, 151);
//                googlemap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                googlemap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
        if (getActivity() instanceof onCallBackBoxIncome2)
            onCallBackBoxIncome2 = (onCallBackBoxIncome2) getActivity();
    }


}
