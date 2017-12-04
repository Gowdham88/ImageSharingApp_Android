package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tagsuggestion {

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
private Integer isingredient;
@SerializedName("isverified")
@Expose
private Integer isverified;
@SerializedName("createdat")
@Expose
private String createdat;
@SerializedName("updatedat")
@Expose
private Object updatedat;
@SerializedName("istranslatedvalue")
@Expose
private Boolean istranslatedvalue;
@SerializedName("tagtranslationid")
@Expose
private Integer tagtranslationid;

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

public Integer getIsingredient() {
return isingredient;
}

public void setIsingredient(Integer isingredient) {
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

public Object getUpdatedat() {
return updatedat;
}

public void setUpdatedat(Object updatedat) {
this.updatedat = updatedat;
}

public Boolean getIstranslatedvalue() {
return istranslatedvalue;
}

public void setIstranslatedvalue(Boolean istranslatedvalue) {
this.istranslatedvalue = istranslatedvalue;
}

public Integer getTagtranslationid() {
return tagtranslationid;
}

public void setTagtranslationid(Integer tagtranslationid) {
this.tagtranslationid = tagtranslationid;
}

}