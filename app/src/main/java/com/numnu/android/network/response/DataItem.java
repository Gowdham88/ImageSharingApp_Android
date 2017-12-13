package com.numnu.android.network.response;

import java.util.List;

public class DataItem{
	private String dateofbirth;
	private boolean isemailverified;
	private int gender;
	private String businessusername;
	private List<UserimagesItem> userimages;
	private String description;
	private List<TagsItem> tags;
	private String businessdescription;
	private String name;
	private String businessname;
	private int id;
	private String email;
	private String businessuserphone;
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