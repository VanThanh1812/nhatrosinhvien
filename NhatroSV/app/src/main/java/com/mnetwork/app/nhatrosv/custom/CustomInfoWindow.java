package com.mnetwork.app.nhatrosv.custom;

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

        // snippet =  link + price + electric + water + acr
        String[] snippet = marker.getSnippet().split(StaticVariables.split);

        ImageView img = (ImageView) v.findViewById(R.id.marker_image);
        TextView txt_price = (TextView) v.findViewById(R.id.marker_price);
        TextView txt_price_electric = (TextView) v.findViewById(R.id.marker_price_elec);
        TextView txt_price_water = (TextView) v.findViewById(R.id.marker_price_water);
        TextView txt_acr = (TextView) v.findViewById(R.id.marker_acr);
        TextView txt_title = (TextView) v.findViewById(R.id.marker_title);

        Glide.with(activity).load(snippet[0]).centerCrop().into(img);
        if (!snippet[1].equals(null)) txt_price.setText(snippet[1]);
        if (!snippet[2].equals(null)) txt_price_electric.setText(snippet[2]);
        if (!snippet[3].equals(null)) txt_price_water.setText(snippet[3]);
        if (!snippet[4].equals(null)) txt_acr.setText(snippet[4]);
        txt_title.setText(marker.getTitle());
        return v;
    }
}
