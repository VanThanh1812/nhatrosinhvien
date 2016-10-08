package com.mnetwork.app.nhatrosv.controller;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.mnetwork.app.nhatrosv.model.GroupPost;
import com.mnetwork.app.nhatrosv.model.Group_Poster;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by vanthanhbk on 24/09/2016.
 */
public class ParseJsonGroupPost {
    public static final String ID_GROUP = "774199205937037";
    private AccessToken accessToken;

    public ParseJsonGroupPost(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public void getJson (){
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
                            getListGroupPost(response.getJSONObject());

                        }
                    }

                    @Override
                    public void onProgress(long current, long max) {

                    }
                }).executeAsync();
    }

    public GroupPost getGroupPost (JSONObject object) {
        GroupPost post = new GroupPost();
        if (object == null ) return null;
        /*
        * TODO: parse post
        * {
      "from": {
        "name": "An Nguyệt",
        "id": "653017681485298"
      },
      "message": "Mình bán lại cái phát wife này nha. Bạn nào cần thì inbox mình ạ.",
      "picture": "https://scontent.xx.fbcdn.net/v/t1.0-0/s130x130/14440631_1028654350588294_1116831511778474923_n.jpg?oh=6e8599b387a27d4231d062f50b6e6fe6&oe=5881BA8F",
      "full_picture": "https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/14440631_1028654350588294_1116831511778474923_n.jpg?oh=b6b7e53bd9a40628ed7963fab072d6e4&oe=587F6C9A",
      "object_id": "1028654350588294",
      "id": "774199205937037_1280739665282986"
    }
        * */

        post.setPost_id(object.optString("id"));
        post.setPost_poster(new Group_Poster(object.optJSONObject("from").optString("id"),object.optJSONObject("from").optString("name")));
        post.setPost_message(object.optString("message"));
        post.setPost_updated_time(object.optString("updated_time"));
        post.setPost_picture(object.optString("picture"));
        post.setPost_full_picture(object.optString("full_picture"));
        post.setPost_comments(object.optJSONObject("comments"));

        Log.d("post", post.toString());
        return post;
    }

    public ArrayList<GroupPost> getListGroupPost (JSONObject object){
        ArrayList<GroupPost> arr_post = new ArrayList<>();
        JSONArray arr_json_post = object.optJSONArray("data");

        int size_arr = arr_json_post.length();

        if (size_arr == 0) return null;

        for (int i=0;i<size_arr;i++){
            arr_post.add(getGroupPost(arr_json_post.optJSONObject(i)));
        }

        return arr_post;

    }
}
