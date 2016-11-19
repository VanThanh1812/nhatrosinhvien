    package com.mnetwork.app.nhatrosv.activity;

    import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
    import com.firebase.client.Firebase;
    import com.mnetwork.app.nhatrosv.R;
import com.mnetwork.app.nhatrosv.customadapter.ListImageRecyclerAdapter;
import com.mnetwork.app.nhatrosv.customadapter.RecyclerItemClickListener;
import com.mnetwork.app.nhatrosv.database.MyDatabaseHelper;
import com.mnetwork.app.nhatrosv.model.Feedback;
import com.mnetwork.app.nhatrosv.model.GPSTracker;
import com.mnetwork.app.nhatrosv.model.HouseOwner;
import com.mnetwork.app.nhatrosv.model.ImageRoom;
import com.mnetwork.app.nhatrosv.model.LatlngRoom;
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

        Firebase.setAndroidContext(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPSTracker gps =new GPSTracker(view.getContext());
                if (gps.canGetLocation()){
                    LatlngRoom latlngRoom = db.getListLatlog_room(motelRoom.getRoom_id()).get(0);
                    Location latLng = gps.getLocation();
                    String uri ="geo:"+String.valueOf(latLng.getLongitude())+","+String.valueOf(latLng.getLatitude())+"?q="+String.valueOf(latlngRoom.getLatlog_log())+","+String.valueOf(latlngRoom.getLatlog_lat())+" (";
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(uri + motelRoom.getRoom_address() + ")"));
                    startActivity(intent);
                }else {
//                    Dialog builder = new Dialog(view.getContext());
//                    View v = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_message,null);
//                    TextView txt = (TextView) v.findViewById(R.id.tv_dialog_message);
//                    txt.setText("Bạn cần bật GPS để chỉ đường từ chỗ của bạn");
//                    builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    builder.setContentView(v);
//                    //builder.show();
                    gps.showSettingsAlert();

                }
            }
        });

        db=new MyDatabaseHelper(this);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsed);

        getModels();

        setTitleLayout();

        setBackgroundForCollapsingToolbarLayout();

        setInfoRoom();

        setListImage();

    }

    private void setListImage() {

        final ArrayList<String> arr_link = new ArrayList<>();

        for (int i=0;i<arr_imgRoom.size();i++){

            arr_link.add(arr_imgRoom.get(i).getImage_link());

        }

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcv_listimage);

        recyclerView.setLayoutManager(manager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(new ListImageRecyclerAdapter(this,arr_imgRoom));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(RoomDetailActivity.this, ViewImageActivity.class);

                intent.putStringArrayListExtra("listlink",arr_link);

                intent.putExtra("position", position);

                startActivity(intent);

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

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

        ImageView iv_room_first = (ImageView) findViewById(R.id.iv_room_first);

        Glide.with(this).load(arr_imgRoom.get(0).getImage_link()).centerCrop().into(iv_room_first);

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
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + houseOwner.getOwner_phone()));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        startActivity(callIntent);
    }

    public void sendMessage(View view) {

        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", houseOwner.getOwner_phone());
        startActivity(smsIntent);

    }

    public void showFeedback(View view) {

        Dialog builder = new Dialog(this);

        View v = LayoutInflater.from(this).inflate(R.layout.dialog_feedback,null);

        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);

        RatingBar ratingBar = (RatingBar) v.findViewById(R.id.rate_feedback);

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();

        stars.getDrawable(2).setColorFilter(Color.parseColor("#03a9f4"), PorterDuff.Mode.SRC_ATOP);

        builder.setContentView(v);

        setEvent(v,builder);

        builder.show();

    }

        private void setEvent(final View v, final Dialog builder) {
            Button bt_feedback_cancel = (Button) v.findViewById(R.id.bt_feedback_cancel);
            Button bt_feedback_send = (Button) v.findViewById(R.id.bt_feedback_send);

            bt_feedback_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder.dismiss();
                }
            });

            bt_feedback_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    EditText et_feedback_message = (EditText) v.findViewById(R.id.et_feedback_message);
                    RatingBar rate_feedback = (RatingBar) v.findViewById(R.id.rate_feedback);

                    Feedback feedback = new Feedback(motelRoom.getRoom_id(), AccessToken.getCurrentAccessToken().getUserId(),String.valueOf(rate_feedback.getRating()),et_feedback_message.getText().toString());
                    feedback.sendFeedback(v.getContext());
                    builder.dismiss();

                }
            });
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
