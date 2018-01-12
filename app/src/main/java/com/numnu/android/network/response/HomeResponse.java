package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by czsm4 on 11/01/18.
 */

public class HomeResponse {

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("data")
    @Expose
    private List<HomeApiResponse> data = null;
    @SerializedName("listTitle")
    @Expose
    private String listTitle;
    @SerializedName("listIndex")
    @Expose
    private Integer listIndex;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<HomeApiResponse> getData() {
        return data;
    }

    public void setData(List<HomeApiResponse> data) {
        this.data = data;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public Integer getListIndex() {
        return listIndex;
    }

    public void setListIndex(Integer listIndex) {
        this.listIndex = listIndex;
    }

}