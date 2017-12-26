package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by czsm4 on 26/12/17.
 */

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("lattitude")
    @Expose
    private Object lattitude;
    @SerializedName("longitude")
    @Expose
    private Object longitude;
    @SerializedName("isgoogleplace")
    @Expose
    private Boolean isgoogleplace;
    @SerializedName("googleplaceid")
    @Expose
    private String googleplaceid;
    @SerializedName("googleplacetype")
    @Expose
    private String googleplacetype;
    @SerializedName("createdat")
    @Expose
    private String createdat;
    @SerializedName("updatedat")
    @Expose
    private String updatedat;
    @SerializedName("createdby")
    @Expose
    private Object createdby;
    @SerializedName("updatedby")
    @Expose
    private Object updatedby;
    @SerializedName("locationimages")
    @Expose
    private List<Locationimage> locationimages = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Object getLattitude() {
        return lattitude;
    }

    public void setLattitude(Object lattitude) {
        this.lattitude = lattitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public Boolean getIsgoogleplace() {
        return isgoogleplace;
    }

    public void setIsgoogleplace(Boolean isgoogleplace) {
        this.isgoogleplace = isgoogleplace;
    }

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

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public String getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(String updatedat) {
        this.updatedat = updatedat;
    }

    public Object getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Object createdby) {
        this.createdby = createdby;
    }

    public Object getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(Object updatedby) {
        this.updatedby = updatedby;
    }

    public List<Locationimage> getLocationimages() {
        return locationimages;
    }

    public void setLocationimages(List<Locationimage> locationimages) {
        this.locationimages = locationimages;
    }

}
