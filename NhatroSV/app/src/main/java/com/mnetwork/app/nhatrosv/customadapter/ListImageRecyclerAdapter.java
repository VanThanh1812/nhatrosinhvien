package com.mnetwork.app.nhatrosv.customadapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnetwork.app.nhatrosv.R;
import com.mnetwork.app.nhatrosv.model.ImageRoom;

import java.util.ArrayList;

/**
 * Created by vanthanhbk on 17/10/2016.
 */
public class ListImageRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private ArrayList<ImageRoom> imageRooms;
    private Activity activity;

    public ListImageRecyclerAdapter(Activity activity, ArrayList<ImageRoom> imageRooms) {

        this.activity = activity;
        this.imageRooms = imageRooms;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,null,false);

        return new RecyclerViewHolder(v);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.bindImage(imageRooms.get(position));
    }

    @Override
    public int getItemCount() {
        return imageRooms.size();
    }
}
