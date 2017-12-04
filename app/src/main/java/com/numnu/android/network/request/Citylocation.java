package com.numnu.android.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Citylocation {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("isgoogleplace")
    @Expose
    private Boolean isgoogleplace;
    @SerializedName("lattitude")
    @Expose
    private Double lattitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;

    @SerializedName("googleplaceid")
    @Expose
    private String googleplaceid;
    @SerializedName("googleplacetype")
    @Expose
    private String googleplacetype;


    public String getGoogleplaceid() {
        return googleplaceid;
    }

    public void setGoogleplaceid(String googleplaceid) {
        this.googleplaceid = googleplaceid;
    }

    public String getGoogleplacetype() {
        return googleplacetype;
    }

    public void setGoogleplacetype(String googleplacetype) {
        this.googleplacetype = googleplacetype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsgoogleplace() {
        return isgoogleplace;
    }

    public void setIsgoogleplace(Boolean isgoogleplace) {
        this.isgoogleplace = isgoogleplace;
    }

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

}