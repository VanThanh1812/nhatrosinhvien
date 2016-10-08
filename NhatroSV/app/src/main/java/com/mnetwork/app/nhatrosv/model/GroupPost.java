package com.mnetwork.app.nhatrosv.model;

import org.json.JSONObject;

/**
 * Created by vanthanhbk on 24/09/2016.
 */
public class GroupPost {
    private String post_id;
    private Group_Poster post_poster;
    private String post_message;
    private String post_updated_time;
    private String post_picture;
    private String post_full_picture;
    private JSONObject post_comments;

    public GroupPost() {
    }

    public GroupPost(String post_id, Group_Poster post_poster, String post_message, String post_updated_time, String post_picture, String post_full_picture,JSONObject post_comments) {
        this.post_id = post_id;
        this.post_poster = post_poster;
        this.post_message = post_message;
        this.post_updated_time = post_updated_time;
        this.post_picture = post_picture;
        this.post_full_picture = post_full_picture;
        this.post_comments = post_comments;
    }

    public JSONObject getPost_comments() {
        return post_comments;
    }

    public void setPost_comments(JSONObject post_comments) {
        this.post_comments = post_comments;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_message() {
        return post_message;
    }

    public void setPost_message(String post_message) {
        this.post_message = post_message;
    }

    public String getPost_updated_time() {
        return post_updated_time;
    }

    public void setPost_updated_time(String post_updated_time) {
        this.post_updated_time = post_updated_time;
    }

    public String getPost_full_picture() {
        return post_full_picture;
    }

    public void setPost_full_picture(String post_full_picture) {
        this.post_full_picture = post_full_picture;
    }

    public String getPost_picture() {
        return post_picture;
    }

    public void setPost_picture(String post_picture) {
        this.post_picture = post_picture;
    }

    public Group_Poster getPost_poster() {
        return post_poster;
    }

    public void setPost_poster(Group_Poster post_poster) {
        this.post_poster = post_poster;
    }

    @Override
    public String toString() {
        return "GroupPost{" +
                "post_comments=" + post_comments +
                ", post_id='" + post_id + '\'' +
                ", post_poster=" + post_poster +
                ", post_message='" + post_message + '\'' +
                ", post_updated_time='" + post_updated_time + '\'' +
                ", post_picture='" + post_picture + '\'' +
                ", post_full_picture='" + post_full_picture + '\'' +
                '}';
    }
}
