package com.mnetwork.app.nhatrosv.controller;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mnetwork.app.nhatrosv.database.MyDatabaseHelper;
import com.mnetwork.app.nhatrosv.model.HouseOwner;
import com.mnetwork.app.nhatrosv.model.ImageRoom;
import com.mnetwork.app.nhatrosv.model.LatlngRoom;
import com.mnetwork.app.nhatrosv.model.MotelRoom;
import com.mnetwork.app.nhatrosv.staticvalues.StaticVariables;

import java.util.ArrayList;

/**
 * Created by vanthanhbk on 31/08/2016.
 */
public class MapControl {

    public static void setMarker(final Context activity, com.google.android.gms.maps.GoogleMap myMap, ArrayList<MotelRoom> list) {

        myMap.clear();

        MyDatabaseHelper db = new MyDatabaseHelper(activity);

        int count = list.size();

        for (int i = 0; i < count; i++) {

            MotelRoom room = list.get(i);//ko du 3g de load du lieu

            Log.d("setForRoom", String.valueOf(room.getRoom_id()));

            LatlngRoom latlogRoom = db.getListLatlog_room(room.getRoom_id()).get(0);

            HouseOwner owner = db.getOwner(room.getRoom_id_owner());

            ImageRoom imageRoom = db.getListImageRoomForRoom(room.getRoom_id()).get(0);

            String marker_title = String.valueOf(room.getRoom_id()) + StaticVariables.split + room.getRoom_type();

            String marker_snippet = imageRoom.getImage_link() +
                    StaticVariables.split + room.getRoom_address() +
                    StaticVariables.split + owner.getOwner_phone() +
                    StaticVariables.split + room.getRoom_id()+
                    StaticVariables.split + room.getRoom_price();

            MarkerOptions options = new MarkerOptions();

            options.snippet(marker_snippet);
            options.title(marker_title);
            options.position(new LatLng(latlogRoom.getLatlog_log(), latlogRoom.getLatlog_lat()));
            options.icon(BitmapDescriptorFactory.defaultMarker());

            if (myMap != null) {
                myMap.addMarker(options);
                Log.d("addhouseoffline", options.getTitle() + "  " + String.valueOf(latlogRoom.getLatlog_log()));
            } else Log.d("check", "null roi");

        }
    }
}
