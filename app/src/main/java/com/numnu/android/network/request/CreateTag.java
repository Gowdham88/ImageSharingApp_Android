package com.numnu.android.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateTag {

@SerializedName("text")
@Expose
private String text;
@SerializedName("imageurl")
@Expose
private String imageurl;
@SerializedName("isingredient")
@Expose
private Boolean isingredient;
@SerializedName("createdby")
@Expose
private Integer createdby;
@SerializedName("updatedby")
@Expose
private Integer updatedby;
@SerializedName("clientip")
@Expose
private String clientip;
@SerializedName("clientapp")
@Expose
private String clientapp;

public String getText() {
return text;
}

public void setText(String text) {
this.text = text;
}

public String getImageurl() {
return imageurl;
}

public void setImageurl(String imageurl) {
this.imageurl = imageurl;
}

public Boolean getIsingredient() {
return isingredient;
}

public void setIsingredient(Boolean isingredient) {
this.isingredient = isingredient;
}

public Integer getCreatedby() {
return createdby;
}

public void setCreatedby(Integer createdby) {
this.createdby = createdby;
}

public Integer getUpdatedby() {
return updatedby;
}

public void setUpdatedby(Integer updatedby) {
this.updatedby = updatedby;
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


