package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class TagsItem{

	@SerializedName("isingredient")
	private Boolean isingredient;

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

	@SerializedName("text")
	private String text;

	@SerializedName("isverified")
	private Boolean isverified;

	@SerializedName("updatedat")
	private String updatedat;

	public void setIsingredient(Boolean isingredient){
		this.isingredient = isingredient;
	}

	public Boolean getIsingredient(){
		return isingredient;
	}

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

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setIsverified(Boolean isverified){
		this.isverified = isverified;
	}

	public Boolean getIsverified(){
		return isverified;
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
			"TagsItem{" + 
			"isingredient = '" + isingredient + '\'' + 
			",createdat = '" + createdat + '\'' + 
			",updatedby = '" + updatedby + '\'' + 
			",createdby = '" + createdby + '\'' + 
			",imageurl = '" + imageurl + '\'' + 
			",id = '" + id + '\'' + 
			",text = '" + text + '\'' + 
			",isverified = '" + isverified + '\'' + 
			",updatedat = '" + updatedat + '\'' + 
			"}";
		}
}