package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class ItemlinksItem{

	@SerializedName("itemid")
	private int itemid;

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
}