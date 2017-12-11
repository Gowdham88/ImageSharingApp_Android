package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class EventimagesItem{

	@SerializedName("eventid")
	private int eventid;

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

	@SerializedName("updatedat")
	private String updatedat;

	public void setEventid(int eventid){
		this.eventid = eventid;
	}

	public int getEventid(){
		return eventid;
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

	public void setUpdatedat(String updatedat){
		this.updatedat = updatedat;
	}

	public String getUpdatedat(){
		return updatedat;
	}

	@Override
 	public String toString(){
		return 
			"EventimagesItem{" + 
			"eventid = '" + eventid + '\'' + 
			",createdat = '" + createdat + '\'' + 
			",updatedby = '" + updatedby + '\'' + 
			",createdby = '" + createdby + '\'' + 
			",imageurl = '" + imageurl + '\'' + 
			",id = '" + id + '\'' + 
			",updatedat = '" + updatedat + '\'' + 
			"}";
		}
}