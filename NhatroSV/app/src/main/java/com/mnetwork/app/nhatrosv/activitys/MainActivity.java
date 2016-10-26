package com.mnetwork.app.nhatrosv.activitys;

import android.Manifest;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.github.clans.fab.FloatingActionButton;
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
import com.mnetwork.app.nhatrosv.staticvalues.StaticVariables;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private com.google.android.gms.maps.GoogleMap myMap;
    private ProgressDialog myProgress;
    ImageView imgCenter;

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

        FloatingActionButton fab_view_post_facebook = (FloatingActionButton) findViewById(R.id.menu_list);
        FloatingActionButton fab_add_house = (FloatingActionButton) findViewById(R.id.menu_add);
        FloatingActionButton fab_view_house = (FloatingActionButton) findViewById(R.id.menu_house);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        fab_add_house.setOnClickListener(new View.OnClickListener() {
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

            goLoginActivity();

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



    private void goLoginActivity() {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }

    private void onMyMapReady(com.google.android.gms.maps.GoogleMap googleMap) {

        myMap = googleMap;

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

        GPSTracker g =new GPSTracker(this);

        if (g.canGetLocation()){

            Log.d("location",String.valueOf(g.getLatitude())+"  "+String.valueOf(g.getLongitude()));

            myMap.addMarker(new MarkerOptions().position(new LatLng(g.getLatitude(),g.getLongitude())).title("Bạn đang ở đây"+StaticVariables.split).snippet(StaticVariables.split+"Lat: "+String.valueOf(g.getLatitude())+"   Long: "+String.valueOf(g.getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker)));

            myMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(g.getLatitude(),g.getLongitude())));

            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(g.getLatitude(),g.getLongitude()), 15f));

        }else {

            myMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(20.985,105.85)));

            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.985,105.85), 10f));

        }

//        MyDatabaseHelper db = new MyDatabaseHelper(this);
//        Log.d("testlatlng",String.valueOf(db.getListLatlog_room(3001).get(0).getLatlog_lat())+"   "+db.getMotelRoomById(3001).getRoom_id());

        testData();

    }
    public void testData (){

        MyDatabaseHelper db = new MyDatabaseHelper(this);
        FirebaseHouseOwner.getDataToDatabase(this,db,myMap);

        if (db.getAllRoom().size() != 0){
            MapControl.setMarker(this,myMap);
        }

        setEventMap(myMap);
    }

    private void setEventMap(GoogleMap myMap) {
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
                startActivity(i);
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


                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + phone_no));
                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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

                txt_inbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                        smsIntent.setType("vnd.android-dir/mms-sms");
                        smsIntent.putExtra("address", phone_no);
                        startActivity(smsIntent);
                    }
                });

                builder.setView(v);
                builder.show();
            }
        });
    }


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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            return true;
        }

        if (id ==R.id.action_refesh){
            MyDatabaseHelper db = new MyDatabaseHelper(this);
            db.deleteAllData();
            FirebaseHouseOwner.getDataToDatabase(this, db ,myMap);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            LoginManager.getInstance().logOut();
            goLoginActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
