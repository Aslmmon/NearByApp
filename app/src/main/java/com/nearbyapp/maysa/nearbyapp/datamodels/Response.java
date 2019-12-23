
package com.nearbyapp.maysa.nearbyapp.datamodels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("confident")
    private Boolean mConfident;
    @SerializedName("venues")
    private List<Venue> mVenues;
    @SerializedName("photos")
    private Photos mPhotos;



    public Photos getPhotos() {
        return mPhotos;
    }

    public void setPhotos(Photos photos) {
        mPhotos = photos;
    }
    public Boolean getConfident() {
        return mConfident;
    }

    public void setConfident(Boolean confident) {
        mConfident = confident;
    }

    public List<Venue> getVenues() {
        return mVenues;
    }

    public void setVenues(List<Venue> venues) {
        mVenues = venues;
    }

}
