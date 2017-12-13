package com.numnu.android.network.response;

import java.util.List;

public class EventTagBusiness{
	private String createdat;
	private List<ItemimagesItem> itemimages;
	private int updatedby;
	private int createdby;
	private int businessuserid;
	private String name;
	private String description;
	private String businessname;
	private int id;
	private String updatedat;
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

	public void setBusinessname(String businessname){
		this.businessname = businessname;
	}

	public String getBusinessname(){
		return businessname;
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

	@Override
 	public String toString(){
		return 
			"EventTagBusiness{" + 
			"createdat = '" + createdat + '\'' + 
			",itemimages = '" + itemimages + '\'' + 
			",updatedby = '" + updatedby + '\'' + 
			",createdby = '" + createdby + '\'' + 
			",businessuserid = '" + businessuserid + '\'' + 
			",name = '" + name + '\'' + 
			",description = '" + description + '\'' + 
			",businessname = '" + businessname + '\'' + 
			",id = '" + id + '\'' + 
			",updatedat = '" + updatedat + '\'' + 
			",tags = '" + tags + '\'' + 
			"}";
		}
}