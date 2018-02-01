package com.numnu.android.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostdataItem{

	@SerializedName("postlocation")
	private Postlocation postlocation;

	@SerializedName("business")
	private Business business;

	@SerializedName("rating")
	private int rating;

	@SerializedName("postlikes")
	private List<PostlikesItem> postlikes;

	@SerializedName("taggedusers")
	private List<TaggedusersItem> taggedusers;

	@SerializedName("postcomments")
	private List<PostcommentsItem> postcomments;

	@SerializedName("tags")
	private List<TagsItem> tags;

	@SerializedName("createdat")
	private String createdat;

	@SerializedName("postcreator")
	private Postcreator postcreator;

	@SerializedName("comment")
	private String comment;

	@SerializedName("postimages")
	private List<PostimagesItem> postimages;

	@SerializedName("taggeditems")
	private List<TaggeditemsItem> taggeditems;

	@SerializedName("id")
	private int id;

	@SerializedName("event")
	private Event event;

	@SerializedName("updatedat")
	private String updatedat;

	public void setPostlocation(Postlocation postlocation){
		this.postlocation = postlocation;
	}

	public Postlocation getPostlocation(){
		return postlocation;
	}

	public void setBusiness(Business business){
		this.business = business;
	}

	public Business getBusiness(){
		return business;
	}

	public void setRating(int rating){
		this.rating = rating;
	}

	public int getRating(){
		return rating;
	}

	public void setPostlikes(List<PostlikesItem> postlikes){
		this.postlikes = postlikes;
	}

	public List<PostlikesItem> getPostlikes(){
		return postlikes;
	}

	public void setTaggedusers(List<TaggedusersItem> taggedusers){
		this.taggedusers = taggedusers;
	}

	public List<TaggedusersItem> getTaggedusers(){
		return taggedusers;
	}

	public void setPostcomments(List<PostcommentsItem> postcomments){
		this.postcomments = postcomments;
	}

	public List<PostcommentsItem> getPostcomments(){
		return postcomments;
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

	public void setPostcreator(Postcreator postcreator){
		this.postcreator = postcreator;
	}

	public Postcreator getPostcreator(){
		return postcreator;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return comment;
	}

	public void setPostimages(List<PostimagesItem> postimages){
		this.postimages = postimages;
	}

	public List<PostimagesItem> getPostimages(){
		return postimages;
	}

	public void setTaggeditems(List<TaggeditemsItem> taggeditems){
		this.taggeditems = taggeditems;
	}

	public List<TaggeditemsItem> getTaggeditems(){
		return taggeditems;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setEvent(Event event){
		this.event = event;
	}

	public Event getEvent(){
		return event;
	}

	public void setUpdatedat(String updatedat){
		this.updatedat = updatedat;
	}

	public String getUpdatedat(){
		return updatedat;
	}
}