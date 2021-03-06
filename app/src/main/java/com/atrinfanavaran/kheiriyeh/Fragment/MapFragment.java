package com.atrinfanavaran.kheiriyeh.Fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.atrinfanavaran.kheiriyeh.Domain.BoxIncomeApi;
import com.atrinfanavaran.kheiriyeh.Domain.BoxNotEmptyApi;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Controller;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallbackGet;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;


public class MapFragment extends Fragment implements LocationListener, GoogleMap.OnMapLoadedCallback {

    private GoogleMap googlemap;
    private LatLng safecompPOS;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private ProgressBar progressBar;
    private Context context;
    private MapView mMapView;
    private TextView titleToolbar;
    private RadioGroup statusGroup;
    private RadioButton radio1, radio2, radio3, radio4, radio5;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        context = getActivity();

        LinearLayout filterBtn = getActivity().findViewById(R.id.filterButton);
        filterBtn.setVisibility(View.GONE);
        LinearLayout refreshBtn = getActivity().findViewById(R.id.refreshBtn);
        refreshBtn.setVisibility(View.GONE);

        titleToolbar = getActivity().findViewById(R.id.titleToolbar);
        titleToolbar.setText("آدرس ها");

        statusGroup = view.findViewById(R.id.radioButtonGroup);
        radio1 = view.findViewById(R.id.radioButton1);
        radio2 = view.findViewById(R.id.radioButton2);
        radio3 = view.findViewById(R.id.radioButton3);
        radio4 = view.findViewById(R.id.radioButton4);

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
                googlemap.setOnMyLocationChangeListener(myLocationChangeListener);
                // For dropping a marker at a point on the Map
//                LatLng sydney = new LatLng(32.7154899, 51.698559);
//                googlemap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                googlemap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                googlemap.setOnCameraIdleListener(() -> {
                    Log.i("moh3n", "onCameraIdle: ");

                });
                LatLng latlng = googlemap.getProjection().getVisibleRegion().latLngBounds.getCenter();
                Log.i("moh3n", "onMapReady: " + latlng.longitude);
                googlemap.setOnCameraIdleListener(() -> {
//
////                addItemsToMap(yourMarkerList);
                    final LatLng POS_center = googlemap.getCameraPosition().target;
                    Log.i("moh3n", "onMapReady: " + POS_center.longitude);
//
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

        statusGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioButton1:
                    getBoxesOnline("all");
                    break;
                case R.id.radioButton2:
                    getBoxesOnline("1");
                    break;
                case R.id.radioButton3:
                    getBoxesOnline("2");
                    break;
                case R.id.radioButton4:
                    getBoxesOnline("3");
                    break;
                case R.id.radioButton5:
                    getBoxesOnline("4");
                    break;
            }
        });

    }

    private void initView(View view) {
//        progressBar=view.findViewById(R.id.)
    }


//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        googlemap = googleMap;
//
//        googlemap.setOnCameraIdleListener(() -> {
//
////                addItemsToMap(yourMarkerList);
//            //     final LatLng POS_center = googlemap.getCameraPosition().target;
//
//
//        });
//        googlemap.setOnCameraMoveStartedListener(i -> {
//            googlemap.setOnMapLoadedCallback(this);
//            progressBar.setVisibility(View.VISIBLE);
//        });
//
//
//        googlemap.getUiSettings().setMyLocationButtonEnabled(true);
//        googlemap.getUiSettings().setZoomControlsEnabled(true);
//        googlemap.setOnMyLocationChangeListener(myLocationChangeListener);
//
//    }

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
            getBoxesOnline("all");
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
//        googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(safecompPOS, 12));
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

    private void getBoxesOnline(String status) {
//        progressBar.setVisibility(View.VISIBLE);
        Controller controller = new Controller(getActivity());

        googlemap.clear();

        if (!status.equals("4")) {
            controller.Get(BoxIncomeApi.class, null, 0, 0, true, new CallbackGet() {
                @Override
                public <T> void onSuccess(ArrayList<T> result, int count) {

                    ArrayList<BoxIncomeApi> response = new ArrayList<>((Collection<? extends BoxIncomeApi>) result);

                    for (int i = 0; i < response.size(); i++) {
                        if (response.get(i).getlat() == null || response.get(i).getlon() == null)
                            return;
                        double lat = Double.valueOf(response.get(i).getlat());
                        double lng = Double.valueOf(response.get(i).getlon());

                        LatLng location = new LatLng(lat, lng);
                        String statusStr = "";
                        float coloredMarker = BitmapDescriptorFactory.HUE_RED;
                        switch (response.get(i).getstatus()) {
                            case "1": {
                                statusStr = "عدم حضور";
                                coloredMarker = BitmapDescriptorFactory.HUE_ORANGE;
                                break;
                            }
                            case "2": {
                                statusStr = "عدم موجودی";
                                coloredMarker = BitmapDescriptorFactory.HUE_RED;
                                break;
                            }
                            case "3": {
                                statusStr = "تخلیه شده";
                                coloredMarker = BitmapDescriptorFactory.HUE_GREEN;
                                break;
                            }
                        }
                        String number = response.get(i).getnumber();
                        if (number == null) number = " ";

                        //set fot filter map
                        if (status.equals("all")) {
                            googlemap.addMarker(new MarkerOptions().position(location).snippet(statusStr).title(number).icon(BitmapDescriptorFactory.defaultMarker(coloredMarker)));
                        } else if (status.equals("1")) {
                            if (response.get(i).getstatus().equals("1")) {
                                googlemap.addMarker(new MarkerOptions().position(location).snippet(statusStr).title(number).icon(BitmapDescriptorFactory.defaultMarker(coloredMarker)));
                            }
                        } else if (status.equals("2")) {
                            if (response.get(i).getstatus().equals("2")) {
                                googlemap.addMarker(new MarkerOptions().position(location).snippet(statusStr).title(number).icon(BitmapDescriptorFactory.defaultMarker(coloredMarker)));
                            }
                        } else if (status.equals("3")) {
                            if (response.get(i).getstatus().equals("3")) {
                                googlemap.addMarker(new MarkerOptions().position(location).snippet(statusStr).title(number).icon(BitmapDescriptorFactory.defaultMarker(coloredMarker)));
                            }
                        }
                    }


//                progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(context, "خطا در دریافت اطلاعات", Toast.LENGTH_SHORT).show();
//                progressBar.setVisibility(View.GONE);
                }
            });
        }
        if (status.equals("all") || status.equals("4")) {

            controller.Get(BoxNotEmptyApi.class, null, 0, 0, true, new CallbackGet() {
                @Override
                public <T> void onSuccess(ArrayList<T> result, int count) {

                    ArrayList<BoxNotEmptyApi> response = new ArrayList<>((Collection<? extends BoxNotEmptyApi>) result);

                    for (int i = 0; i < response.size(); i++) {
                        if (response.get(i).getlat() == null || response.get(i).getlon() == null)
                            return;
                        double lat = Double.valueOf(response.get(i).getlat());
                        double lng = Double.valueOf(response.get(i).getlon());

                        LatLng location = new LatLng(lat, lng);

                        String statusStr = "تخلیه نشده";
                        float coloredMarker = BitmapDescriptorFactory.HUE_AZURE;

                        String number = response.get(i).getnumber();
                        if (number == null) number = " ";
                        googlemap.addMarker(new MarkerOptions().position(location).snippet(statusStr).title(number).icon(BitmapDescriptorFactory.defaultMarker(coloredMarker)));
                    }


//                progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(context, "خطا در دریافت اطلاعات", Toast.LENGTH_SHORT).show();
//                progressBar.setVisibility(View.GONE);
                }
            });
        }
    }


}
