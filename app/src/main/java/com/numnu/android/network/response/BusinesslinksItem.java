package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class BusinesslinksItem{

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("weblink")
	private String weblink;

	@SerializedName("id")
	private int id;

	@SerializedName("linktext")
	private String linktext;

	@SerializedName("displayorder")
	private int displayorder;

	public void setCreatedat(String createdat){
		this.createdat = createdat;
	}

	public String getCreatedat(){
		return createdat;
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
}