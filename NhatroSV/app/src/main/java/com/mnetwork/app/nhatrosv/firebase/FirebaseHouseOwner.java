package com.mnetwork.app.nhatrosv.firebase;

import android.app.Activity;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.maps.GoogleMap;
import com.mnetwork.app.nhatrosv.database.MyDatabaseHelper;
import com.mnetwork.app.nhatrosv.model.HouseOwner;

import java.util.Map;

/**
 * Created by vanthanhbk on 29/08/2016.
 */
public class FirebaseHouseOwner {
    final private static String HOUSEOWNER="https://nha-tro-sinh-vien.firebaseio.com/house-owner";
    public static final String TAG= "owner";
    public static final String KEY_OWNER_id="owner-id";
    public static final String KEY_OWNER_name="owner-name";
    public static final String KEY_OWNER_age="owner-age";
    public static final String KEY_OWNER_phone="owner-phone";
    public static final String KEY_OWNER_email="owner-email";
    public static final String KEY_OWNER_address="owner-address";

    public static void getDataToDatabase (final Activity activity, final MyDatabaseHelper db,final GoogleMap myMap){
        Firebase.setAndroidContext(activity);
        Firebase firebase= new Firebase(HOUSEOWNER);
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Map<String, Object> map = dataSnapshot.getValue(Map.class);
                HouseOwner owner =new HouseOwner();

                owner.setOwner_id(Integer.parseInt(dataSnapshot.getKey()));
                owner.setOwner_address(map.get(KEY_OWNER_address).toString());
                owner.setOwner_name(map.get(KEY_OWNER_name).toString());
                owner.setOwner_age(Integer.parseInt(map.get(KEY_OWNER_age).toString()));
                owner.setOwner_phone(map.get(KEY_OWNER_phone).toString());
                owner.setOwner_email(map.get(KEY_OWNER_email).toString());

                db.addHouseOwner(owner);

                FirebaseMotelRoom.getRoom(activity,Integer.parseInt(dataSnapshot.getKey()),myMap);

                //Log.d("demo",db.getOwner(1001).toString());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                Map<String, Object> map = dataSnapshot.getValue(Map.class);
                HouseOwner owner =new HouseOwner();

                owner.setOwner_id(Integer.parseInt(dataSnapshot.getKey()));
                owner.setOwner_address(map.get(KEY_OWNER_address).toString());
                owner.setOwner_name(map.get(KEY_OWNER_name).toString());
                owner.setOwner_age(Integer.parseInt(map.get(KEY_OWNER_age).toString()));
                owner.setOwner_phone(map.get(KEY_OWNER_phone).toString());
                owner.setOwner_email(map.get(KEY_OWNER_email).toString());

                db.updateOwner(owner);
                Log.d(TAG,db.getOwner(owner.getOwner_id()).toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                MyDatabaseHelper db = new MyDatabaseHelper(activity);
                db.deleteHouseOwnerById(Integer.parseInt(dataSnapshot.getKey()));

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
