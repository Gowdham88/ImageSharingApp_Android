package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class TagsItem{

	@SerializedName("isingredient")
	private boolean isingredient;

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("id")
	private int id;

	@SerializedName("text")
	private String text;

	@SerializedName("displayorder")
	private int displayorder;

	@SerializedName("isverified")
	private boolean isverified;

	public void setIsingredient(boolean isingredient){
		this.isingredient = isingredient;
	}

	public boolean isIsingredient(){
		return isingredient;
	}

	public void setCreatedat(String createdat){
		this.createdat = createdat;
	}

	public String getCreatedat(){
		return createdat;
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

	public void setDisplayorder(int displayorder){
		this.displayorder = displayorder;
	}

	public int getDisplayorder(){
		return displayorder;
	}

	public void setIsverified(boolean isverified){
		this.isverified = isverified;
	}

	public boolean isIsverified(){
		return isverified;
	}
}