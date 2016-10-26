package com.mnetwork.app.nhatrosv.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mnetwork.app.nhatrosv.R;
import com.mnetwork.app.nhatrosv.customadapter.SectionPaperAdapter;

import java.util.ArrayList;

public class ViewImageActivity extends AppCompatActivity {

    private SectionPaperAdapter mSectionPaperAdapter;
    private ViewPager mViewPager;
    int position=-1;
    ArrayList<String> data_link = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ảnh");

        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        data_link = intent.getStringArrayListExtra("listlink");

        mSectionPaperAdapter = new SectionPaperAdapter(getSupportFragmentManager(),data_link);
        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.setAdapter(mSectionPaperAdapter);
        mViewPager.setCurrentItem(position);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }
}
