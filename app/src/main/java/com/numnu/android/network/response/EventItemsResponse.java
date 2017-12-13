package com.numnu.android.network.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class EventItemsResponse {

	@SerializedName("pagination")
	private Pagination pagination;

	@SerializedName("data")
	private List<EventTagsDataItem> data;

	public void setPagination(Pagination pagination){
		this.pagination = pagination;
	}

	public Pagination getPagination(){
		return pagination;
	}

	public void setData(List<EventTagsDataItem> data){
		this.data = data;
	}

	public List<EventTagsDataItem> getData(){
		return data;
	}
}