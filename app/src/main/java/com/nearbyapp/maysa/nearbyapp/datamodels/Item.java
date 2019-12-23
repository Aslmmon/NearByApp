
package com.nearbyapp.maysa.nearbyapp.datamodels;

import com.google.gson.annotations.SerializedName;


public class Item {

    @SerializedName("prefix")
    private String mPrefix;
    @SerializedName("source")
    private Source mSource;
    @SerializedName("suffix")
    private String mSuffix;


    public String getPrefix() {
        return mPrefix;
    }

    public void setPrefix(String prefix) {
        mPrefix = prefix;
    }

    public Source getSource() {
        return mSource;
    }

    public void setSource(Source source) {
        mSource = source;
    }

    public String getSuffix() {
        return mSuffix;
    }

    public void setSuffix(String suffix) {
        mSuffix = suffix;
    }


}
