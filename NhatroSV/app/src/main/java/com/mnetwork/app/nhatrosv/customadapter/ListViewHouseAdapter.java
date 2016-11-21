package com.mnetwork.app.nhatrosv.customadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mnetwork.app.nhatrosv.R;
import com.mnetwork.app.nhatrosv.database.MyDatabaseHelper;
import com.mnetwork.app.nhatrosv.model.ImageRoom;
import com.mnetwork.app.nhatrosv.model.MotelRoom;

import java.util.ArrayList;

/**
 * Created by DELL on 11/19/2016.
 */

public class ListViewHouseAdapter extends ArrayAdapter<MotelRoom> {

    private Context context;
    private ArrayList<MotelRoom> arr;

    public ListViewHouseAdapter(Context context, ArrayList<MotelRoom> objects) {
        super(context, 0, objects);
        this.context = context;
        this.arr = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater;
            inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.listview_adaptor,parent,false);
        }

        ImageView anhdaidien=(ImageView)convertView.findViewById(R.id.listview_img);
        TextView giatien=(TextView)convertView.findViewById(R.id.list_txt_giatien);
        TextView tv_addr = (TextView) convertView.findViewById(R.id.list_txt_diachi);
        TextView dientich=(TextView)convertView.findViewById(R.id.list_txt_dientich);

        MyDatabaseHelper db = new MyDatabaseHelper(context);

        MotelRoom motelRoom = arr.get(position);

        giatien.setText(String.valueOf( motelRoom.getRoom_price()));
        dientich.setText(String.valueOf(motelRoom.getRoom_acreage()));
        tv_addr.setText(motelRoom.getRoom_address());

        ImageRoom imageRoom = db.getListImageRoomForRoom(motelRoom.getRoom_id()).get(0);

        Glide.with(context).load(imageRoom.getImage_link()).into(anhdaidien);

        return convertView;
    }
}
