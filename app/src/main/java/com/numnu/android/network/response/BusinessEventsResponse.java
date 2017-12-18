package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessEventsResponse {

	@SerializedName("data")
	private List<EventdataItem> eventdata;

	@SerializedName("pagination")
	private Pagination pagination;

	public void setEventdata(List<EventdataItem> eventdata){
		this.eventdata = eventdata;
	}

	public List<EventdataItem> getEventdata(){
		return eventdata;
	}

	public void setPagination(Pagination pagination){
		this.pagination = pagination;
	}

	public Pagination getPagination(){
		return pagination;
	}
}