
package com.nearbyapp.maysa.nearbyapp.datamodels;

import com.google.gson.annotations.SerializedName;

public class VenuesResDataModel {

    @SerializedName("meta")
    private Meta mMeta;
    @SerializedName("response")
    private Response mResponse;

    public VenuesResDataModel(String isEmpty) {
        this.isEmpty = isEmpty;
    }

    public VenuesResDataModel() {
    }

    public String getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(String isEmpty) {
        this.isEmpty = isEmpty;
    }

    private String isEmpty ;

    public Meta getMeta() {
        return mMeta;
    }

    public void setMeta(Meta meta) {
        mMeta = meta;
    }

    public Response getResponse() {
        return mResponse;
    }

    public void setResponse(Response response) {
        mResponse = response;
    }

}
