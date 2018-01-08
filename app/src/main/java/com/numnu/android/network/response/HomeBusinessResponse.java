package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by czsm4 on 08/01/18.
 */

public class HomeBusinessResponse  {

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("data")
    @Expose
    private List<HBussresp> data = null;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<HBussresp> getData() {
        return data;
    }

    public void setData(List<HBussresp> data) {
        this.data = data;
    }

}