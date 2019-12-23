package com.nearbyapp.maysa.nearbyapp.utilis;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.foursquare.pilgrim.LogLevel;
import com.foursquare.pilgrim.PilgrimSdk;
import com.nearbyapp.maysa.nearbyapp.R;

public class MyApplication extends Application {

        SharedPreferences sharedpreferences ;

        @Override
        public void onCreate() {
            super.onCreate();
            PilgrimSdk.Builder builder = new PilgrimSdk.Builder(this)
                    .consumer(Constants.CLIENT_ID, Constants.CLIENT_SECRET)
                    .logLevel(LogLevel.DEBUG);
            PilgrimSdk.with(builder);
            sharedpreferences =  getSharedPreferences("USER_CHOCIES", Context.MODE_PRIVATE);
        }


        @Override
        protected void attachBaseContext(Context base) {
            super.attachBaseContext(base);
        }

    }
