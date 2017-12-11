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
private String createdby;
@SerializedName("updatedby")
@Expose
private String updatedby;
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


