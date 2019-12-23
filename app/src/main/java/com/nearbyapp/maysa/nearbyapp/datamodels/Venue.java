
package com.nearbyapp.maysa.nearbyapp.datamodels;

import java.lang.ref.PhantomReference;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Venue {

    @SerializedName("id")
    private String mId;
    @SerializedName("location")
    private Location mLocation;
    @SerializedName("name")
    private String mName;
    @SerializedName("referralId")
    private String mReferralId;

    public Venue() {
    }

    public Venue(String isEmpty) {
        this.isEmpty = isEmpty;
    }

    private String isEmpty ;

    public String getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(String isEmpty) {
        this.isEmpty = isEmpty;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    private String photo ;


    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getReferralId() {
        return mReferralId;
    }

    public void setReferralId(String referralId) {
        mReferralId = referralId;
    }

}
