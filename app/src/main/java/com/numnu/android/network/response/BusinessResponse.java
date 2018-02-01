package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessResponse{

	@SerializedName("dateofbirth")
	private String dateofbirth;

	@SerializedName("isemailverified")
	private boolean isemailverified;

	@SerializedName("businesstypes")
	private List<BusinesstypesItem> businesstypes;

	@SerializedName("updatedby")
	private int updatedby;

	@SerializedName("gender")
	private int gender;

	@SerializedName("businessusername")
	private String businessusername;

	@SerializedName("description")
	private String description;

	@SerializedName("tags")
	private List<TagsItem> tags;

	@SerializedName("citylocation")
	private Citylocation citylocation;

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

	@SerializedName("businessimages")
	private List<BusinessimagesItem> businessimages;

	@SerializedName("businessname")
	private String businessname;

	@SerializedName("businesslinks")
	private List<BusinesslinksItem> businesslinks;

	@SerializedName("businessuseraddresslocation")
	private Businessuseraddresslocation businessuseraddresslocation;

	@SerializedName("id")
	private int id;

	@SerializedName("isbusinessuser")
	private boolean isbusinessuser;

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

	public void setBusinesstypes(List<BusinesstypesItem> businesstypes){
		this.businesstypes = businesstypes;
	}

	public List<BusinesstypesItem> getBusinesstypes(){
		return businesstypes;
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

	public void setCitylocation(Citylocation citylocation){
		this.citylocation = citylocation;
	}

	public Citylocation getCitylocation(){
		return citylocation;
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

	public void setBusinessimages(List<BusinessimagesItem> businessimages){
		this.businessimages = businessimages;
	}

	public List<BusinessimagesItem> getBusinessimages(){
		return businessimages;
	}

	public void setBusinessname(String businessname){
		this.businessname = businessname;
	}

	public String getBusinessname(){
		return businessname;
	}

	public void setBusinesslinks(List<BusinesslinksItem> businesslinks){
		this.businesslinks = businesslinks;
	}

	public List<BusinesslinksItem> getBusinesslinks(){
		return businesslinks;
	}

	public void setBusinessuseraddresslocation(Businessuseraddresslocation businessuseraddresslocation){
		this.businessuseraddresslocation = businessuseraddresslocation;
	}

	public Businessuseraddresslocation getBusinessuseraddresslocation(){
		return businessuseraddresslocation;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setIsbusinessuser(boolean isbusinessuser){
		this.isbusinessuser = isbusinessuser;
	}

	public boolean isIsbusinessuser(){
		return isbusinessuser;
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