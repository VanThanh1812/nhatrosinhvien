package com.mnetwork.app.nhatrosv.firebase;

import android.app.Activity;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.maps.GoogleMap;
import com.mnetwork.app.nhatrosv.database.MyDatabaseHelper;
import com.mnetwork.app.nhatrosv.model.MotelRoom;

import java.util.Map;

/**
 * Created by vanthanhbk on 31/08/2016.
 */
public class FirebaseMotelRoom {
    public static final String LINKROOM= "https://nha-tro-sinh-vien.firebaseio.com/motel-room";

    public static final String ROOM_COLUMN_ADDRESS = "room-address";
    public static final String ROOM_COLUMN_TYPE = "room-type";
    public static final String ROOM_COLUMN_PRICE = "room-price";
    public static final String ROOM_COLUMN_ELEC_PRICE = "room-electric-price";
    public static final String ROOM_COLUMN_WATER_PRICE = "room-water-price";
    public static final String ROOM_COLUMN_ACR = "room-acreage";
    public static final String ROOM_COLUMN_DESCRIBE = "room-describe";
    public static final String ROOM_COLUMN_RATE = "room-rate";
    public static final String ROOM_COLUMN_STATUS = "room-status";
    public static final String ROOM_COLUMN_OWNER_ID = "room-id-owner";

    public static void getRoom (final Activity activity, final int id_owner,final GoogleMap myMap){
        Firebase.setAndroidContext(activity);
        Firebase root =new Firebase(LINKROOM);
        root.child(String.valueOf(id_owner)).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("idhouse",dataSnapshot.getKey());
                Map<String,Object> map =dataSnapshot.getValue(Map.class);
                MotelRoom room =new MotelRoom();

                room.setRoom_id(Integer.parseInt(dataSnapshot.getKey()));
                room.setRoom_acreage(Double.parseDouble(map.get(ROOM_COLUMN_ACR).toString()));
                room.setRoom_address(map.get(ROOM_COLUMN_ADDRESS).toString());
                room.setRoom_describe(map.get(ROOM_COLUMN_DESCRIBE).toString());
                room.setRoom_electric_price(Double.parseDouble(map.get(ROOM_COLUMN_ELEC_PRICE).toString()));
                room.setRoom_price(Double.parseDouble(map.get(ROOM_COLUMN_PRICE).toString()));
                room.setRoom_water_price(Double.parseDouble(map.get(ROOM_COLUMN_WATER_PRICE).toString()));
                room.setRoom_id_owner(id_owner);
                room.setRoom_rate(Integer.parseInt(map.get(ROOM_COLUMN_RATE).toString()));
                room.setRoom_type(map.get(ROOM_COLUMN_TYPE).toString());
                room.setRoom_status(map.get(ROOM_COLUMN_STATUS).toString());

                MyDatabaseHelper db =new MyDatabaseHelper(activity);
                db.addMotelRoom(room);


                Log.d("count",String.valueOf(dataSnapshot.getKey()));

                FirebaseLatlog.getLatlogByRoom(activity,Integer.parseInt(dataSnapshot.getKey()),myMap);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Map<String,Object> map =dataSnapshot.getValue(Map.class);
                MotelRoom room =new MotelRoom();

                room.setRoom_id(Integer.parseInt(dataSnapshot.getKey()));
                room.setRoom_acreage(Double.parseDouble(map.get(ROOM_COLUMN_ACR).toString()));
                room.setRoom_address(map.get(ROOM_COLUMN_ADDRESS).toString());
                room.setRoom_describe(map.get(ROOM_COLUMN_DESCRIBE).toString());
                room.setRoom_electric_price(Double.parseDouble(map.get(ROOM_COLUMN_ELEC_PRICE).toString()));
                room.setRoom_price(Double.parseDouble(map.get(ROOM_COLUMN_PRICE).toString()));
                room.setRoom_water_price(Double.parseDouble(map.get(ROOM_COLUMN_WATER_PRICE).toString()));
                room.setRoom_id_owner(id_owner);
                room.setRoom_rate(Integer.parseInt(map.get(ROOM_COLUMN_RATE).toString()));
                room.setRoom_type(map.get(ROOM_COLUMN_TYPE).toString());
                room.setRoom_status(map.get(ROOM_COLUMN_STATUS).toString());

                MyDatabaseHelper db =new MyDatabaseHelper(activity);
                db.updateMotelRoom(room);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MyDatabaseHelper db = new MyDatabaseHelper(activity);
                db.deleteMotelRoomById(Integer.parseInt(dataSnapshot.getKey()));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
