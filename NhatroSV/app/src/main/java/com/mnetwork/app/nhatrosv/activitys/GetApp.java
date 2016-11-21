package com.mnetwork.app.nhatrosv.activitys;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.mnetwork.app.nhatrosv.database.MyDatabaseHelper;

/**
 * Created by vanthanhbk on 20/09/2016.
 */
public class GetApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        MyDatabaseHelper db = new MyDatabaseHelper(this);

    }
}
