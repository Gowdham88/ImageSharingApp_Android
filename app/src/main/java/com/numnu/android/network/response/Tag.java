package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tag {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("text")
@Expose
private String text;
@SerializedName("imageurl")
@Expose
private String imageurl;
@SerializedName("isingredient")
@Expose
private Boolean isingredient;
@SerializedName("isverified")
@Expose
private String isverified;
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
@SerializedName("displayorder")
@Expose
private Integer displayorder;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

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

public String getIsverified() {
return isverified;
}

public void setIsverified(String isverified) {
this.isverified = isverified;
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

public Integer getDisplayorder() {
return displayorder;
}

public void setDisplayorder(Integer displayorder) {
this.displayorder = displayorder;
}

}