package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetBookmarksResponse{

	@SerializedName("pagination")
	private Pagination pagination;

	@SerializedName("bookmarkdata")
	private List<BookmarkdataItem> bookmarkdata;

	public void setPagination(Pagination pagination){
		this.pagination = pagination;
	}

	public Pagination getPagination(){
		return pagination;
	}

	public void setBookmarkdata(List<BookmarkdataItem> bookmarkdata){
		this.bookmarkdata = bookmarkdata;
	}

	public List<BookmarkdataItem> getBookmarkdata(){
		return bookmarkdata;
	}
}