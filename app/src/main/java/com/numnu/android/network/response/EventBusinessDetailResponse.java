package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventBusinessDetailResponse{

	@SerializedName("dateofbirth")
	private String dateofbirth;

	@SerializedName("isemailverified")
	private boolean isemailverified;

	@SerializedName("gender")
	private int gender;

	@SerializedName("businessusername")
	private String businessusername;

	@SerializedName("description")
	private String description;

	@SerializedName("tags")
	private List<TagsItem> tags;

	@SerializedName("businessdescription")
	private String businessdescription;

	@SerializedName("name")
	private String name;

	@SerializedName("businessimages")
	private List<BusinessimagesItem> businessimages;

	@SerializedName("businessname")
	private String businessname;

	@SerializedName("id")
	private int id;

	@SerializedName("isbusinessuser")
	private boolean isbusinessuser;

	@SerializedName("event")
	private Event event;

	@SerializedName("email")
	private String email;

	@SerializedName("businessuserphone")
	private String businessuserphone;

	@SerializedName("username")
	private String username;

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

	public void setBusinessdescription(String businessdescription){
		this.businessdescription = businessdescription;
	}

	public String getBusinessdescription(){
		return businessdescription;
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

	public void setEvent(Event event){
		this.event = event;
	}

	public Event getEvent(){
		return event;
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
}