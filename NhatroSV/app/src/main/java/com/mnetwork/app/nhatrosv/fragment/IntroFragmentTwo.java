package com.mnetwork.app.nhatrosv.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnetwork.app.nhatrosv.R;

/**
 * Created by vanthanhbk on 15/11/2016.
 */

public class IntroFragmentTwo extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intro_two,null);
        return v;
    }
}
