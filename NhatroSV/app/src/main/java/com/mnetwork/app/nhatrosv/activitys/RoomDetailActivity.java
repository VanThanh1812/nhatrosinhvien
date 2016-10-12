package com.mnetwork.app.nhatrosv.activitys;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mnetwork.app.nhatrosv.R;
import com.mnetwork.app.nhatrosv.database.MyDatabaseHelper;
import com.mnetwork.app.nhatrosv.model.HouseOwner;
import com.mnetwork.app.nhatrosv.model.ImageRoom;
import com.mnetwork.app.nhatrosv.model.MotelRoom;
import com.mnetwork.app.nhatrosv.staticvalues.StaticVariables;

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

        setInfoRoom();

    }

    private void setInfoRoom() {
        TextView tv_detai_acr = (TextView) findViewById(R.id.tv_detail_acr);
        TextView tv_detail_addr = (TextView) findViewById(R.id.tv_detail_addr);
        TextView tv_detail_electric = (TextView) findViewById(R.id.tv_detail_electric);
        TextView tv_detail_water = (TextView) findViewById(R.id.tv_detail_water);
        TextView tv_detail_price = (TextView) findViewById(R.id.tv_detail_pricehouse);
        TextView tv_detail_describe = (TextView) findViewById(R.id.tv_detail_describe);
        TextView tv_detail_style = (TextView) findViewById(R.id.tv_detail_type);

        tv_detai_acr.setText(String.valueOf(motelRoom.getRoom_acreage())+" m2");
        tv_detail_addr.setText(motelRoom.getRoom_address());
        tv_detail_electric.setText(String.valueOf(motelRoom.getRoom_electric_price())+" VNĐ/kWh");
        tv_detail_water.setText(String.valueOf(motelRoom.getRoom_water_price())+" VNĐ/m3 nước");
        tv_detail_price.setText(String.valueOf(motelRoom.getRoom_price())+" triệu VNĐ");
        tv_detail_describe.setText(motelRoom.getRoom_describe());
        tv_detail_style.setText(motelRoom.getRoom_type());
    }

    private void getModels() {
        Intent i =getIntent();
        marker_title = i.getStringExtra("room_id").split(StaticVariables.split);

        motelRoom = db.getMotelRoomById(Integer.parseInt(marker_title[0]));

        arr_imgRoom = db.getListImageRoomForRoom(motelRoom.getRoom_id());

        houseOwner = db.getOwner(motelRoom.getRoom_id_owner());
    }

    private void setBackgroundForCollapsingToolbarLayout() {

//        collapsingToolbarLayout.setBackgroundColor(getColor(R.color.cardview_dark_background));

        ImageView iv_room_first = (ImageView) findViewById(R.id.iv_room_first);

        Glide.with(this).load(arr_imgRoom.get(0).getImage_link()).centerCrop().into(iv_room_first);

//        iv_room_first.setImageDrawable(getDrawable(R.drawable.com_facebook_close));
//        try {
//            URL url = new URL(arr_imgRoom.get(0).toString());
//            iv_room_first.setImageBitmap(BitmapFactory.decodeStream(url.openConnection().getInputStream()));
//
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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
        Dialog builder = new Dialog(this);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_owner,null);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setDataforView(v,builder);
        builder.setContentView(v);
        builder.show();
    }

    private void setDataforView(final View v, final Dialog aBuilder) {
        TextView tv_dialog_addr = (TextView) v.findViewById(R.id.tv_dialog_addr);
        TextView tv_dialog_age = (TextView) v.findViewById(R.id.tv_dialog_age);
        TextView tv_dialog_phone = (TextView) v.findViewById(R.id.tv_dialog_phone);
        TextView tv_dialog_email = (TextView) v.findViewById(R.id.tv_dialog_email);
        TextView tv_dialog_title = (TextView) v.findViewById(R.id.tv_dialog_title);

        tv_dialog_addr.setText(houseOwner.getOwner_address());
        tv_dialog_age.setText(String.valueOf(houseOwner.getOwner_age()));
        tv_dialog_phone.setText(houseOwner.getOwner_phone());
        tv_dialog_title.setText(houseOwner.getOwner_name());
        tv_dialog_email.setText(houseOwner.getOwner_email());

        Button bt_dialog_call = (Button) v.findViewById(R.id.bt_dialog_call);
        Button bt_dialog_cncel = (Button) v.findViewById(R.id.bt_dialog_cancel);

        bt_dialog_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callOwner(view);
            }
        });

        bt_dialog_cncel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aBuilder.dismiss();
            }
        });
    }
}
