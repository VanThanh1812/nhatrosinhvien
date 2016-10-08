package com.mnetwork.app.nhatrosv.custom;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.mnetwork.app.nhatrosv.R;
import com.mnetwork.app.nhatrosv.model.GroupPost;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by vanthanhbk on 28/09/2016.
 */
public class CommentsAdapter extends ArrayAdapter<GroupPost> {

    private Activity context;
    private ArrayList<GroupPost> arr_comment;
    public CommentsAdapter(Activity context, ArrayList<GroupPost> objects) {
        super(context, 0, objects);
        this.context = context;
        this.arr_comment = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_list_post, null);

        final ImageView img_listhouse_avatar = (ImageView) convertView.findViewById(R.id.img_listhouse_avatar);
        //ImageView img_listhouse_picture = (ImageView) convertView.findViewById(R.id.img_listhouse_picture);
        final TextView txt_listhouse_name = (TextView) convertView.findViewById(R.id.txt_listhouse_name);
        TextView txt_listhouse_message = (TextView) convertView.findViewById(R.id.txt_listhouse_message);
        TextView txt_listhouse_updatetime = (TextView) convertView.findViewById(R.id.txt_listhouse_updatetime);
        Button btn_listpost_comments = (Button) convertView.findViewById(R.id.btn_listpost_comments);

        //Glide.with(context).load(arr_comment.get(position).getPost_full_picture()).centerCrop().into(img_listhouse_picture);
        txt_listhouse_name.setText(arr_comment.get(position).getPost_poster().getPoster_name());
        txt_listhouse_message.setText(arr_comment.get(position).getPost_message());
        txt_listhouse_updatetime.setText(arr_comment.get(position).getPost_updated_time());

        Bundle params = new Bundle();
        params.putString("fields", "link,picture.type(normal)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), arr_comment.get(position).getPost_poster().getPoster_id(), params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            try {
                                final JSONObject data = response.getJSONObject();
                                if (data.has("picture") & (data.has("link"))) {
                                    String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                    // set profile image to imageview using Picasso or Native methods
                                    Glide.with(context).load(profilePicUrl).centerCrop().into(img_listhouse_avatar);

                                    txt_listhouse_name.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String link = data.optString("link");

                                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                                            context.startActivity(i);

                                        }
                                    });
                                    Log.d("picture",profilePicUrl);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });

        btn_listpost_comments.setVisibility(View.GONE);


        return convertView;
    }

}
