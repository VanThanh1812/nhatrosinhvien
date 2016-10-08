package com.mnetwork.app.nhatrosv.firebase;

import android.app.Activity;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.maps.GoogleMap;
import com.mnetwork.app.nhatrosv.database.MyDatabaseHelper;
import com.mnetwork.app.nhatrosv.model.LatlngRoom;

import java.util.Map;

/**
 * Created by vanthanhbk on 31/08/2016.
 */
public class FirebaseLatlog {
    public static final String LINKLATLOG = "https://nha-tro-sinh-vien.firebaseio.com/latlog-room";
    public static final String LATLOG_ID="latlog_id";
    public static final String LATLOG_LOG="latlog-log";
    public static final String LATLOG_LAT="latlog-lat";

    public static void getLatlogByRoom (final Activity activity, final int id_room,final GoogleMap myMap){
        Firebase.setAndroidContext(activity);
        Firebase firebase = new Firebase(LINKLATLOG);
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("tag",dataSnapshot.toString());
                Map<String,Object> map= dataSnapshot.getValue(Map.class);


                if (!map.get(LATLOG_LOG).toString().equals(null)){
                    LatlngRoom latlog = new LatlngRoom(id_room,Double.parseDouble(map.get(LATLOG_LOG).toString()),Double.parseDouble(map.get(LATLOG_LAT).toString()));
                    MyDatabaseHelper db = new MyDatabaseHelper(activity);
                    db.addLatLogRoom(latlog);
                }
                //cha co tac dung meo gi het

                FirebaseImage.getImage(activity,Integer.parseInt(dataSnapshot.getKey()),myMap);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Map<String,Object> map= dataSnapshot.getValue(Map.class);
                LatlngRoom latlog = new LatlngRoom(id_room,Double.parseDouble(map.get(LATLOG_LOG).toString()),Double.parseDouble(map.get(LATLOG_LAT).toString()));
                MyDatabaseHelper db = new MyDatabaseHelper(activity);
                db.updateLatlogRoom(latlog);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

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
