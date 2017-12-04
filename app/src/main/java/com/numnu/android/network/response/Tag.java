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
private Object imageurl;
@SerializedName("isingredient")
@Expose
private Object isingredient;
@SerializedName("isverified")
@Expose
private Integer isverified;
@SerializedName("createdat")
@Expose
private String createdat;
@SerializedName("updatedat")
@Expose
private String updatedat;
@SerializedName("createdby")
@Expose
private Integer createdby;
@SerializedName("updatedby")
@Expose
private Integer updatedby;
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

public Object getImageurl() {
return imageurl;
}

public void setImageurl(Object imageurl) {
this.imageurl = imageurl;
}

public Object getIsingredient() {
return isingredient;
}

public void setIsingredient(Object isingredient) {
this.isingredient = isingredient;
}

public Integer getIsverified() {
return isverified;
}

public void setIsverified(Integer isverified) {
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

public Integer getDisplayorder() {
return displayorder;
}

public void setDisplayorder(Integer displayorder) {
this.displayorder = displayorder;
}

}