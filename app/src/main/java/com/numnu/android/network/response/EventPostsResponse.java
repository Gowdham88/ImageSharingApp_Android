package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventPostsResponse {

	@SerializedName("pagination")
	private Pagination pagination;

	@SerializedName("data")
	private List<PostdataItem> postdata;

	public void setPagination(Pagination pagination){
		this.pagination = pagination;
	}

	public Pagination getPagination(){
		return pagination;
	}

	public void setPostdata(List<PostdataItem> postdata){
		this.postdata = postdata;
	}

	public List<PostdataItem> getPostdata(){
		return postdata;
	}
}