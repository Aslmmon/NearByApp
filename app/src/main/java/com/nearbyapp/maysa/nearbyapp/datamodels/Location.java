
package com.nearbyapp.maysa.nearbyapp.datamodels;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Location {


    @SerializedName("country")
    private String mCountry;
    @SerializedName("distance")
    private Long mDistance;
    @SerializedName("formattedAddress")
    private ArrayList<String>mFormattedAddress;



    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public Long getDistance() {
        return mDistance;
    }

    public void setDistance(Long distance) {
        mDistance = distance;
    }

    public ArrayList<String> getFormattedAddress() {
        return mFormattedAddress;
    }

    public void setFormattedAddress(ArrayList<String> formattedAddress) {
        mFormattedAddress = formattedAddress;
    }


}
