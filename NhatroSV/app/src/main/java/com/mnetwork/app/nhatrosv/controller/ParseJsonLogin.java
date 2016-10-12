package com.mnetwork.app.nhatrosv.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.mnetwork.app.nhatrosv.R;
import com.mnetwork.app.nhatrosv.activitys.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vanthanhbk on 25/09/2016.
 */
public class ParseJsonLogin {
    private Activity activity;
    private AccessToken accessToken;

    public ParseJsonLogin(AccessToken accessToken, Activity activity) {
        this.accessToken = accessToken;
        this.activity = activity;
    }

    public void setInfoUser (){
        final GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("loginactivity",response.toString());
                        //
//                    Log.d("json",object.toString());

                        try {

                            if (response != null && response.getError() == null &&
                                    object != null) {

                                String name = object.opt("first_name")+" "+object.opt("last_name");
                                String email = object.opt("email").toString();

                                TextView txt_nav_name = (TextView)activity.findViewById(R.id.txt_nav_name);
                                TextView txt_nav_email = (TextView)activity.findViewById(R.id.txt_nav_email);
                                activity.setTitle("Hi, "+object.get("first_name"));

                                if (!email.equals(null)){
                                    txt_nav_email.setText(email);
                                }else txt_nav_email.setVisibility(View.GONE);
                                txt_nav_name.setText(name);

                                String url_ava = object.optJSONObject("picture")
                                        .optJSONObject("data").optString("url");
                                ImageView img = (ImageView)activity.findViewById(R.id.img_nav_avatar);
                                Glide.with(activity).load(url_ava).centerCrop().into(img);

                                Snackbar.make(activity.getCurrentFocus(),"Xin chào "+name,Snackbar.LENGTH_LONG).setAction("Cancel", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                }).show();
                            }else {
                                activity.setTitle("Hi, guy");
                                Toast.makeText(activity, "Error connect internet", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(activity, LoginActivity.class);
                                activity.startActivity(i);
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

    }



}
