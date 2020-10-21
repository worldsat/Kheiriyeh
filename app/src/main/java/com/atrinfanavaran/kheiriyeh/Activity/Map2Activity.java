package com.atrinfanavaran.kheiriyeh.Activity;

import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.atrinfanavaran.kheiriyeh.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;


import java.util.Locale;

public class Map2Activity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapLoadedCallback {
    private GoogleMap googlemap;
    private LatLng safecompPOS;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String languageToLoad = "fa_IR";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_map2);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        progressBar = findViewById(R.id.progressBar2);

        MapFragment mapfragmnets = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapfragmnets.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(connectionListener)
                .addOnConnectionFailedListener(connectionFailedListener)
                .addApi(LocationServices.API)
                .build();

        createLocationRequest();    //tanzimat & deghat gps

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googlemap = googleMap;

        googlemap.setOnCameraIdleListener(() -> {

//                addItemsToMap(yourMarkerList);
            //     final LatLng POS_center = googlemap.getCameraPosition().target;


        });
        googlemap.setOnCameraMoveStartedListener(i -> {
            googlemap.setOnMapLoadedCallback(Map2Activity.this);
            progressBar.setVisibility(View.VISIBLE);
        });


        googlemap.getUiSettings().setMyLocationButtonEnabled(true);
        googlemap.getUiSettings().setZoomControlsEnabled(true);
        googlemap.setOnMyLocationChangeListener(myLocationChangeListener);

    }

    GoogleApiClient.ConnectionCallbacks connectionListener = new GoogleApiClient.ConnectionCallbacks() {

        @Override
        public void onConnectionSuspended(int arg0) {
            Toast.makeText(Map2Activity.this, "Connection Suspended", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onConnected(Bundle arg0) {

            if (ContextCompat.checkSelfPermission(Map2Activity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
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
        Log.i("moh3n", "onLocationChanged: "+mLastLocation);
        safecompPOS = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(safecompPOS, 12));
    }
    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
//            mMarker = mMap.addMarker(new MarkerOptions().position(loc));
            if(googlemap != null){
                googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            }
        }
    };
}
