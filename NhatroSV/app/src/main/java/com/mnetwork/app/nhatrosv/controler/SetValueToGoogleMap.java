package com.mnetwork.app.nhatrosv.controler;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mnetwork.app.nhatrosv.custom.CustomInfoWindow;
import com.mnetwork.app.nhatrosv.database.MyDatabaseHelper;
import com.mnetwork.app.nhatrosv.model.ImageRoom;
import com.mnetwork.app.nhatrosv.model.Latlog_Room;
import com.mnetwork.app.nhatrosv.model.MotelRoom;
import com.mnetwork.app.nhatrosv.staticvalues.StaticVariables;

import java.util.ArrayList;

/**
 * Created by vanthanhbk on 31/08/2016.
 */
public class SetValueToGoogleMap {
    public static void setMarker (Activity activity, GoogleMap myMap){

        MyDatabaseHelper db = new MyDatabaseHelper(activity);
        ArrayList<MotelRoom> list = db.getAllRoom();

        int count = list.size();

        for (int i=0;i<count;i++){

            MotelRoom room = list.get(i);
            ImageRoom imageRoom = db.getListImageRoomForRoom(list.get(i).getRoom_id()).get(0);
            Latlog_Room latlogRoom =db.getListLatlog_room(room.getRoom_id()).get(0);
            // snippet =  link + price + electric + water + acr

            String marker_title = room.getRoom_type();
            String marker_snippet = imageRoom.getImage_link()+
                    StaticVariables.split+room.getRoom_price()+
                    StaticVariables.split+room.getRoom_electric_price()+
                    StaticVariables.split+room.getRoom_water_price()+
                    StaticVariables.split+room.getRoom_acreage();

            MarkerOptions options=new MarkerOptions();

            options.snippet(marker_snippet);
            options.title(marker_title);
            options.position(new LatLng(latlogRoom.getLatlog_log(),latlogRoom.getLatlog_lat()));
            options.icon(BitmapDescriptorFactory.defaultMarker());

            if (myMap != null){
                myMap.addMarker(options);
            }else Log.d("check","null roi");
        }

        myMap.setInfoWindowAdapter(new CustomInfoWindow(activity));

    }

}
