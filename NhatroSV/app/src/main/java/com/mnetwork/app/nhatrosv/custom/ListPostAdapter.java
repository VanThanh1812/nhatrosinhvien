package com.mnetwork.app.nhatrosv.custom;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.mnetwork.app.nhatrosv.R;
import com.mnetwork.app.nhatrosv.model.GroupPost;
import com.mnetwork.app.nhatrosv.model.Group_Poster;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by vanthanhbk on 25/09/2016.
 */
public class ListPostAdapter extends ArrayAdapter<GroupPost> {
    private Activity context;
    private ArrayList<GroupPost> arr_groupPosts;

    public ListPostAdapter(Activity context, ArrayList<GroupPost> objects) {
        super(context, 0, objects);
        this.context=context;
        this.arr_groupPosts=objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_list_post, null);

        final ImageView img_listhouse_avatar = (ImageView) convertView.findViewById(R.id.img_listhouse_avatar);
        ImageView img_listhouse_picture = (ImageView) convertView.findViewById(R.id.img_listhouse_picture);
        final TextView txt_listhouse_name = (TextView) convertView.findViewById(R.id.txt_listhouse_name);
        TextView txt_listhouse_message = (TextView) convertView.findViewById(R.id.txt_listhouse_message);
        TextView txt_listhouse_updatetime = (TextView) convertView.findViewById(R.id.txt_listhouse_updatetime);
        Button btn_listpost_comments = (Button) convertView.findViewById(R.id.btn_listpost_comments);

        Glide.with(context).load(arr_groupPosts.get(position).getPost_full_picture()).centerCrop().into(img_listhouse_picture);
        txt_listhouse_name.setText(arr_groupPosts.get(position).getPost_poster().getPoster_name());
        txt_listhouse_message.setText(arr_groupPosts.get(position).getPost_message());
        txt_listhouse_updatetime.setText(arr_groupPosts.get(position).getPost_updated_time());

        Bundle params = new Bundle();
        params.putString("fields", "link,picture.type(normal)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), arr_groupPosts.get(position).getPost_poster().getPoster_id(), params, HttpMethod.GET,
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
                }).executeAsync();

        btn_listpost_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPopupComments(arr_groupPosts.get(position));
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
    }

    private void viewPopupComments (GroupPost post) {

        AlertDialog.Builder aBuilder = new AlertDialog.Builder(context,R.style.AppCompatAlertDialogStyle);

        aBuilder.setTitle("Bình luận");

        View v = LayoutInflater.from(context).inflate(R.layout.dialog_comments,null);
        ListView lst_comment = (ListView) v.findViewById(R.id.lst_comments);

        if (post.getPost_comments() == null) {
            Toast.makeText(context, "Không có bình luận nào cả !", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONArray arr_comment= post.getPost_comments().optJSONArray("data");
        ArrayList<GroupPost> list_comment = new ArrayList<>();

        int count = arr_comment.length();
        if (count == 0) return;

        for (int i = 0; i<count;i++){

            JSONObject comment_json = (JSONObject) arr_comment.opt(i);
            GroupPost comment = new GroupPost();
            comment.setPost_message(comment_json.optString("message"));
            comment.setPost_poster(new Group_Poster(comment_json.optJSONObject("from").optString("id"),comment_json.optJSONObject("from").optString("name")));
            comment.setPost_updated_time(comment_json.optString("created_time"));
            list_comment.add(comment);

        }

        CommentsAdapter comment_adapter = new CommentsAdapter(context,list_comment);
        lst_comment.setAdapter(comment_adapter);

        aBuilder.setView(v);
        aBuilder.create();
        aBuilder.show();

    }



}
