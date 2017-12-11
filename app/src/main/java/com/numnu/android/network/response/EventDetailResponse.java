package com.numnu.android.network.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class EventDetailResponse{

	@SerializedName("eventlinks")
	private List<EventlinksItem> eventlinks;

	@SerializedName("updatedby")
	private int updatedby;

	@SerializedName("description")
	private String description;

	@SerializedName("tags")
	private List<TagsItem> tags;

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("eventimages")
	private List<EventimagesItem> eventimages;

	@SerializedName("isdetailedcontentpublished")
	private boolean isdetailedcontentpublished;

	@SerializedName("ispublished")
	private boolean ispublished;

	@SerializedName("createdby")
	private int createdby;

	@SerializedName("businessuserid")
	private int businessuserid;

	@SerializedName("eventtypeid")
	private int eventtypeid;

	@SerializedName("name")
	private String name;

	@SerializedName("startsat")
	private String startsat;

	@SerializedName("location")
	private Location location;

	@SerializedName("id")
	private int id;

	@SerializedName("locationsummary")
	private String locationsummary;

	@SerializedName("endsat")
	private String endsat;

	@SerializedName("updatedat")
	private String updatedat;

	public void setEventlinks(List<EventlinksItem> eventlinks){
		this.eventlinks = eventlinks;
	}

	public List<EventlinksItem> getEventlinks(){
		return eventlinks;
	}

	public void setUpdatedby(int updatedby){
		this.updatedby = updatedby;
	}

	public int getUpdatedby(){
		return updatedby;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setTags(List<TagsItem> tags){
		this.tags = tags;
	}

	public List<TagsItem> getTags(){
		return tags;
	}

	public void setCreatedat(String createdat){
		this.createdat = createdat;
	}

	public String getCreatedat(){
		return createdat;
	}

	public void setEventimages(List<EventimagesItem> eventimages){
		this.eventimages = eventimages;
	}

	public List<EventimagesItem> getEventimages(){
		return eventimages;
	}

	public void setIsdetailedcontentpublished(boolean isdetailedcontentpublished){
		this.isdetailedcontentpublished = isdetailedcontentpublished;
	}

	public boolean isIsdetailedcontentpublished(){
		return isdetailedcontentpublished;
	}

	public void setIspublished(boolean ispublished){
		this.ispublished = ispublished;
	}

	public boolean isIspublished(){
		return ispublished;
	}

	public void setCreatedby(int createdby){
		this.createdby = createdby;
	}

	public int getCreatedby(){
		return createdby;
	}

	public void setBusinessuserid(int businessuserid){
		this.businessuserid = businessuserid;
	}

	public int getBusinessuserid(){
		return businessuserid;
	}

	public void setEventtypeid(int eventtypeid){
		this.eventtypeid = eventtypeid;
	}

	public int getEventtypeid(){
		return eventtypeid;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setStartsat(String startsat){
		this.startsat = startsat;
	}

	public String getStartsat(){
		return startsat;
	}

	public void setLocation(Location location){
		this.location = location;
	}

	public Location getLocation(){
		return location;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setLocationsummary(String locationsummary){
		this.locationsummary = locationsummary;
	}

	public String getLocationsummary(){
		return locationsummary;
	}

	public void setEndsat(String endsat){
		this.endsat = endsat;
	}

	public String getEndsat(){
		return endsat;
	}

	public void setUpdatedat(String updatedat){
		this.updatedat = updatedat;
	}

	public String getUpdatedat(){
		return updatedat;
	}

}