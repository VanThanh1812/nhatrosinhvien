package com.mnetwork.app.nhatrosv.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mnetwork.app.nhatrosv.R;
import com.mnetwork.app.nhatrosv.activitys.RoomDetailActivity;
import com.mnetwork.app.nhatrosv.custom.CustomInfoWindow;
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
    public static void setMarker(final Activity activity, com.google.android.gms.maps.GoogleMap myMap) {

        MyDatabaseHelper db = new MyDatabaseHelper(activity);
        ArrayList<MotelRoom> list = db.getAllRoom();

        int count = list.size();

        for (int i = 0; i < count; i++) {

            MotelRoom room = list.get(i);
            Log.d("setForRoom",String.valueOf(room.getRoom_id()));
            ImageRoom imageRoom = db.getListImageRoomForRoom(room.getRoom_id()).get(0);
            LatlngRoom latlogRoom = db.getListLatlog_room(room.getRoom_id()).get(0);
            HouseOwner owner = db.getOwner(room.getRoom_id_owner());
            // snippet =  link + address+ sđt + room_id
            String marker_title = String.valueOf(room.getRoom_id())+StaticVariables.split+room.getRoom_type();
            String marker_snippet = imageRoom.getImage_link() +
                    StaticVariables.split + room.getRoom_address() +
                    StaticVariables.split + owner.getOwner_phone() +
                    StaticVariables.split + room.getRoom_id();

            MarkerOptions options = new MarkerOptions();

            options.snippet(marker_snippet);
            options.title(marker_title);
            options.position(new LatLng(latlogRoom.getLatlog_log(), latlogRoom.getLatlog_lat()));
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker));

            if (myMap != null) {
                myMap.addMarker(options);
                Log.d("addhouseoffline",options.getTitle()+"  "+String.valueOf(latlogRoom.getLatlog_log()));
            } else Log.d("check", "null roi");
        }

        myMap.setInfoWindowAdapter(new CustomInfoWindow(activity));

        myMap.setOnMarkerClickListener(new com.google.android.gms.maps.GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });

        myMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Log.i("centerLat",String.valueOf(cameraPosition.target.latitude));

                Log.i("centerLong",String.valueOf(cameraPosition.target.longitude));
            }
        });

        myMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent i = new Intent(activity, RoomDetailActivity.class);
                i.putExtra("room_id",marker.getTitle());
                activity.startActivity(i);
            }
        });

        myMap.setOnInfoWindowLongClickListener(new com.google.android.gms.maps.GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {

                final String[] info_marker = marker.getSnippet().split(StaticVariables.split);
                final String phone_no = info_marker[2].toString().replaceAll("-", "");

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                View v = LayoutInflater.from(activity).inflate(R.layout.dialog_marker_long_click, null);

                TextView txt_call = (TextView) v.findViewById(R.id.txt_call);
                TextView txt_inbox = (TextView) v.findViewById(R.id.txt_inbox);
                TextView txt_danhgia = (TextView) v.findViewById(R.id.txt_danhgia);

                txt_call.setSelected(true);
                txt_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // snippet =  link + address+ sđt + room_id


                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + phone_no));
                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        activity.startActivity(callIntent);
                    }
                });

                txt_inbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                        smsIntent.setType("vnd.android-dir/mms-sms");
                        smsIntent.putExtra("address", phone_no);
                        activity.startActivity(smsIntent);
                    }
                });

                builder.setView(v);
                builder.show();
            }
        });

    }



}
