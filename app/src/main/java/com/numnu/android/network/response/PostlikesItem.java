package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class PostlikesItem{

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("id")
	private int id;

	public void setCreatedat(String createdat){
		this.createdat = createdat;
	}

	public String getCreatedat(){
		return createdat;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}