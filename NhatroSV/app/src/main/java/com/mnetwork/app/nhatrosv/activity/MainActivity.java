package com.mnetwork.app.nhatrosv.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mnetwork.app.nhatrosv.R;
import com.mnetwork.app.nhatrosv.controller.MapControl;
import com.mnetwork.app.nhatrosv.controller.ParseJsonGroupPost;
import com.mnetwork.app.nhatrosv.controller.ParseJsonLogin;
import com.mnetwork.app.nhatrosv.customadapter.CustomInfoWindow;
import com.mnetwork.app.nhatrosv.database.MyDatabaseHelper;
import com.mnetwork.app.nhatrosv.firebase.FirebaseHouseOwner;
import com.mnetwork.app.nhatrosv.model.GPSTracker;
import com.mnetwork.app.nhatrosv.model.MotelRoom;
import com.mnetwork.app.nhatrosv.staticvalues.StaticVariables;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private com.google.android.gms.maps.GoogleMap myMap;
    private ProgressDialog myProgress;
    FloatingActionMenu fab_menu;

    private static final String MYTAG = "MYTAG";
    Toolbar toolbar;
    // Mã yêu cầu uhỏi người dùng cho phép xem vị trí hiện tại của họ (***).
    // Giá trị mã 8bit (value < 256).
    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StaticVariables.progessDialog = new ProgressDialog(this);
        //request permission android sdk>=23
//        ActivityCompat.requestPermissions(MainActivity.this,
//                new String[]{Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
//                1);

        FloatingActionButton fab_view_post_facebook = (FloatingActionButton) findViewById(R.id.menu_list);
        FloatingActionButton fab_view_house = (FloatingActionButton) findViewById(R.id.menu_house);
        fab_menu = (FloatingActionMenu) findViewById(R.id.menu_menu);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_house);


        loginFacebook();

        fab_view_post_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),ListPostFacebookActivity.class);
                startActivity(intent);

            }
        });

        fab_view_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(getCurrentFocus(),"Nothing",Snackbar.LENGTH_SHORT).setAction("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // nothing
                    }
                }).show();
            }
        });

    }

    private void loginFacebook (){

        if (AccessToken.getCurrentAccessToken() == null){

            goIntroActivity();

        }else {
            /*
            * TODO:get info
            * */
            Log.d("accesstoken",AccessToken.getCurrentAccessToken().getToken());
            ParseJsonLogin login = new ParseJsonLogin(AccessToken.getCurrentAccessToken(),this);
            login.setInfoUser();

            /*
            * TODO: get Maps
            * */
            myProgress = new ProgressDialog(this);
            myProgress.show();
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
                    onMyMapReady(googleMap);
                }
            });

            /*
            * TODO: get post
            * */

            ParseJsonGroupPost parseJsonGroupPost = new ParseJsonGroupPost(AccessToken.getCurrentAccessToken());
            parseJsonGroupPost.getJson();

        }
    }



    private void goIntroActivity() {
        Intent i = new Intent(this,IntroActivity.class);
        startActivity(i);
    }

    private void onMyMapReady(com.google.android.gms.maps.GoogleMap googleMap) {

        myMap = googleMap;

        showFilter();

        myMap.setBuildingsEnabled(true);

        myMap.setOnMapLoadedCallback(new com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                myProgress.dismiss();
            }
        });

        myMap.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL);

        myMap.getUiSettings().setZoomControlsEnabled(false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        myMap.setMyLocationEnabled(true);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(20.985,105.85))
                .zoom(12f)
                .bearing(11)
                .tilt(30)    // Sets the tilt of the camera to 30 degrees
                .build();    // Creates a CameraPosition from the builder

        myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        GPSTracker g =new GPSTracker(this);

        if (g.canGetLocation()){

            Log.d("location",String.valueOf(g.getLatitude())+"  "+String.valueOf(g.getLongitude()));

            myMap.addMarker(new MarkerOptions().position(new LatLng(g.getLatitude(),g.getLongitude())).title("Bạn đang ở đây"+StaticVariables.split).snippet(StaticVariables.split+"Lat: "+String.valueOf(g.getLatitude())+"   Long: "+String.valueOf(g.getLongitude())).icon(BitmapDescriptorFactory.defaultMarker()));

            myMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(g.getLatitude(),g.getLongitude())));

            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(g.getLatitude(),g.getLongitude()), 15f));

        }else {

            myMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(20.985,105.85)));

            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.985,105.85), 12f));

        }

//        MyDatabaseHelper db = new MyDatabaseHelper(this);
//        Log.d("testlatlng",String.valueOf(db.getListLatlog_room(3001).get(0).getLatlog_lat())+"   "+db.getMotelRoomById(3001).getRoom_id());

        getDatabase();

    }
    public void getDatabase(){

        MyDatabaseHelper db = new MyDatabaseHelper(this);

        FirebaseHouseOwner.getDataToDatabase(this,db,myMap);

        if (db.getAllRoom().size() != 0){
            MapControl.setMarker(this,myMap,db.getAllRoom());
        }

        setEventMap(myMap);
    }


    private void setEventMap(final GoogleMap myMap) {
//
//        ClusterManager<LatlngRoom> mClusterManager = new ClusterManager<>(this, myMap);
//
//        myMap.setOnCameraIdleListener(mClusterManager);
//
//        myMap.setOnMarkerClickListener(mClusterManager);
//
//        addItemCluster(mClusterManager);
        myMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if (fab_menu.isOpened()){
                    fab_menu.close(true);
                }
            }
        });

        myMap.setInfoWindowAdapter(new CustomInfoWindow(this));

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
                Intent i = new Intent(MainActivity.this, RoomDetailActivity.class);
                i.putExtra("room_id",marker.getTitle());
                if (fab_menu.isOpened()){
                    fab_menu.close(true);
                }
                startActivity(i);

            }
        });

        myMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(marker.getPosition())
                        .zoom(12f)
                        .bearing(0)
                        .tilt(30)    // Sets the tilt of the camera to 30 degrees
                        .build();    // Creates a CameraPosition from the builder

                myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                marker.showInfoWindow();

                if (fab_menu.isOpened()){
                    fab_menu.close(true);
                }

                return true;
            }
        });

        myMap.setOnInfoWindowLongClickListener(new com.google.android.gms.maps.GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {

                final String[] info_marker = marker.getSnippet().split(StaticVariables.split);
                final String phone_no = info_marker[2].toString().replaceAll("-", "");

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_marker_long_click, null);

                TextView txt_call = (TextView) v.findViewById(R.id.txt_call);
                TextView txt_inbox = (TextView) v.findViewById(R.id.txt_inbox);
                TextView txt_danhgia = (TextView) v.findViewById(R.id.txt_danhgia);

                txt_call.setSelected(true);
                txt_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // snippet =  link + address+ sđt + room_id


                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + phone_no));
                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(callIntent);
                    }
                });

                txt_inbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                        smsIntent.setType("vnd.android-dir/mms-sms");
                        smsIntent.putExtra("address", phone_no);
                        startActivity(smsIntent);
                    }
                });

                txt_danhgia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog builder = new Dialog(getApplicationContext());
                        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_feedback,null);
                        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        builder.setContentView(v);
                        builder.show();
                    }
                });

                builder.setView(v);
                builder.show();
            }
        });
    }

   /* private void addItemCluster(ClusterManager<LatlngRoom> manager) {
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        ArrayList<MotelRoom> roomArrayList = db.getAllRoom();
        int number_room = roomArrayList.size();

        for (int i=0;i<number_room;i++){
            LatlngRoom lnRoom =db.getListLatlog_room(roomArrayList.get(i).getRoom_id()).get(0);
            manager.addItem(lnRoom);
        }
    }*/


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            showFilter();
            return true;
        }

        if (id ==R.id.action_refesh){
            StaticVariables.progessDialog = new ProgressDialog(this);
            StaticVariables.progessDialog.show();
            MyDatabaseHelper db = new MyDatabaseHelper(this);
            myMap.clear();
            db.deleteAllData();
            FirebaseHouseOwner.getDataToDatabase(this, db ,myMap);
            return true;
        }

        if (id == R.id.action_add){
            final Dialog builder = new Dialog(this);
            final View v = LayoutInflater.from(this).inflate(R.layout.dialog_message,null);

            Button bt_dialog_access = (Button) v.findViewById(R.id.bt_dialog_access);

            bt_dialog_access.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText et_dialog_key = (EditText) v.findViewById(R.id.et_dialog_keyaccess);
                    if (et_dialog_key.getText().toString().trim().equals("believe_in_yourself")){
                        Snackbar.make(v.getRootView(),"Success, có điều module chưa được xây dựng",Snackbar.LENGTH_SHORT).show();
                    }else{
                        Snackbar.make(v.getRootView(),"Fail, bạn không được cung cấp key như vậy",Snackbar.LENGTH_SHORT).show();
                    }
                }
            });

            Button bt_dialog_cncel = (Button) v.findViewById(R.id.bt_dialog_cancel);
            bt_dialog_cncel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder.dismiss();
                }
            });

            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            builder.setContentView(v);
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFilter(){
        final Dialog builder = new Dialog(this);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_filter,null);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(v);

        final RangeSeekBar seekbar = (RangeSeekBar) v.findViewById(R.id.pricebar_with_label);
        seekbar.setColorFilter(R.color.colorBlue);
        seekbar.setRangeValues(0.8,10.0);
        final TextView tv_price = (TextView) v.findViewById(R.id.tv_detail_price);

        seekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                tv_price.setText(String.valueOf(minValue)+" đến "+String.valueOf(maxValue)+" triệu");
            }
        });

        Button bt_dialog_filter = (Button) v.findViewById(R.id.bt_dialog_filter);
        Button bt_dialog_cncel = (Button) v.findViewById(R.id.bt_dialog_cancel);

        bt_dialog_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper db = new MyDatabaseHelper(getApplication());
                ArrayList<MotelRoom> arr_list = db.getListRoomByPrice(String.valueOf(seekbar.getSelectedMinValue()),String.valueOf(seekbar.getSelectedMaxValue()));
                MapControl.setMarker(getApplicationContext(),myMap,arr_list);
                builder.dismiss();
                Toast.makeText(getApplicationContext(), "Done, có tất cả "+String.valueOf(arr_list.size())+" phòng", Toast.LENGTH_SHORT).show();
            }
        });

        bt_dialog_cncel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });

        builder.show();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_house) {
            // Handle the camera action
        } else if (id == R.id.nav_location_fr) {

        } else if (id == R.id.nav_share) {
            setFragment();
        } else if (id == R.id.nav_logout) {
            LoginManager.getInstance().logOut();
            goIntroActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment() {
        Intent i = new Intent(this,InfomationActivity.class);
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("a","aaaa");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
