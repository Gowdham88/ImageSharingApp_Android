package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by czsm4 on 08/01/18.
 */

public class HBussresp{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("businessname")
    @Expose
    private String businessname;
    @SerializedName("businessdescription")
    @Expose
    private String businessdescription;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}