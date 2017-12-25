package com.numnu.android.network.request;

import com.google.gson.annotations.SerializedName;

public class BookmarkRequestData{

	@SerializedName("updatedby")
	private int updatedby;

	@SerializedName("createdby")
	private int createdby;

	@SerializedName("clientip")
	private String clientip;

	@SerializedName("clientapp")
	private String clientapp;

	@SerializedName("entityname")
	private String entityname;

	@SerializedName("entityid")
	private String entityid;

	@SerializedName("type")
	private String type;

	public void setUpdatedby(int updatedby){
		this.updatedby = updatedby;
	}

	public int getUpdatedby(){
		return updatedby;
	}

	public void setCreatedby(int createdby){
		this.createdby = createdby;
	}

	public int getCreatedby(){
		return createdby;
	}

	public void setClientip(String clientip){
		this.clientip = clientip;
	}

	public String getClientip(){
		return clientip;
	}

	public void setClientapp(String clientapp){
		this.clientapp = clientapp;
	}

	public String getClientapp(){
		return clientapp;
	}

	public void setEntityname(String entityname){
		this.entityname = entityname;
	}

	public String getEntityname(){
		return entityname;
	}

	public void setEntityid(String entityid){
		this.entityid = entityid;
	}

	public String getEntityid(){
		return entityid;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}
}