package com.mnetwork.app.nhatrosv.activitys;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.mnetwork.app.nhatrosv.R;
import com.mnetwork.app.nhatrosv.customadapter.ListViewHouseAdapter;
import com.mnetwork.app.nhatrosv.database.MyDatabaseHelper;

public class ShowListHouse extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_house);
        //lay action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //lay du lieu
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        Log.d("number",String.valueOf(db.getAllRoom().size()));
        ListViewHouseAdapter adapter = new ListViewHouseAdapter(this,db.getAllRoom());
        //tao list
        listView=(ListView)findViewById(R.id.showlisthuose);

        listView.setAdapter(adapter);
        //db.getMotelRoomById(.getRoom_price());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
