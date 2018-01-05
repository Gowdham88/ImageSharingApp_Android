package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by czsm4 on 05/01/18.
 */

public class LocationHomePost {
    @SerializedName("locationObject")
    @Expose
    private LocationObject locationObject;
    @SerializedName("searchText")
    @Expose
    private String searchText;
    @SerializedName("clientip")
    @Expose
    private String clientip;
    @SerializedName("clientapp")
    @Expose
    private String clientapp;

    public LocationObject getLocationObject() {
        return locationObject;
    }

    public void setLocationObject(LocationObject locationObject) {
        this.locationObject = locationObject;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getClientip() {
        return clientip;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    public String getClientapp() {
        return clientapp;
    }

    public void setClientapp(String clientapp) {
        this.clientapp = clientapp;
    }

}