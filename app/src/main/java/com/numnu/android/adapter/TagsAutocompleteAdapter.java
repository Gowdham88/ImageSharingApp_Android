/*
 * Copyright (C) 2015 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.numnu.android.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.AutocompletePrediction;
import com.numnu.android.R;
import com.numnu.android.network.ApiServices;
import com.numnu.android.network.response.TagsResponse;
import com.numnu.android.network.response.Tagsuggestion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Adapter that handles Autocomplete requests from the Places Geo Data Client.
 * {@link Tagsuggestion} results from the API are frozen and stored directly in this
 * adapter. (See {@link AutocompletePrediction#freeze()}.)
 */
public class TagsAutocompleteAdapter
        extends ArrayAdapter<Tagsuggestion> implements Filterable {

    private static final String TAG = "PlaceAutocomplete";
    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);
    /**
     * Current results returned by this adapter.
     */
    private ArrayList<Tagsuggestion> mResultList;

    /**
     * Handles autocomplete requests.
     */
    private ApiServices apiServices;

    private Context context;

    /**
     * Initializes with a resource for text rows and autocomplete query bounds.
     *
     * @see ArrayAdapter#ArrayAdapter(Context, int)
     */
    public TagsAutocompleteAdapter(Context context, ApiServices geoDataClient) {
        super(context, R.layout.auto_dialog, R.id.lbl_name);
        this.context = context;
        apiServices = geoDataClient;
    }


    /**
     * Returns the number of results received in the last autocomplete query.
     */
    @Override
    public int getCount() {
        return mResultList.size();
    }

    /**
     * Returns an item from the last autocomplete query.
     */
    @Override
    public Tagsuggestion getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = super.getView(position, convertView, parent);

        // Sets the primary and secondary text for a row.
        // Note that getPrimaryText() and getSecondaryText() return a CharSequence that may contain
        // styling based on the given CharacterStyle.

        Tagsuggestion item = getItem(position);

        TextView textView1 = row.findViewById(R.id.lbl_name);
        textView1.setText(item.getText());

        return row;
    }

    /**
     * Returns the filter for the current set of autocomplete results.
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                // We need a separate list to store the results, since
                // this is run asynchronously.
                ArrayList<Tagsuggestion> filterData = new ArrayList<>();

                // Skip the autocomplete query if no constraints are given.
                if (constraint != null) {
                    // Query the autocomplete API for the (constraint) search string.
                    filterData = getSuggestions(constraint.toString());
                }

                results.values = filterData;
                if (filterData != null) {
                    results.count = filterData.size();
                } else {
                    results.count = 0;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    mResultList = (ArrayList<Tagsuggestion>) results.values;
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                // Override this method to display a readable result in the AutocompleteTextView
                // when clicked.
                if (resultValue instanceof AutocompletePrediction) {
                    return ((AutocompletePrediction) resultValue).getFullText(null);
                } else {
                    return super.convertResultToString(resultValue);
                }
            }
        };
    }

    /**
     * Submits an autocomplete query to the Tags Autocomplete API.
     * Results are returned as frozen Tagsuggestion objects, ready to be cached.
     * Returns an empty list if no results were found.
     */
    private ArrayList<Tagsuggestion> getSuggestions(String s)  {

        Call<TagsResponse> call = apiServices.getTags(s);
        ArrayList<Tagsuggestion> tagsuggestionArrayList= null;
        try {
            tagsuggestionArrayList = (ArrayList<Tagsuggestion>) call.execute().body().getTagsuggestions();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tagsuggestionArrayList;
    }

}
