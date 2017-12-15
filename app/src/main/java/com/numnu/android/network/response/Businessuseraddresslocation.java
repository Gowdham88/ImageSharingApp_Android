package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class Businessuseraddresslocation{

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("address")
	private String address;

	@SerializedName("lattitude")
	private Object lattitude;

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
	private Object longitude;

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
}