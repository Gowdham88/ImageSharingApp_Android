package com.numnu.android.network.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class EventBusinessesResponse{

    private Pagination pagination;
    private List<DataItem> data;

    public void setPagination(Pagination pagination){
        this.pagination = pagination;
    }

    public Pagination getPagination(){
        return pagination;
    }

    public void setData(List<DataItem> data){
        this.data = data;
    }

    public List<DataItem> getData(){
        return data;
    }
}