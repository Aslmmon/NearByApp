package com.nearbyapp.maysa.nearbyapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.google.gson.Gson;
import com.nearbyapp.maysa.nearbyapp.datamodels.Item;
import com.nearbyapp.maysa.nearbyapp.datamodels.PhotosVenuesDataModel;
import com.nearbyapp.maysa.nearbyapp.datamodels.Venue;
import com.nearbyapp.maysa.nearbyapp.utilis.Constants;
import com.nearbyapp.maysa.nearbyapp.utilis.RetrofitSetting;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainPageViewModel extends ViewModel  {


    //get all  nearby places
    private MutableLiveData<Venue> venuesResDataModelMutableLiveData ;
    public LiveData<Venue> getAllNearByPlaces(String clientID, String clientSecret,
                                                           String ll, String currentDate, double llAcc){
        venuesResDataModelMutableLiveData = new MutableLiveData<>();
        getAllNearByPLacesMethod(clientID,clientSecret,ll,currentDate,llAcc);
        return venuesResDataModelMutableLiveData;
    }
    private void getAllNearByPLacesMethod(final String clientID, final String clientSecret,
                                          String ll, String currentDate, double llAcc){

        Observer<Venue> observer = new Observer<Venue>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("onSubscribe","Disposable");
            }

            @Override
            public void onNext(Venue venue) {

                if (venue!=null) {
                    venue.setIsEmpty("has_data");
                    getPhotosofAllNearByPlacesMethod(venue);
                }
                else {
                    venuesResDataModelMutableLiveData.postValue(new Venue("no_data"));
                }
            }

            @Override
            public void onError(Throwable e) {
                venuesResDataModelMutableLiveData.postValue(new Venue("error"));

            }

            @Override
            public void onComplete() {
            }
        };
        RetrofitSetting.getInstance().getApi()
                .getAllNearbyPlaces(clientID,clientSecret,ll, currentDate,llAcc)
                .flatMapIterable(venuesList -> venuesList.getResponse().getVenues())
                .flatMap(this::getObservableFromVenueList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
    private Observable<Venue> getObservableFromVenueList(Venue venue) {
        return Observable.just(venue);
    }
    private void getPhotosofAllNearByPlacesMethod( Venue venue ){
           Observable<PhotosVenuesDataModel> allNearbyPlacesPhotos =  RetrofitSetting.getInstance()
                 .getApi().getPhotosOfAllNearByPlaces( Constants.BASE_URL + "venues/" +
                         venue.getId() + "/photos?" +
                         "client_id=" + Constants.CLIENT_ID
                         + "&client_secret=" +  Constants.CLIENT_SECRET + "&v=20192312");;
           allNearbyPlacesPhotos.subscribeOn(Schedulers.io()).
                 observeOn(AndroidSchedulers.mainThread()).
                 subscribe(new Observer<PhotosVenuesDataModel>() {
                     @Override
                     public void onSubscribe(Disposable d) {
                         Log.d("onSubscribe", "Disposable");
                     }
                     @Override
                     public void onNext(PhotosVenuesDataModel photosVenuesDataModel) {
                         Log.d("onNext", "onNext");
                         Log.d("EVERY_PLACE", new Gson().toJson(venue));
                         if (photosVenuesDataModel.getResponse().getPhotos().getItems().size() != 0) {
                             for (Item item : photosVenuesDataModel.getResponse().getPhotos().getItems()) {
                                 venue.setPhoto(item.getPrefix() + "original" + item.getSuffix());
                             }
                         }
                         else {
                             Log.d("HEREEEE", new Gson().toJson(venue));
                             venue.setPhoto("no_photo");
                         }
                         venuesResDataModelMutableLiveData.postValue(venue);
                         Log.d("AFTER_EDIT", new Gson().toJson(venue));
                     }

                     @Override
                     public void onError(Throwable e) {
                         Log.d("ERRORRRRRRRRR", new Gson().toJson(venue));

                         venue.setPhoto("no_photo");
                         venuesResDataModelMutableLiveData.postValue(venue);

                     }

                     @Override
                     public void onComplete() {
                         Log.d("COMPLETE", "COMPLETE");

                     }
                 });


    }



}
