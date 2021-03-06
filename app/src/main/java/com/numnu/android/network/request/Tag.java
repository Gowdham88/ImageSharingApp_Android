package com.numnu.android.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tag {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("displayorder")
@Expose
private Integer displayorder;
@SerializedName("text")
@Expose
private String text;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getDisplayorder() {
return displayorder;
}

public void setDisplayorder(Integer displayorder) {
this.displayorder = displayorder;
}

public String getText() {
return text;
}

public void setText(String text) {
this.text = text;
}

}