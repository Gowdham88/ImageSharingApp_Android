package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class Event{

	@SerializedName("name")
	private String name;

	@SerializedName("startsat")
	private String startsat;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("endsat")
	private String endsat;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setStartsat(String startsat){
		this.startsat = startsat;
	}

	public String getStartsat(){
		return startsat;
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

	public void setEndsat(String endsat){
		this.endsat = endsat;
	}

	public String getEndsat(){
		return endsat;
	}
}