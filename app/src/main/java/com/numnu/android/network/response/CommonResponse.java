package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thulir on 1/12/17.
 */

public class CommonResponse {

    @SerializedName("usernameexists")
    @Expose
    private Boolean usernameexists;

    @SerializedName("imageurl")
    @Expose
    private String imageurl;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }


    public Boolean getUsernameexists() {
        return usernameexists;
    }

    public void setUsernameexists(Boolean usernameexists) {
        this.usernameexists = usernameexists;
    }


}
