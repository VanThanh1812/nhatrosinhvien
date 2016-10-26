package com.mnetwork.app.nhatrosv.customadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by vanthanhbk on 19/10/2016.
 */
public class SectionPaperAdapter extends FragmentPagerAdapter{

    ArrayList<String> datalink = new ArrayList<>();

    public SectionPaperAdapter(FragmentManager fm, ArrayList<String> list_string) {
        super(fm);
        this.datalink = list_string;
    }

    @Override
    public Fragment getItem(int position) {
        return PlaceholderFragment.newInstance(position,datalink.get(position));
    }

    @Override
    public int getCount() {
        return datalink.size();
    }
}
