package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class EventlinksItem{

	@SerializedName("eventid")
	private int eventid;

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("updatedby")
	private int updatedby;

	@SerializedName("createdby")
	private int createdby;

	@SerializedName("weblink")
	private String weblink;

	@SerializedName("id")
	private int id;

	@SerializedName("linktext")
	private String linktext;

	@SerializedName("displayorder")
	private int displayorder;

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

	public void setWeblink(String weblink){
		this.weblink = weblink;
	}

	public String getWeblink(){
		return weblink;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setLinktext(String linktext){
		this.linktext = linktext;
	}

	public String getLinktext(){
		return linktext;
	}

	public void setDisplayorder(int displayorder){
		this.displayorder = displayorder;
	}

	public int getDisplayorder(){
		return displayorder;
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
			"EventlinksItem{" + 
			"eventid = '" + eventid + '\'' + 
			",createdat = '" + createdat + '\'' + 
			",updatedby = '" + updatedby + '\'' + 
			",createdby = '" + createdby + '\'' + 
			",weblink = '" + weblink + '\'' + 
			",id = '" + id + '\'' + 
			",linktext = '" + linktext + '\'' + 
			",displayorder = '" + displayorder + '\'' + 
			",updatedat = '" + updatedat + '\'' + 
			"}";
		}
}