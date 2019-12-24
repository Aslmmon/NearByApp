package com.nearbyapp.maysa.nearbyapp.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.nearbyapp.maysa.nearbyapp.R;
import com.nearbyapp.maysa.nearbyapp.adapters.AllNearByPlacesAdapter;
import com.nearbyapp.maysa.nearbyapp.datamodels.Venue;
import com.nearbyapp.maysa.nearbyapp.utilis.ConnectionLiveData;
import com.nearbyapp.maysa.nearbyapp.utilis.Constants;
import com.nearbyapp.maysa.nearbyapp.viewmodels.MainPageViewModel;
import com.victor.loading.rotate.RotateLoading;
import java.util.ArrayList;
import java.util.List;
import static android.location.GpsStatus.GPS_EVENT_STARTED;
import static android.location.GpsStatus.GPS_EVENT_STOPPED;

public class MainPage extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener , LocationListener {


    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    Location mLastKnownLocation;
    private LocationManager manager;
    private MainPageViewModel mainPageViewModel;
    private RecyclerView all_nearby_places ;
    private ImageView no_data_image ;
    private TextView no_data ;
    private List<Venue> all_nearby_places_list;
    private  boolean haveData = false ;
    private AllNearByPlacesAdapter allNearByPlacesAdapter ;
    private RotateLoading rotateloading;
    private static boolean GPS_ENABLED = false ;
    private boolean firsttime = true ;
    SharedPreferences sharedpreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        init();
        checkConnection();

    }

    private void init(){
        sharedpreferences =  getSharedPreferences("MY_DATA", Context.MODE_PRIVATE);
        boolean last_user_choice = sharedpreferences.getBoolean("USER_CHOICE", false);

        Log.d("test", String.valueOf(last_user_choice));
        Switch single_or_real_time = findViewById(R.id.single_or_real_time);

        single_or_real_time.setChecked(last_user_choice);
        rotateloading = findViewById(R.id.rotateloading);
        all_nearby_places_list= new ArrayList<>();
        all_nearby_places= findViewById(R.id.all_nearby_places);
        no_data = findViewById(R.id.no_data);
        no_data_image=findViewById(R.id.no_data_image);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mainPageViewModel = ViewModelProviders.of
                (this).get(MainPageViewModel.class);
        single_or_real_time.setOnCheckedChangeListener(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            } else {
                mLocationPermissionGranted = true;
                mLastKnownLocation = null;
            }
        }
    }



    public void statusCheck() {
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            GPS_ENABLED = false ;
            buildAlertMessageNoGps();

        } else {
            GPS_ENABLED = true ;
            getLocation(true);
        }
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            Log.d("permission", String.valueOf(mLocationPermissionGranted));

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            Log.d("OKAY", String.valueOf(mLocationPermissionGranted));
        }
    }

    private void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
                })
                .setNegativeButton("No", (dialog, id) -> {
                    GPS_ENABLED = false;
                    dialog.cancel();
                    Toast.makeText(this,"Your GPS seems to be disabled",Toast.LENGTH_SHORT).show();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    private void callGetAllNearByMethod( String ll) {
        rotateloading.start();
        all_nearby_places_list.clear();
        double altAcc = 10000.0;
        mainPageViewModel.getAllNearByPlaces(Constants.CLIENT_ID,
                Constants.CLIENT_SECRET, ll,
                "20192312", altAcc).observe(this, venue -> {
                    rotateloading.stop();
                    rotateloading.setVisibility(View.GONE);
            assert venue != null;
            if (venue.getIsEmpty().equals("has_data")) {
                        //there is data
                        haveData = true ;
                        no_data.setVisibility(View.GONE);
                        no_data_image.setVisibility(View.GONE);
                        all_nearby_places_list.add(venue);
                        allNearByPlacesAdapter = new
                                AllNearByPlacesAdapter(all_nearby_places_list);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainPage.this);
                        all_nearby_places.setLayoutManager(linearLayoutManager);
                        all_nearby_places.setAdapter(allNearByPlacesAdapter);
                        all_nearby_places.setVisibility(View.VISIBLE);

                    }
                    //error
                    else if (venue.getIsEmpty().equals("no_data")) {
                        no_data.setText("Something went wrong !!");
                        no_data.setVisibility(View.VISIBLE);
                        all_nearby_places.setVisibility(View.GONE);
                        no_data_image.setVisibility(View.VISIBLE);
                        no_data_image.setImageDrawable(getResources().getDrawable(R.drawable.no_internet));

                    }

                    else {
                        // no data found
                        no_data.setText("No data found !");
                        no_data_image.setImageDrawable(getResources().getDrawable(R.drawable.no_data));
                        no_data.setVisibility(View.VISIBLE);
                        no_data_image.setVisibility(View.VISIBLE);
                        all_nearby_places.setVisibility(View.GONE);
                    }
                });
    }

    private void checkConnection() {
        ConnectionLiveData connectionLiveData = new ConnectionLiveData(MainPage.this);
        connectionLiveData.observe(MainPage.this, connection -> {
            if (connection != null) {
                //network is  connected
                if (connection.getIsConnected()) {
                    if (!haveData) {
                        no_data_image.setVisibility(View.GONE);
                        no_data.setVisibility(View.GONE);
                        all_nearby_places.setVisibility(View.GONE);
                        getLocationPermission();
                        statusCheck();
                    }
                }
                //network is not connected
                else {
                    if (!haveData) {
                            no_data.setText("Something went wrong !!");
                            no_data.setVisibility(View.VISIBLE);
                            all_nearby_places.setVisibility(View.GONE);
                            no_data_image.setVisibility(View.VISIBLE);
                            no_data_image.setImageDrawable(getResources().getDrawable(R.drawable.no_internet));

                    }

                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked){
            getLocation(true);
        }
        else {
            getLocation(false);
        }
        sharedpreferences.edit().putBoolean("USER_CHOICE",isChecked).apply();


    }

    private void getLocation(boolean real_time) {
        if(!GPS_ENABLED){
            Toast.makeText(MainPage.this,"Please open your GPS to " +
                            "get nearby places around your location..",
                    Toast.LENGTH_SHORT).show();
        }
          try {
                manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                manager.addGpsStatusListener(event -> {
                    switch (event) {
                        case GPS_EVENT_STARTED:
                            GPS_ENABLED = true ;
                            Log.d("GPS_ENABLED", String.valueOf(GPS_ENABLED));
                            break;
                        case GPS_EVENT_STOPPED:
                            GPS_ENABLED = false ;
                            Log.d("GPS_ENABLED", String.valueOf(GPS_ENABLED));
//                            Toast.makeText(MainPage.this,"Your GPS seems to be disabled",
//                                    Toast.LENGTH_SHORT).show();
                            break;
                    }
                });

                if (real_time) {
                    manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            0, 500, this);
                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            0, 500, this);
                } else {
                    manager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,
                            this, null);
                    manager.requestSingleUpdate(LocationManager.GPS_PROVIDER,
                            this, null);

                }

            } catch (SecurityException e) {
                e.printStackTrace();
            }

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastKnownLocation = location ;
        String userLL  = location.getLatitude() + "," +
                location.getLongitude();
        callGetAllNearByMethod(userLL);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("provider", String.valueOf(provider));

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("provider", String.valueOf(provider));

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("provider", String.valueOf(provider));

    }

    @Override
    protected void onResume() {

        if (firsttime) {
            firsttime = false;
        } else {
            boolean test = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (test) {
                getLocationPermission();
                statusCheck();
            }
        }
        super.onResume();
    }


}
