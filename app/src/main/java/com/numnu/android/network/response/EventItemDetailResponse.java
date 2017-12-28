package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventItemDetailResponse{

	@SerializedName("itemid")
	private int itemid;

	@SerializedName("itemdescription")
	private String itemdescription;

	@SerializedName("eventid")
	private int eventid;

	@SerializedName("itemtags")
	private List<TagsItem> itemtags;

	@SerializedName("itemimages")
	private List<ItemimagesItem> itemimages;

	@SerializedName("itemname")
	private String itemname;

	@SerializedName("businessuserid")
	private int businessuserid;

	@SerializedName("priceamount")
	private String priceamount;

	@SerializedName("businessname")
	private String businessname;

	@SerializedName("currencyid")
	private int currencyid;

	@SerializedName("currencycode")
	private String currencycode;

	@SerializedName("eventname")
	private String eventname;

	public void setItemid(int itemid){
		this.itemid = itemid;
	}

	public int getItemid(){
		return itemid;
	}

	public void setItemdescription(String itemdescription){
		this.itemdescription = itemdescription;
	}

	public String getItemdescription(){
		return itemdescription;
	}

	public void setEventid(int eventid){
		this.eventid = eventid;
	}

	public int getEventid(){
		return eventid;
	}

	public void setItemtags(List<TagsItem> itemtags){
		this.itemtags = itemtags;
	}

	public List<TagsItem> getItemtags(){
		return itemtags;
	}

	public void setItemimages(List<ItemimagesItem> itemimages){
		this.itemimages = itemimages;
	}

	public List<ItemimagesItem> getItemimages(){
		return itemimages;
	}

	public void setItemname(String itemname){
		this.itemname = itemname;
	}

	public String getItemname(){
		return itemname;
	}

	public void setBusinessuserid(int businessuserid){
		this.businessuserid = businessuserid;
	}

	public int getBusinessuserid(){
		return businessuserid;
	}

	public void setPriceamount(String priceamount){
		this.priceamount = priceamount;
	}

	public String getPriceamount(){
		return priceamount;
	}

	public void setBusinessname(String businessname){
		this.businessname = businessname;
	}

	public String getBusinessname(){
		return businessname;
	}

	public void setCurrencyid(int currencyid){
		this.currencyid = currencyid;
	}

	public int getCurrencyid(){
		return currencyid;
	}

	public void setCurrencycode(String currencycode){
		this.currencycode = currencycode;
	}

	public String getCurrencycode(){
		return currencycode;
	}

	public void setEventname(String eventname){
		this.eventname = eventname;
	}

	public String getEventname(){
		return eventname;
	}
}