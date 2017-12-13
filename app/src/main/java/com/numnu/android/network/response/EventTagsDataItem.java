package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class EventTagsDataItem{

	@SerializedName("tagid")
	private int tagid;

	@SerializedName("itemcount")
	private int itemcount;

	@SerializedName("tagtext")
	private String tagtext;

	public void setTagid(int tagid){
		this.tagid = tagid;
	}

	public int getTagid(){
		return tagid;
	}

	public void setItemcount(int itemcount){
		this.itemcount = itemcount;
	}

	public int getItemcount(){
		return itemcount;
	}

	public void setTagtext(String tagtext){
		this.tagtext = tagtext;
	}

	public String getTagtext(){
		return tagtext;
	}
}