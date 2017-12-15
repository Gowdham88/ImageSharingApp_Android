package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

public class BusinessimagesItem{

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("imageurl")
	private String imageurl;

	public void setCreatedat(String createdat){
		this.createdat = createdat;
	}

	public String getCreatedat(){
		return createdat;
	}

	public void setImageurl(String imageurl){
		this.imageurl = imageurl;
	}

	public String getImageurl(){
		return imageurl;
	}
}