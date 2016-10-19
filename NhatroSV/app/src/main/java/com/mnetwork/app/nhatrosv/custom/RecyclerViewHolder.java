package com.mnetwork.app.nhatrosv.custom;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mnetwork.app.nhatrosv.R;
import com.mnetwork.app.nhatrosv.model.ImageRoom;

/**
 * Created by vanthanhbk on 17/10/2016.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{

    ImageView iv_item;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        iv_item = (ImageView) itemView.findViewById(R.id.iv_item_listimage);
    }

    public void bindImage (ImageRoom imageRoom){
        Glide.with(iv_item.getContext()).load(imageRoom.getImage_link()).centerCrop().placeholder(R.drawable.ic_menu_gallery).into(iv_item);
    }

    @Override
    public void onClick(View view) {

    }
}
