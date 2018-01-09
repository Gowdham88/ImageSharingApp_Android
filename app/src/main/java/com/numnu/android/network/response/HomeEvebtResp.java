package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by czsm4 on 08/01/18.
 */

public class HomeEvebtResp {

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

}
