package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by czsm4 on 09/01/18.
 */

public class HomepostResp {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("createdat")
    @Expose
    private String createdat;
    @SerializedName("updatedat")
    @Expose
    private String updatedat;
    @SerializedName("postimages")
    @Expose
    private List<PostimagesItem> postimages = null;
    @SerializedName("postcomments")
    @Expose
    private List<PostcommentsItem> postcomments = null;
    @SerializedName("postlikes")
    @Expose
    private List<PostlikesItem> postlikes = null;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;
    @SerializedName("taggedusers")
    @Expose
    private List<Taggeduser> taggedusers = null;
    @SerializedName("taggeditems")
    @Expose
    private List<Taggeditem> taggeditems = null;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("event")
    @Expose
    private Event event;
    @SerializedName("business")
    @Expose
    private Business business;
    @SerializedName("postcreator")
    @Expose
    private Postcreator postcreator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public String getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(String updatedat) {
        this.updatedat = updatedat;
    }

    public List<PostimagesItem> getPostimages() {
        return postimages;
    }

    public void setPostimages(List<PostimagesItem> postimages) {
        this.postimages = postimages;
    }

    public List<PostcommentsItem> getPostcomments() {
        return postcomments;
    }

    public void setPostcomments(List<PostcommentsItem> postcomments) {
        this.postcomments = postcomments;
    }

    public List<PostlikesItem> getPostlikes() {
        return postlikes;
    }

    public void setPostlikes(List<PostlikesItem> postlikes) {
        this.postlikes = postlikes;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Taggeduser> getTaggedusers() {
        return taggedusers;
    }

    public void setTaggedusers(List<Taggeduser> taggedusers) {
        this.taggedusers = taggedusers;
    }

    public List<Taggeditem> getTaggeditems() {
        return taggeditems;
    }

    public void setTaggeditems(List<Taggeditem> taggeditems) {
        this.taggeditems = taggeditems;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Postcreator getPostcreator() {
        return postcreator;
    }

    public void setPostcreator(Postcreator postcreator) {
        this.postcreator = postcreator;
    }

}
