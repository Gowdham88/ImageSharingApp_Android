package com.numnu.android.network.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class EventBusinessesResponse{

	@SerializedName("dateofbirth")
	private String dateofbirth;

	@SerializedName("isemailverified")
	private boolean isemailverified;

	@SerializedName("businessdescription")
	private String businessdescription;

	@SerializedName("gender")
	private int gender;

	@SerializedName("businessusername")
	private String businessusername;

	@SerializedName("userimages")
	private List<UserimagesItem> userimages;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("businessuserphone")
	private String businessuserphone;

	@SerializedName("username")
	private String username;

	@SerializedName("tags")
	private List<TagsItem> tags;

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

	public void setBusinessdescription(String businessdescription){
		this.businessdescription = businessdescription;
	}

	public String getBusinessdescription(){
		return businessdescription;
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

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
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

	public void setTags(List<TagsItem> tags){
		this.tags = tags;
	}

	public List<TagsItem> getTags(){
		return tags;
	}
}