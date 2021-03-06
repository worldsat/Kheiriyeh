package com.atrinfanavaran.kheiriyeh.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.room.Update;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.atrinfanavaran.kheiriyeh.Domain.Box;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackAddBox2;
import com.atrinfanavaran.kheiriyeh.Interface.onCallBackBoxIncome2;
import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxR;
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


public class MapBoxFragment extends Fragment implements LocationListener, GoogleMap.OnMapLoadedCallback {
    private Button btn2Save;
    private onCallBackBoxIncome2 onCallBackBoxIncome2;
    private GoogleMap googlemap;
    private LatLng safecompPOS;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private ProgressBar progressBar;
    private Context context;
    private onCallBackAddBox2 onCallBack;
    private MapView mMapView;
    private Box box;
    private boolean editable = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle = getArguments();
        if (bundle != null) {
            box = (Box) bundle.get("Box");
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
        LinearLayout filterBtn = getActivity().findViewById(R.id.filterButton);
        filterBtn.setVisibility(View.GONE);
        LinearLayout refreshBtn = getActivity().findViewById(R.id.refreshBtn);
        refreshBtn.setVisibility(View.GONE);


        btn2Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (box == null) {
                    Toast.makeText(context, "خطا در انجام عملیات", Toast.LENGTH_SHORT).show();

                } else {
                    if (box.getLat() == null) {
                        Toast.makeText(context, "خطا در دریافت موقعیت", Toast.LENGTH_SHORT).show();
                    } else {

                        BoxR boxR = new BoxR();
                        boxR.code = box.getCode();

                        boxR.assignmentDate = box.getassignmentDate();
                        boxR.mobile = box.getMobile();
                        boxR.address = box.getAddress();
                        boxR.fullName = box.getFullName();
                        boxR.lat = box.getLat();
                        boxR.lon = box.getLng();
                        boxR.number = box.getNumber();
                        boxR.day = box.getday();
                        boxR.boxKind = box.getboxKind();
                        boxR.dischargeRouteId = box.getDischargeRouteId();
                        boxR.guidDischargeRoute = box.getguidDischargeRoute();
                        boxR.guidBox = box.getguidBox();
                        if (editable) {
                            boxR.id = box.getBoxId();
                        } else {
                            boxR.isNew = "true";
                        }
                        onCallBack.SaveBox2(boxR, editable);
                    }
                }


//                onCallBackBoxIncome2.SaveBoxIncome2();
            }
        });


        context = getActivity();

       /* String languageToLoad = "fa_IR";
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
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Toast.makeText(context, "دسترسی برای دریافت موقعیت داده نشده", Toast.LENGTH_SHORT).show();
                    return;
                }
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
                    box.setLat(latStr);
                    box.setLng(lngStr);

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
*/
    }

    GoogleApiClient.ConnectionCallbacks connectionListener = new GoogleApiClient.ConnectionCallbacks() {

        @Override
        public void onConnectionSuspended(int arg0) {
            Toast.makeText(context, "Connection Suspended", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onConnected(Bundle arg0) {
            try {
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//                googlemap.setMyLocationEnabled(true);

                    if (mLastLocation != null && safecompPOS == null) {

                        safecompPOS = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                        if (googlemap != null) {
                            googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(safecompPOS, 12));
                        }
                        //   map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                    }
                }

                safecompPOS = new LatLng(32.656358, 51.669068);
                if (googlemap != null) {
                    googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(safecompPOS, 12));
                }
            } catch (Exception e) {

                Toast.makeText(context, "خطا در زوم نقشه", Toast.LENGTH_SHORT).show();
            }


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
//        mGoogleApiClient.connect();
    }

    public void onStop() {
        super.onStop();
//        if (mGoogleApiClient.isConnected()) {
//            mGoogleApiClient.disconnect();
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
//        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        mMapView.onLowMemory();
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
        if (getActivity() instanceof onCallBackAddBox2) {
            onCallBack = (onCallBackAddBox2) getActivity();
            //******************
            BoxR boxR = new BoxR();
            boxR.code = box.getCode();

            boxR.assignmentDate = box.getassignmentDate();
            boxR.mobile = box.getMobile();
            boxR.address = box.getAddress();
            boxR.fullName = box.getFullName();
            boxR.lat ="0";
            boxR.lon = "0";
            boxR.number = box.getNumber();
            boxR.day = box.getday();
            boxR.boxKind = box.getboxKind();
            boxR.dischargeRouteId = box.getDischargeRouteId();
            boxR.guidDischargeRoute = box.getguidDischargeRoute();
            boxR.guidBox = box.getguidBox();
            if (editable) {
                boxR.id = box.getBoxId();
            } else {
                boxR.isNew = "true";
            }
            onCallBack.SaveBox2(boxR, editable);
            //**************************************
        }
    }


}
