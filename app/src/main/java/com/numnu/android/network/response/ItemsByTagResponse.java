package com.numnu.android.network.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ItemsByTagResponse{

	@SerializedName("pagination")
	private Pagination pagination;

	@SerializedName("data")
	private List<EventTagBusiness> data;

	public void setPagination(Pagination pagination){
		this.pagination = pagination;
	}

	public Pagination getPagination(){
		return pagination;
	}

	public void setData(List<EventTagBusiness> data){
		this.data = data;
	}

	public List<EventTagBusiness> getData(){
		return data;
	}
}