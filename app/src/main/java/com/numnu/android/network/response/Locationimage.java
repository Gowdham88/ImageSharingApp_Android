package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by czsm4 on 26/12/17.
 */

public class Locationimage {

    @SerializedName("imageurl")
    @Expose
    private String imageurl;
    @SerializedName("createdat")
    @Expose
    private String createdat;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

}