package com.mnetwork.app.nhatrosv.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mnetwork.app.nhatrosv.R;
import com.mnetwork.app.nhatrosv.database.MyDatabaseHelper;
import com.mnetwork.app.nhatrosv.model.HouseOwner;
import com.mnetwork.app.nhatrosv.model.ImageRoom;
import com.mnetwork.app.nhatrosv.model.MotelRoom;
import com.mnetwork.app.nhatrosv.staticvalues.StaticVariables;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RoomDetailActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private String[] marker_title;
    private MotelRoom motelRoom;
    private ArrayList<ImageRoom> arr_imgRoom;
    private HouseOwner houseOwner;
    MyDatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Phòng trọ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + houseOwner.getOwner_phone()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (ActivityCompat.checkSelfPermission(view.getContext() , Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });

        db=new MyDatabaseHelper(this);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsed);

        getModels();

        setTitleLayout();

        setBackgroundForCollapsingToolbarLayout();

    }

    private void getModels() {
        Intent i =getIntent();
        marker_title = i.getStringExtra("room_id").split(StaticVariables.split);

        motelRoom = db.getMotelRoomById(Integer.parseInt(marker_title[0]));

        arr_imgRoom = db.getListImageRoomForRoom(motelRoom.getRoom_id());

        houseOwner = db.getOwner(motelRoom.getRoom_id_owner());
    }

    private void setBackgroundForCollapsingToolbarLayout() {
        ImageView iv_room_first = (ImageView) findViewById(R.id.iv_room_first);
        //Glide.with(this).load(arr_imgRoom.get(0)).centerCrop().into(iv_room_first);
//        iv_room_first.setImageDrawable(getDrawable(R.drawable.com_facebook_close));
        try {
            URL url = new URL(arr_imgRoom.get(0).toString());
            iv_room_first.setImageBitmap(BitmapFactory.decodeStream(url.openConnection().getInputStream()));


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setTitleLayout() {
        TextView tv_detail_title = (TextView) findViewById(R.id.tv_detail_title);
        TextView tv_detail_title_addr = (TextView) findViewById(R.id.tv_detail_title_addr);

        String title_layout = String.valueOf(motelRoom.getRoom_price()) + " triệu VNĐ/ "+String.valueOf(motelRoom.getRoom_acreage())+" m2";
        tv_detail_title.setText(title_layout);
        tv_detail_title_addr.setText(motelRoom.getRoom_address());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            this.finish();
        }
        return true;
    }

    // event onclick 4 linearlayout
    public void callOwner(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + houseOwner.getOwner_phone()));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }

    public void sendMessage(View view) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", houseOwner.getOwner_phone());
        startActivity(smsIntent);
    }

    public void showFeedback(View view) {
    }

    public void showInfoOwner(View view) {
    }
}
