package com.mnetwork.app.nhatrosv.activitys;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mnetwork.app.nhatrosv.R;
import com.mnetwork.app.nhatrosv.controler.MapControl;
import com.mnetwork.app.nhatrosv.database.MyDatabaseHelper;
import com.mnetwork.app.nhatrosv.firebase.FirebaseHouseOwner;
import com.mnetwork.app.nhatrosv.model.GPSTracker;
import com.mnetwork.app.nhatrosv.staticvalues.StaticVariables;

import org.json.JSONException;
import org.json.JSONObject;

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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Login
        loginFacebook();
        //

        /*try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.mnetwork.app.nhatrosv",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/
    }

    private void loginFacebook (){
        if (AccessToken.getCurrentAccessToken() == null){
            goLoginActivity();
        }else {
            /*
            * TODO:get info
            * */
            Log.d("accesstoken",AccessToken.getCurrentAccessToken().getToken());
            final GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    Log.d("loginactivity",response.toString());
                    //
//                    Log.d("json",object.toString());

                    try {
                        Log.d("loginactivity",object.toString());

                        if (response != null && response.getError() == null &&
                                object != null) {

                            String name = object.get("first_name")+" "+object.get("last_name");
                            String email = object.get("email").toString();

                            TextView txt_nav_name = (TextView)findViewById(R.id.txt_nav_name);
                            TextView txt_nav_email = (TextView)findViewById(R.id.txt_nav_email);
                            toolbar.setTitle("Hi, "+object.get("first_name"));

                            txt_nav_email.setText(email);
                            txt_nav_name.setText(name);

                            String url_ava = object.getJSONObject("picture")
                                    .getJSONObject("data").getString("url");
                            ImageView img = (ImageView)findViewById(R.id.img_nav_avatar);
                            Glide.with(getBaseContext()).load(url_ava).centerCrop().into(img);

                            /*String url_cover_picture= object.getJSONObject("cover").getString("source").toString();
                            LinearLayout linearLayout =(LinearLayout)findViewById(R.id.img_nav_cover);
                            try {
                                URL url = new URL(url_cover_picture);

                                Drawable d= Drawable.createFromStream(url.openStream(),"src");
                                linearLayout.setBackground(d);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/


                            Snackbar.make(getCurrentFocus(),"Xin chào "+name,Snackbar.LENGTH_LONG).setAction("Cancel", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            }).show();
                        }else {
                            Toast.makeText(getBaseContext(),"Login lỗi :((" ,Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            Bundle parameters=new Bundle();
            parameters.putString("fields","id,first_name,last_name,email,gender,birthday,location,picture.width(150).height(150),cover");
            request.setParameters(parameters);
            request.executeAsync();


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

            myMap.addMarker(new MarkerOptions().position(new LatLng(g.getLatitude(),g.getLongitude())).title("Bạn đang ở đây").snippet(StaticVariables.split+"Lat: "+String.valueOf(g.getLatitude())+"   Long: "+String.valueOf(g.getLongitude())).icon(BitmapDescriptorFactory.defaultMarker()));

            myMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(g.getLatitude(),g.getLongitude())));

            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(g.getLatitude(),g.getLongitude()), 15f));

        }else {
            myMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(20.985,105.85)));

            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.985,105.85), 10f));
        }

       testData();
    }
    public void testData (){
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        FirebaseHouseOwner.getDataToDatabase(this,db,myMap);
        if (db.getAllRoom().size() != 0){
            MapControl.setMarker(this,myMap);
        }

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
