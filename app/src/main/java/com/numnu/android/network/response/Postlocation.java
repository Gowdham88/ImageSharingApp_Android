package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class Postlocation{

	@SerializedName("createdat")
	private Object createdat;

	@SerializedName("address")
	private Object address;

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
	private Object googleplacetype;

	@SerializedName("id")
	private int id;

	@SerializedName("googleplaceid")
	private Object googleplaceid;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("updatedat")
	private Object updatedat;

	public void setCreatedat(Object createdat){
		this.createdat = createdat;
	}

	public Object getCreatedat(){
		return createdat;
	}

	public void setAddress(Object address){
		this.address = address;
	}

	public Object getAddress(){
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

	public void setGoogleplacetype(Object googleplacetype){
		this.googleplacetype = googleplacetype;
	}

	public Object getGoogleplacetype(){
		return googleplacetype;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setGoogleplaceid(Object googleplaceid){
		this.googleplaceid = googleplaceid;
	}

	public Object getGoogleplaceid(){
		return googleplaceid;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	public void setUpdatedat(Object updatedat){
		this.updatedat = updatedat;
	}

	public Object getUpdatedat(){
		return updatedat;
	}
}