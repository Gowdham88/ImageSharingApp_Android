package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventdataItem{

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("eventimages")
	private List<EventimagesItem> eventimages;

	@SerializedName("name")
	private String name;

	@SerializedName("startsat")
	private String startsat;

	@SerializedName("description")
	private String description;

	@SerializedName("location")
	private Location location;

	@SerializedName("id")
	private int id;

	@SerializedName("endsat")
	private String endsat;

	@SerializedName("tags")
	private List<TagsItem> tags;

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

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
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

	public void setEndsat(String endsat){
		this.endsat = endsat;
	}

	public String getEndsat(){
		return endsat;
	}

	public void setTags(List<TagsItem> tags){
		this.tags = tags;
	}

	public List<TagsItem> getTags(){
		return tags;
	}
}