package com.numnu.android.network.response;

public class Pagination{
	private int limit;
	private boolean hasMore;
	private int totalPages;
	private Links links;
	private boolean hasPreviousPages;
	private int totalRows;
	private int currentPage;

	public void setLimit(int limit){
		this.limit = limit;
	}

	public int getLimit(){
		return limit;
	}

	public void setHasMore(boolean hasMore){
		this.hasMore = hasMore;
	}

	public boolean isHasMore(){
		return hasMore;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setLinks(Links links){
		this.links = links;
	}

	public Links getLinks(){
		return links;
	}

	public void setHasPreviousPages(boolean hasPreviousPages){
		this.hasPreviousPages = hasPreviousPages;
	}

	public boolean isHasPreviousPages(){
		return hasPreviousPages;
	}

	public void setTotalRows(int totalRows){
		this.totalRows = totalRows;
	}

	public int getTotalRows(){
		return totalRows;
	}

	public void setCurrentPage(int currentPage){
		this.currentPage = currentPage;
	}

	public int getCurrentPage(){
		return currentPage;
	}
}
