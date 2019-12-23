package com.nearbyapp.maysa.nearbyapp.interfaces;

import com.nearbyapp.maysa.nearbyapp.datamodels.PhotosVenuesDataModel;
import com.nearbyapp.maysa.nearbyapp.datamodels.VenuesResDataModel;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiServices {

    @POST("venues/search?")
    Observable<VenuesResDataModel> getAllNearbyPlaces(@Query("client_id") String clientID,
                                                      @Query("client_secret") String clientSecret,
                                                      @Query("ll") String ll,
                                                      @Query("v") String currentDate,
                                                      @Query("llAcc") double llAcc);


    @GET()
    Observable<PhotosVenuesDataModel> getPhotosOfAllNearByPlaces(@Url String url);


}
