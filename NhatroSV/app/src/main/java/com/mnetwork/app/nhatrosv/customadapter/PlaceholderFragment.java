package com.mnetwork.app.nhatrosv.customadapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mnetwork.app.nhatrosv.R;

/**
 * Created by vanthanhbk on 19/10/2016.
 */
/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private int position;
    private String url;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_IMG_URL="url";

    public PlaceholderFragment() {
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.position=args.getInt(ARG_SECTION_NUMBER);
        this.url=args.get(ARG_IMG_URL).toString();
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

    public static PlaceholderFragment newInstance(int sectionNumber, String url){
        PlaceholderFragment placeholderFragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER,sectionNumber);
        args.putString(ARG_IMG_URL,url);
        placeholderFragment.setArguments(args);
        return placeholderFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_image,container,false);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.iv_view_image);
        Glide.with(rootView.getContext()).load(url).placeholder(R.drawable.ic_menu_gallery).into(imageView);
        return rootView;
    }
}
