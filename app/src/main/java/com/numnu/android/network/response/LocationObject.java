package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by czsm4 on 05/01/18.
 */

public class LocationObject {

    @SerializedName("lattitude")
    @Expose
    private Double lattitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("nearMeRadiusInMiles")
    @Expose
    private Integer nearMeRadiusInMiles;

    public Double getLattitude() {
        return lattitude;
    }

    public void setLattitude(Double lattitude) {
        this.lattitude = lattitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getNearMeRadiusInMiles() {
        return nearMeRadiusInMiles;
    }

    public void setNearMeRadiusInMiles(Integer nearMeRadiusInMiles) {
        this.nearMeRadiusInMiles = nearMeRadiusInMiles;
    }

}