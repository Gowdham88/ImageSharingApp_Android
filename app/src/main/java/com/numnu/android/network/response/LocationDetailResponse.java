package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationDetailResponse{

	@SerializedName("address")
	private String address;

	@SerializedName("updatedby")
	private Object updatedby;

	@SerializedName("lattitude")
	private Object lattitude;

	@SerializedName("isgoogleplace")
	private boolean isgoogleplace;

	@SerializedName("type")
	private int type;

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("business")
	private Locationbusiness locationbusiness;

	@SerializedName("locationimages")
	private List<LocationimagesItem> locationimages;

	@SerializedName("createdby")
	private Object createdby;

	@SerializedName("name")
	private String name;

	@SerializedName("googleplacetype")
	private String googleplacetype;

	@SerializedName("id")
	private int id;

	@SerializedName("googleplaceid")
	private String googleplaceid;

	@SerializedName("longitude")
	private Object longitude;

	@SerializedName("updatedat")
	private String updatedat;

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setUpdatedby(Object updatedby){
		this.updatedby = updatedby;
	}

	public Object getUpdatedby(){
		return updatedby;
	}

	public void setLattitude(Object lattitude){
		this.lattitude = lattitude;
	}

	public Object getLattitude(){
		return lattitude;
	}

	public void setIsgoogleplace(boolean isgoogleplace){
		this.isgoogleplace = isgoogleplace;
	}

	public boolean isIsgoogleplace(){
		return isgoogleplace;
	}

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
	}

	public void setCreatedat(String createdat){
		this.createdat = createdat;
	}

	public String getCreatedat(){
		return createdat;
	}

	public void setLocationbusiness(Locationbusiness locationbusiness){
		this.locationbusiness = locationbusiness;
	}

	public Locationbusiness getLocationbusiness(){
		return locationbusiness;
	}

	public void setLocationimages(List<LocationimagesItem> locationimages){
		this.locationimages = locationimages;
	}

	public List<LocationimagesItem> getLocationimages(){
		return locationimages;
	}

	public void setCreatedby(Object createdby){
		this.createdby = createdby;
	}

	public Object getCreatedby(){
		return createdby;
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

	public void setLongitude(Object longitude){
		this.longitude = longitude;
	}

	public Object getLongitude(){
		return longitude;
	}

	public void setUpdatedat(String updatedat){
		this.updatedat = updatedat;
	}

	public String getUpdatedat(){
		return updatedat;
	}
}