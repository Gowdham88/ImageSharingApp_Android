package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginCityLocation {

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
private String lattitude;
@SerializedName("longitude")
@Expose
private String longitude;
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
private String createdby;
@SerializedName("updatedby")
@Expose
private String updatedby;

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

public String getLattitude() {
return lattitude;
}

public void setLattitude(String lattitude) {
this.lattitude = lattitude;
}

public String getLongitude() {
return longitude;
}

public void setLongitude(String longitude) {
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

public String getCreatedby() {
return createdby;
}

public void setCreatedby(String createdby) {
this.createdby = createdby;
}

public String getUpdatedby() {
return updatedby;
}

public void setUpdatedby(String updatedby) {
this.updatedby = updatedby;
}

}