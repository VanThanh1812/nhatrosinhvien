package com.mnetwork.app.nhatrosv.customadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.mnetwork.app.nhatrosv.R;
import com.mnetwork.app.nhatrosv.staticvalues.StaticVariables;

/**
 * Created by vanthanhbk on 31/08/2016.
 */
public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    private Activity activity;

    public CustomInfoWindow(Activity activity) {
        this.activity=activity;

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        View v = LayoutInflater.from(activity).inflate(R.layout.custom_maker,null);

        String[] snippet = marker.getSnippet().split(StaticVariables.split);

        String[] title = marker.getTitle().split(StaticVariables.split);

        ImageView img = (ImageView) v.findViewById(R.id.marker_image);

        TextView txt_address = (TextView) v.findViewById(R.id.marker_address);

        TextView txt_title = (TextView) v.findViewById(R.id.marker_title);

        TextView txt_price = (TextView) v.findViewById(R.id.marker_price);

        Glide.with(activity).load(snippet[0]).centerCrop().into(img);

        if (!snippet[1].equals(null)) txt_address.setText(snippet[1]);

        if (!snippet[4].equals(null)) txt_price.setText(snippet[4]+" triệu");

        txt_title.setText(title[1]);

        txt_address.setSelected(true);

        return v;
    }
}
