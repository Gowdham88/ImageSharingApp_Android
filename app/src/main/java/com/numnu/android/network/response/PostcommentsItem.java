package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class PostcommentsItem{

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("id")
	private int id;

	@SerializedName("text")
	private String text;

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
}