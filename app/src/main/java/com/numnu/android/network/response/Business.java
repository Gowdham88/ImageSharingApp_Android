package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class Business{

	@SerializedName("businessname")
	private String businessname;

	@SerializedName("id")
	private int id;

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
}