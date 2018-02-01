package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class Location{

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("address")
	private String address;

	@SerializedName("updatedby")
	private int updatedby;

	@SerializedName("lattitude")
	private String lattitude;

	@SerializedName("createdby")
	private int createdby;

	@SerializedName("isgoogleplace")
	private boolean isgoogleplace;

	@SerializedName("name")
	private String name;

	@SerializedName("googleplacetype")
	private String googleplacetype;

	@SerializedName("id")
	private int id;

	@SerializedName("googleplaceid")
	private String googleplaceid;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("updatedat")
	private String updatedat;

	public void setCreatedat(String createdat){
		this.createdat = createdat;
	}

	public String getCreatedat(){
		return createdat;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setUpdatedby(int updatedby){
		this.updatedby = updatedby;
	}

	public int getUpdatedby(){
		return updatedby;
	}

	public void setLattitude(String lattitude){
		this.lattitude = lattitude;
	}

	public String getLattitude(){
		return lattitude;
	}

	public void setCreatedby(int createdby){
		this.createdby = createdby;
	}

	public int getCreatedby(){
		return createdby;
	}

	public void setIsgoogleplace(boolean isgoogleplace){
		this.isgoogleplace = isgoogleplace;
	}

	public boolean isIsgoogleplace(){
		return isgoogleplace;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setGoogleplacetype(String googleplacetype){
		this.googleplacetype = googleplacetype;
	}

	public String getGoogleplacetype(){
		return googleplacetype;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setGoogleplaceid(String googleplaceid){
		this.googleplaceid = googleplaceid;
	}

	public String getGoogleplaceid(){
		return googleplaceid;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
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
			"Location{" + 
			"createdat = '" + createdat + '\'' + 
			",address = '" + address + '\'' + 
			",updatedby = '" + updatedby + '\'' + 
			",lattitude = '" + lattitude + '\'' + 
			",createdby = '" + createdby + '\'' + 
			",isgoogleplace = '" + isgoogleplace + '\'' + 
			",name = '" + name + '\'' + 
			",googleplacetype = '" + googleplacetype + '\'' + 
			",id = '" + id + '\'' + 
			",googleplaceid = '" + googleplaceid + '\'' + 
			",longitude = '" + longitude + '\'' + 
			",updatedat = '" + updatedat + '\'' + 
			"}";
		}
}