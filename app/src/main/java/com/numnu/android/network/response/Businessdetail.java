package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Businessdetail{

	@SerializedName("dateofbirth")
	private String dateofbirth;

	@SerializedName("isemailverified")
	private boolean isemailverified;

	@SerializedName("images")
	private List<ImagesItem> images;

	@SerializedName("updatedby")
	private int updatedby;

	@SerializedName("gender")
	private int gender;

	@SerializedName("businessusername")
	private String businessusername;

	@SerializedName("isbusiness")
	private boolean isbusiness;

	@SerializedName("description")
	private String description;

	@SerializedName("tags")
	private List<TagsItem> tags;

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("firebaseuid")
	private String firebaseuid;

	@SerializedName("businessdescription")
	private String businessdescription;

	@SerializedName("createdby")
	private int createdby;

	@SerializedName("name")
	private String name;

	@SerializedName("businessname")
	private String businessname;

	@SerializedName("id")
	private int id;

	@SerializedName("citylocationid")
	private int citylocationid;

	@SerializedName("businessuseraddresslocationid")
	private int businessuseraddresslocationid;

	@SerializedName("email")
	private String email;

	@SerializedName("businessuserphone")
	private String businessuserphone;

	@SerializedName("username")
	private String username;

	@SerializedName("updatedat")
	private String updatedat;

	public void setDateofbirth(String dateofbirth){
		this.dateofbirth = dateofbirth;
	}

	public String getDateofbirth(){
		return dateofbirth;
	}

	public void setIsemailverified(boolean isemailverified){
		this.isemailverified = isemailverified;
	}

	public boolean isIsemailverified(){
		return isemailverified;
	}

	public void setImages(List<ImagesItem> images){
		this.images = images;
	}

	public List<ImagesItem> getImages(){
		return images;
	}

	public void setUpdatedby(int updatedby){
		this.updatedby = updatedby;
	}

	public int getUpdatedby(){
		return updatedby;
	}

	public void setGender(int gender){
		this.gender = gender;
	}

	public int getGender(){
		return gender;
	}

	public void setBusinessusername(String businessusername){
		this.businessusername = businessusername;
	}

	public String getBusinessusername(){
		return businessusername;
	}

	public void setIsbusiness(boolean isbusiness){
		this.isbusiness = isbusiness;
	}

	public boolean isIsbusiness(){
		return isbusiness;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setTags(List<TagsItem> tags){
		this.tags = tags;
	}

	public List<TagsItem> getTags(){
		return tags;
	}

	public void setCreatedat(String createdat){
		this.createdat = createdat;
	}

	public String getCreatedat(){
		return createdat;
	}

	public void setFirebaseuid(String firebaseuid){
		this.firebaseuid = firebaseuid;
	}

	public String getFirebaseuid(){
		return firebaseuid;
	}

	public void setBusinessdescription(String businessdescription){
		this.businessdescription = businessdescription;
	}

	public String getBusinessdescription(){
		return businessdescription;
	}

	public void setCreatedby(int createdby){
		this.createdby = createdby;
	}

	public int getCreatedby(){
		return createdby;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setBusinessname(String businessname){
		this.businessname = businessname;
	}

	public String getBusinessname(){
		return businessname;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCitylocationid(int citylocationid){
		this.citylocationid = citylocationid;
	}

	public int getCitylocationid(){
		return citylocationid;
	}

	public void setBusinessuseraddresslocationid(int businessuseraddresslocationid){
		this.businessuseraddresslocationid = businessuseraddresslocationid;
	}

	public int getBusinessuseraddresslocationid(){
		return businessuseraddresslocationid;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setBusinessuserphone(String businessuserphone){
		this.businessuserphone = businessuserphone;
	}

	public String getBusinessuserphone(){
		return businessuserphone;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setUpdatedat(String updatedat){
		this.updatedat = updatedat;
	}

	public String getUpdatedat(){
		return updatedat;
	}
}