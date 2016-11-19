package com.mnetwork.app.nhatrosv.model;

import android.content.Context;

import com.firebase.client.Firebase;
import com.mnetwork.app.nhatrosv.staticvalues.StaticVariables;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vanthanhbk on 18/11/2016.
 */

public class Feedback {

    private int feedback_id_room;
    private String feedback_id_user;
    private String feedback_message;
    private String feedback_rate;

    public Feedback(int feedback_id_room, String feedback_id_user, String feedback_message, String feedback_rate) {
        this.feedback_id_room = feedback_id_room;
        this.feedback_id_user = feedback_id_user;
        this.feedback_message = feedback_message;
        this.feedback_rate = feedback_rate;
    }

    public Feedback(String feedback_rate, String feedback_message) {
        this.feedback_rate = feedback_rate;
        this.feedback_message = feedback_message;
    }



    public void sendFeedback(final Context c){
        String ROOT="https://nha-tro-sinh-vien.firebaseio.com/";
        StaticVariables.progessDialog.show();
        Firebase mFirebase = new Firebase(ROOT);
        Map<String, Object> hashmap = new HashMap<>();
        hashmap.put("rate",feedback_rate);
        hashmap.put("message",feedback_message);
        mFirebase.child("feedback").child(String.valueOf(feedback_id_room)).child(feedback_id_user).setValue(hashmap);
    }
}
