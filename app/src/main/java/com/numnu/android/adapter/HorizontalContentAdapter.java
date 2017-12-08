package com.numnu.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.numnu.android.R;
import com.numnu.android.network.response.TagsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czsm4 on 01/12/17.
 */

public class HorizontalContentAdapter extends  RecyclerView.Adapter<HorizontalContentAdapter.ViewHolder> {
    private  List<TagsItem> tags=new ArrayList<>();
    Context context;
    LayoutInflater layout;
    public HorizontalContentAdapter(Context context, List<TagsItem> tags) {
        this.context = context;
        this.tags = tags;
//        layout = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            holder.ScrollText.setText(tags.get(position).getText());
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ScrollText;
        public ViewHolder(View itemView) {
            super(itemView);
            ScrollText=(TextView)itemView.findViewById(R.id.scr_txt);
        }
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }
}
