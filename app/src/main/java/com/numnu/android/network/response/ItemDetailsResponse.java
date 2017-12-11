package com.numnu.android.network.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ItemDetailsResponse{

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("itemimages")
	private List<ItemimagesItem> itemimages;

	@SerializedName("updatedby")
	private int updatedby;

	@SerializedName("itemlinks")
	private List<ItemlinksItem> itemlinks;

	@SerializedName("createdby")
	private int createdby;

	@SerializedName("businessuserid")
	private int businessuserid;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("updatedat")
	private String updatedat;

	@SerializedName("tags")
	private List<TagsItem> tags;

	public void setCreatedat(String createdat){
		this.createdat = createdat;
	}

	public String getCreatedat(){
		return createdat;
	}

	public void setItemimages(List<ItemimagesItem> itemimages){
		this.itemimages = itemimages;
	}

	public List<ItemimagesItem> getItemimages(){
		return itemimages;
	}

	public void setUpdatedby(int updatedby){
		this.updatedby = updatedby;
	}

	public int getUpdatedby(){
		return updatedby;
	}

	public void setItemlinks(List<ItemlinksItem> itemlinks){
		this.itemlinks = itemlinks;
	}

	public List<ItemlinksItem> getItemlinks(){
		return itemlinks;
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

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setUpdatedat(String updatedat){
		this.updatedat = updatedat;
	}

	public String getUpdatedat(){
		return updatedat;
	}

	public void setTags(List<TagsItem> tags){
		this.tags = tags;
	}

	public List<TagsItem> getTags(){
		return tags;
	}
}