package com.mnetwork.app.nhatrosv.model;

/**
 * Created by vanthanhbk on 24/09/2016.
 */
public class Group_Poster {
    private String poster_id;
    private String poster_name;

    public Group_Poster(String poster_id, String poster_name) {
        this.poster_id = poster_id;
        this.poster_name = poster_name;
    }

    public String getPoster_id() {
        return poster_id;
    }

    public void setPoster_id(String poster_id) {
        this.poster_id = poster_id;
    }

    public String getPoster_name() {
        return poster_name;
    }

    public void setPoster_name(String poster_name) {
        this.poster_name = poster_name;
    }
}
