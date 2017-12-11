package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Citylocation {

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
private Double lattitude;
@SerializedName("longitude")
@Expose
private Double longitude;
@SerializedName("isgoogleplace")
@Expose
private Boolean isgoogleplace;
@SerializedName("updatedat")
@Expose
private String updatedat;
@SerializedName("createdat")
@Expose
private String createdat;

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

public Boolean getIsgoogleplace() {
return isgoogleplace;
}

public void setIsgoogleplace(Boolean isgoogleplace) {
this.isgoogleplace = isgoogleplace;
}

public String getUpdatedat() {
return updatedat;
}

public void setUpdatedat(String updatedat) {
this.updatedat = updatedat;
}

public String getCreatedat() {
return createdat;
}

public void setCreatedat(String createdat) {
this.createdat = createdat;
}

}