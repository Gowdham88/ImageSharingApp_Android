package com.numnu.android.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by thulir on 1/12/17.
 */

public class TagsResponse {

        @SerializedName("tagsuggestions")
        @Expose
        private List<Tagsuggestion> tagsuggestions = null;

        public List<Tagsuggestion> getTagsuggestions() {
            return tagsuggestions;
        }

        public void setTagsuggestions(List<Tagsuggestion> tagsuggestions) {
            this.tagsuggestions = tagsuggestions;
        }

    }
