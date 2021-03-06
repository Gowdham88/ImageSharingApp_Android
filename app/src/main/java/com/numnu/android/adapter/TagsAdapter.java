package com.numnu.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.numnu.android.R;

import java.util.ArrayList;

/**
 * Created by czsm4 on 07/12/17.
 */

public class TagsAdapter extends  RecyclerView.Adapter<TagsAdapter.ViewHolder> {
        String[] arr = {"Food","Wine", "Rum", "Food", "Rum","Wine"};
        Context context;
        LayoutInflater layout;
        ArrayList<String> tagslist = new ArrayList<>();
public TagsAdapter(Context context, ArrayList<String> taglist) {
        this.context = context;
        this.tagslist = taglist;
//        layout = LayoutInflater.from(context);

        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_tags_content, parent, false);
        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ScrollText.setText(tagslist.get(position));
        }



public class ViewHolder extends RecyclerView.ViewHolder {
    TextView ScrollText;
    public ViewHolder(View itemView) {
        super(itemView);
        ScrollText=(TextView)itemView.findViewById(R.id.horscr_txt);
    }
}

    @Override
    public int getItemCount() {
        return arr.length;
    }
}


