package com.numnu.android.network.request;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompleteSignUpData {

@SerializedName("username")
@Expose
private String username;
@SerializedName("name")
@Expose
private String name;
@SerializedName("description")
@Expose
private String description;
@SerializedName("firebaseuid")
@Expose
private String firebaseuid;
@SerializedName("dateofbirth")
@Expose
private String dateofbirth;
@SerializedName("gender")
@Expose
private Integer gender;
@SerializedName("tags")
@Expose
private List<Tag> tags = null;
@SerializedName("isbusinessuser")
@Expose
private Boolean isbusinessuser;
@SerializedName("email")
@Expose
private String email;
@SerializedName("citylocation")
@Expose
private Citylocation citylocation;
@SerializedName("clientip")
@Expose
private String clientip;
@SerializedName("clientapp")
@Expose
private String clientapp;

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getFirebaseuid() {
return firebaseuid;
}

public void setFirebaseuid(String firebaseuid) {
this.firebaseuid = firebaseuid;
}

public String getDateofbirth() {
return dateofbirth;
}

public void setDateofbirth(String dateofbirth) {
this.dateofbirth = dateofbirth;
}

public Integer getGender() {
return gender;
}

public void setGender(Integer gender) {
this.gender = gender;
}

public List<Tag> getTags() {
return tags;
}

public void setTags(List<Tag> tags) {
this.tags = tags;
}

public Boolean getIsbusinessuser() {
return isbusinessuser;
}

public void setIsbusinessuser(Boolean isbusinessuser) {
this.isbusinessuser = isbusinessuser;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public Citylocation getCitylocation() {
return citylocation;
}

public void setCitylocation(Citylocation citylocation) {
this.citylocation = citylocation;
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