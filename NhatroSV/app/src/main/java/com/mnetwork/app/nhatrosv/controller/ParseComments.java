package com.mnetwork.app.nhatrosv.controller;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONObject;

/**
 * Created by vanthanhbk on 28/09/2016.
 */
public class ParseComments {
    private String id_post;

    public ParseComments(String id_post) {
        this.id_post = id_post;
    }

    public void getJson (){
        Bundle para2=new Bundle();
        para2.putString("fields","id,from,message,updated_time,picture,full_picture");
        new GraphRequest(AccessToken.getCurrentAccessToken(),"774199205937037/feed",
                para2,
                HttpMethod.GET,
                new GraphRequest.OnProgressCallback(){

                    @Override
                    public void onCompleted(GraphResponse response) {
                        JSONObject json =response.getJSONObject();
                        if (response.getJSONObject()!=(null)){

                            Log.d("groupf",json.optString("picture"));
                            //getListGroupPost(response.getJSONObject());

                        }
                    }

                    @Override
                    public void onProgress(long current, long max) {

                    }
                }).executeAsync();
    }
}
