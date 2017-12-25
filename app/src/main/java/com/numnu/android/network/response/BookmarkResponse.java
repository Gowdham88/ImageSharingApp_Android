package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class BookmarkResponse{

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("updatedby")
	private int updatedby;

	@SerializedName("createdby")
	private int createdby;

	@SerializedName("entityname")
	private String entityname;

	@SerializedName("entityid")
	private String entityid;

	@SerializedName("id")
	private int id;

	@SerializedName("type")
	private String type;

	@SerializedName("userid")
	private String userid;

	@SerializedName("updatedat")
	private String updatedat;

	public void setCreatedat(String createdat){
		this.createdat = createdat;
	}

	public String getCreatedat(){
		return createdat;
	}

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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setUserid(String userid){
		this.userid = userid;
	}

	public String getUserid(){
		return userid;
	}

	public void setUpdatedat(String updatedat){
		this.updatedat = updatedat;
	}

	public String getUpdatedat(){
		return updatedat;
	}
}