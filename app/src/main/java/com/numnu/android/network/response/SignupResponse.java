package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignupResponse {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("username")
@Expose
private String username;
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
@SerializedName("email")
@Expose
private String email;
@SerializedName("citylocation")
@Expose
private Citylocation citylocation;
@SerializedName("updatedat")
@Expose
private String updatedat;
@SerializedName("createdat")
@Expose
private String createdat;
@SerializedName("tags")
@Expose
private List<Tag> tags = null;
@SerializedName("name")
@Expose
private String name;
@SerializedName("isbusinessuser")
@Expose
private Boolean isbusinessuser;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
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

public List<Tag> getTags() {
return tags;
}

public void setTags(List<Tag> tags) {
this.tags = tags;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public Boolean getIsbusinessuser() {
return isbusinessuser;
}

public void setIsbusinessuser(Boolean isbusinessuser) {
this.isbusinessuser = isbusinessuser;
}

}