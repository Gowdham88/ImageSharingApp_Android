package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by czsm4 on 26/12/17.
 */

public class ItemLocationResponse {

        @SerializedName("pagination")
        @Expose
        private Pagination pagination;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;

        public Pagination getPagination() {
            return pagination;
        }

        public void setPagination(Pagination pagination) {
            this.pagination = pagination;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }


}
