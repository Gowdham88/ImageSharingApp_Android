package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by czsm4 on 11/01/18.
 */

public class HomeApiResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("startsat")
    @Expose
    private String startsat;
    @SerializedName("endsat")
    @Expose
    private String endsat;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("eventimages")
    @Expose
    private List<Eventimage> eventimages = null;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;
    @SerializedName("businessuserid")
    @Expose
    private Integer businessuserid;
    @SerializedName("createdat")
    @Expose
    private String createdat;
    @SerializedName("updatedat")
    @Expose
    private String updatedat;
    @SerializedName("createdby")
    @Expose
    private Integer createdby;
    @SerializedName("updatedby")
    @Expose
    private Integer updatedby;
    @SerializedName("itemimages")
    @Expose
    private List<Itemimage> itemimages = null;
    @SerializedName("businessname")
    @Expose
    private String businessname;
    @SerializedName("businessdescription")
    @Expose
    private String businessdescription;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartsat() {
        return startsat;
    }

    public void setStartsat(String startsat) {
        this.startsat = startsat;
    }

    public String getEndsat() {
        return endsat;
    }

    public void setEndsat(String endsat) {
        this.endsat = endsat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Eventimage> getEventimages() {
        return eventimages;
    }

    public void setEventimages(List<Eventimage> eventimages) {
        this.eventimages = eventimages;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Integer getBusinessuserid() {
        return businessuserid;
    }

    public void setBusinessuserid(Integer businessuserid) {
        this.businessuserid = businessuserid;
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

    public Integer getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Integer createdby) {
        this.createdby = createdby;
    }

    public Integer getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(Integer updatedby) {
        this.updatedby = updatedby;
    }

    public List<Itemimage> getItemimages() {
        return itemimages;
    }

    public void setItemimages(List<Itemimage> itemimages) {
        this.itemimages = itemimages;
    }

    public String getBusinessname() {
        return businessname;
    }

    public void setBusinessname(String businessname) {
        this.businessname = businessname;
    }

    public String getBusinessdescription() {
        return businessdescription;
    }

    public void setBusinessdescription(String businessdescription) {
        this.businessdescription = businessdescription;
    }

}
