package com.mnetwork.app.nhatrosv.activitys;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.mnetwork.app.nhatrosv.R;
import com.mnetwork.app.nhatrosv.controller.ParseJsonGroupPost;
import com.mnetwork.app.nhatrosv.custom.ListPostAdapter;
import com.mnetwork.app.nhatrosv.model.GroupPost;

import org.json.JSONObject;

import java.util.ArrayList;

public class ListHouseActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ListPostAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private ListView listView;
    private ArrayList<GroupPost> arrayList = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_house);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bài đăng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        listView = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        //adapter = new ListPostAdapter(this,arrayList);
        getJson(this);

        refreshLayout.setOnRefreshListener(this);
        /*
          * Showing Swipe Refresh animation on activity create
          * As animation won't start on onCreate, post runnable is used
        */
        refreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        refreshLayout.setRefreshing(true);

                                        fetchMovies(getBaseContext());
                                    }
                                }
        );

    }

    private void fetchMovies(final Context context) {

        refreshLayout.setRefreshing(true);

        Bundle para2=new Bundle();
        para2.putString("fields","id,from,message,updated_time,picture,full_picture,comments");
        new GraphRequest(AccessToken.getCurrentAccessToken(),"774199205937037/feed",
                para2,
                HttpMethod.GET,
                new GraphRequest.OnProgressCallback(){

                    @Override
                    public void onCompleted(GraphResponse response) {
                        JSONObject json =response.getJSONObject();
                        if (response.getJSONObject()!=(null)){

                            Log.d("groupf",json.optString("picture"));
                            ParseJsonGroupPost parseJsonGroupPost = new ParseJsonGroupPost(AccessToken.getCurrentAccessToken());
                            arrayList = parseJsonGroupPost.getListGroupPost(response.getJSONObject());
                            adapter = new ListPostAdapter(ListHouseActivity.this, arrayList);
                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            refreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onProgress(long current, long max) {

                    }
                }).executeAsync();
    }

    private void getJson (final Activity c){
        Bundle para2=new Bundle();
        para2.putString("fields","id,from,message,updated_time,picture,full_picture,comments");
        new GraphRequest(AccessToken.getCurrentAccessToken(),"774199205937037/feed",
                para2,
                HttpMethod.GET,
                new GraphRequest.OnProgressCallback(){

                    @Override
                    public void onCompleted(GraphResponse response) {
                        JSONObject json =response.getJSONObject();
                        if (response.getJSONObject()!=(null)){

                            ParseJsonGroupPost parseJsonGroupPost = new ParseJsonGroupPost(AccessToken.getCurrentAccessToken());
                            arrayList = parseJsonGroupPost.getListGroupPost(response.getJSONObject());
                            adapter = new ListPostAdapter(c, arrayList);
                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onProgress(long current, long max) {

                    }
                }).executeAsync();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
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


    @Override
    public void onRefresh() {
        fetchMovies(getBaseContext());
    }
}
