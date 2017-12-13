package com.numnu.android.network.response;

public class ItemimagesItem{
	private String createdat;
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

	@Override
 	public String toString(){
		return 
			"ItemimagesItem{" + 
			"createdat = '" + createdat + '\'' + 
			",imageurl = '" + imageurl + '\'' + 
			"}";
		}
}
