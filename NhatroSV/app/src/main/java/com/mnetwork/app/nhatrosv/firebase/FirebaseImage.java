package com.mnetwork.app.nhatrosv.firebase;

import android.app.Activity;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mnetwork.app.nhatrosv.R;
import com.mnetwork.app.nhatrosv.customadapter.CustomInfoWindow;
import com.mnetwork.app.nhatrosv.database.MyDatabaseHelper;
import com.mnetwork.app.nhatrosv.model.ImageRoom;
import com.mnetwork.app.nhatrosv.model.LatlngRoom;
import com.mnetwork.app.nhatrosv.model.MotelRoom;
import com.mnetwork.app.nhatrosv.staticvalues.StaticVariables;

/**
 * Created by vanthanhbk on 30/08/2016.
 */
public class FirebaseImage {
    final private static String LINKIMAGE="https://nha-tro-sinh-vien.firebaseio.com/image-room";
    public static final String TAG="sqlite";
    public static final String IMAGE_ID="image-id";
    public static final String IMAGE_LINK="image-link";
    public static final String IMAGE_ID_OWNER="image-id-owner";

    public static void getImage (final Activity activity, final int id_room,final GoogleMap myMap){
        Firebase.setAndroidContext(activity);
        Firebase root =new Firebase(LINKIMAGE);
        Log.d(TAG,String.valueOf(id_room));
        root.child(String.valueOf(id_room)).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Map<String,Object> map = dataSnapshot.getValue(Map.class);
                Log.d("sqlite","them anh");
                ImageRoom image = new ImageRoom();
                image.setImage_id(Integer.parseInt(dataSnapshot.getKey()));
                image.setImage_link(dataSnapshot.getValue().toString());
                image.setRoom_id(id_room);

                MyDatabaseHelper db =new MyDatabaseHelper(activity);
                db.addImageRoom(image);

                setValuesOnline(activity,id_room,myMap,db);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //ap<String,Object> map = dataSnapshot.getValue(Map.class);

                ImageRoom image = new ImageRoom();
                image.setImage_id(Integer.parseInt(dataSnapshot.getKey()));
                image.setImage_link(dataSnapshot.getValue().toString());
                image.setRoom_id(id_room);

                MyDatabaseHelper db =new MyDatabaseHelper(activity);
                db.updateImageRoom(image);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MyDatabaseHelper db = new MyDatabaseHelper(activity);
                db.deleteImagebyId(Integer.parseInt(dataSnapshot.getKey()));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private static void setValuesOnline (Activity activity,int id_room, GoogleMap myMap,MyDatabaseHelper db){

        MotelRoom room = db.getMotelRoomById(id_room);

        ImageRoom imageRoom = db.getListImageRoomForRoom(room.getRoom_id()).get(0);

        LatlngRoom latlogRoom =db.getListLatlog_room(room.getRoom_id()).get(0);
        // snippet =  link + price + electric + water + acr

        String marker_title = String.valueOf(room.getRoom_id())+StaticVariables.split+room.getRoom_type();

        String marker_snippet = imageRoom.getImage_link()+
                StaticVariables.split+room.getRoom_price()+
                StaticVariables.split+room.getRoom_electric_price()+
                StaticVariables.split+room.getRoom_water_price()+
                StaticVariables.split+room.getRoom_acreage();

        MarkerOptions options=new MarkerOptions();

        options.snippet(marker_snippet);
        options.title(marker_title);
        options.position(new LatLng(latlogRoom.getLatlog_log(),latlogRoom.getLatlog_lat()));
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker));

        if (myMap != null){
            myMap.addMarker(options);
            Log.d("addhouseonline",options.getTitle()+"  "+String.valueOf(latlogRoom.getLatlog_log()));
        }else Log.d("check","null roi");

        myMap.setInfoWindowAdapter(new CustomInfoWindow(activity));
    }
}
