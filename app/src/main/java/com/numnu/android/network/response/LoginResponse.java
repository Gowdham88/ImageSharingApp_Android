package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class LoginResponse{

	@SerializedName("dateofbirth")
	private String dateofbirth;

	@SerializedName("isemailverified")
	private Boolean isemailverified;

	@SerializedName("updatedby")
	private String updatedby;

	@SerializedName("gender")
	private int gender;

	@SerializedName("businessusername")
	private String businessusername;

	@SerializedName("userimages")
	private List<UserimagesItem> userimages;

	@SerializedName("isbusiness")
	private Boolean isbusiness;

	@SerializedName("description")
	private String description;

	@SerializedName("tags")
	private List<TagsItem> tags;

	@SerializedName("citylocation")
	private LoginCityLocation citylocation;

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("firebaseuid")
	private String firebaseuid;

	@SerializedName("businessdescription")
	private String businessdescription;

	@SerializedName("createdby")
	private String createdby;

	@SerializedName("id")
	private int id;

	@SerializedName("citylocationid")
	private int citylocationid;

	@SerializedName("businessuseraddresslocationid")
	private String businessuseraddresslocationid;

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

	public void setIsemailverified(Boolean isemailverified){
		this.isemailverified = isemailverified;
	}

	public Boolean getIsemailverified(){
		return isemailverified;
	}

	public void setUpdatedby(String updatedby){
		this.updatedby = updatedby;
	}

	public String getUpdatedby(){
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

	public void setUserimages(List<UserimagesItem> userimages){
		this.userimages = userimages;
	}

	public List<UserimagesItem> getUserimages(){
		return userimages;
	}

	public void setIsbusiness(Boolean isbusiness){
		this.isbusiness = isbusiness;
	}

	public Boolean getIsbusiness(){
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

	public void setCitylocation(LoginCityLocation citylocation){
		this.citylocation = citylocation;
	}

	public LoginCityLocation getCitylocation(){
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

	public void setCreatedby(String createdby){
		this.createdby = createdby;
	}

	public String getCreatedby(){
		return createdby;
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

	public void setBusinessuseraddresslocationid(String businessuseraddresslocationid){
		this.businessuseraddresslocationid = businessuseraddresslocationid;
	}

	public String getBusinessuseraddresslocationid(){
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

	@Override
 	public String toString(){
		return 
			"LoginResponse{" + 
			"dateofbirth = '" + dateofbirth + '\'' + 
			",isemailverified = '" + isemailverified + '\'' + 
			",updatedby = '" + updatedby + '\'' + 
			",gender = '" + gender + '\'' + 
			",businessusername = '" + businessusername + '\'' + 
			",userimages = '" + userimages + '\'' + 
			",isbusiness = '" + isbusiness + '\'' + 
			",description = '" + description + '\'' + 
			",tags = '" + tags + '\'' + 
			",citylocation = '" + citylocation + '\'' + 
			",createdat = '" + createdat + '\'' + 
			",firebaseuid = '" + firebaseuid + '\'' + 
			",businessdescription = '" + businessdescription + '\'' + 
			",createdby = '" + createdby + '\'' + 
			",id = '" + id + '\'' + 
			",citylocationid = '" + citylocationid + '\'' + 
			",businessuseraddresslocationid = '" + businessuseraddresslocationid + '\'' + 
			",email = '" + email + '\'' + 
			",businessuserphone = '" + businessuserphone + '\'' + 
			",username = '" + username + '\'' + 
			",updatedat = '" + updatedat + '\'' + 
			"}";
		}
}