package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class ImagesItem{

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("updatedby")
	private Object updatedby;

	@SerializedName("createdby")
	private Object createdby;

	@SerializedName("imageurl")
	private String imageurl;

	@SerializedName("id")
	private int id;

	@SerializedName("userid")
	private int userid;

	@SerializedName("updatedat")
	private String updatedat;

	public void setCreatedat(String createdat){
		this.createdat = createdat;
	}

	public String getCreatedat(){
		return createdat;
	}

	public void setUpdatedby(Object updatedby){
		this.updatedby = updatedby;
	}

	public Object getUpdatedby(){
		return updatedby;
	}

	public void setCreatedby(Object createdby){
		this.createdby = createdby;
	}

	public Object getCreatedby(){
		return createdby;
	}

	public void setImageurl(String imageurl){
		this.imageurl = imageurl;
	}

	public String getImageurl(){
		return imageurl;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setUserid(int userid){
		this.userid = userid;
	}

	public int getUserid(){
		return userid;
	}

	public void setUpdatedat(String updatedat){
		this.updatedat = updatedat;
	}

	public String getUpdatedat(){
		return updatedat;
	}
}