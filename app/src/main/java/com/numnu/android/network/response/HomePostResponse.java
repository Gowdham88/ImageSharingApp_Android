package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by czsm4 on 09/01/18.
 */

public class HomePostResponse {
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("data")
    @Expose
    private List<HomepostResp> data = null;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<HomepostResp> getData() {
        return data;
    }

    public void setData(List<HomepostResp> data) {
        this.data = data;
    }

}
