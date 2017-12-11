package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class UserimagesItem{

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("updatedby")
	private String updatedby;

	@SerializedName("createdby")
	private String createdby;

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

	public void setUpdatedby(String updatedby){
		this.updatedby = updatedby;
	}

	public String getUpdatedby(){
		return updatedby;
	}

	public void setCreatedby(String createdby){
		this.createdby = createdby;
	}

	public String getCreatedby(){
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

	@Override
 	public String toString(){
		return 
			"UserimagesItem{" + 
			"createdat = '" + createdat + '\'' + 
			",updatedby = '" + updatedby + '\'' + 
			",createdby = '" + createdby + '\'' + 
			",imageurl = '" + imageurl + '\'' + 
			",id = '" + id + '\'' + 
			",userid = '" + userid + '\'' + 
			",updatedat = '" + updatedat + '\'' + 
			"}";
		}
}