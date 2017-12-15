package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class BusinesstypesItem{

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("imageurl")
	private Object imageurl;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	public void setCreatedat(String createdat){
		this.createdat = createdat;
	}

	public String getCreatedat(){
		return createdat;
	}

	public void setImageurl(Object imageurl){
		this.imageurl = imageurl;
	}

	public Object getImageurl(){
		return imageurl;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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
}