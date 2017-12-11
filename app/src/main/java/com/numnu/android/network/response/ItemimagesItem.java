package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class ItemimagesItem{

	@SerializedName("itemid")
	private int itemid;

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("updatedby")
	private int updatedby;

	@SerializedName("createdby")
	private int createdby;

	@SerializedName("imageurl")
	private String imageurl;

	@SerializedName("id")
	private int id;

	@SerializedName("updatedat")
	private String updatedat;

	public void setItemid(int itemid){
		this.itemid = itemid;
	}

	public int getItemid(){
		return itemid;
	}

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

	public void setUpdatedat(String updatedat){
		this.updatedat = updatedat;
	}

	public String getUpdatedat(){
		return updatedat;
	}
}