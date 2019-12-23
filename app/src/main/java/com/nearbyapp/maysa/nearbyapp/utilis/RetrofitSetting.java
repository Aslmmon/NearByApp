package com.nearbyapp.maysa.nearbyapp.utilis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nearbyapp.maysa.nearbyapp.interfaces.ApiServices;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSetting {
    private ApiServices api = null;

    private static final RetrofitSetting ourInstance = new RetrofitSetting();

    public static RetrofitSetting getInstance() {
        return ourInstance;
    }

    private RetrofitSetting() {
        buildRetrofit();
    }

    private void buildRetrofit(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.api = retrofit.create(ApiServices.class);
    }


    public ApiServices getApi(){
        return this.api;
    }

}
