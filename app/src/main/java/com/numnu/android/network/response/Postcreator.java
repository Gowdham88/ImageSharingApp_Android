package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Postcreator{

	@SerializedName("userimages")
	private List<UserimagesItem> userimages;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("username")
	private String username;

	public void setUserimages(List<UserimagesItem> userimages){
		this.userimages = userimages;
	}

	public List<UserimagesItem> getUserimages(){
		return userimages;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}