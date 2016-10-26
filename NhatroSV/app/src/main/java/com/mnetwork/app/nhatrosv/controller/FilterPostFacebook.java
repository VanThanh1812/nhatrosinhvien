package com.mnetwork.app.nhatrosv.controller;

import java.util.ArrayList;

/**
 * Created by vanthanhbk on 26/10/2016.
 */
public class FilterPostFacebook {
    private ArrayList<String> arr_pattern;
    private String post;

    public FilterPostFacebook(String post) {
        this.post = post;
        initFilter();
    }

    private void initFilter() {
        arr_pattern = new ArrayList<>();
        arr_pattern.add("trọ");
        arr_pattern.add("phòng");
        arr_pattern.add("cho thuê");
        arr_pattern.add("cần tìm người");
        arr_pattern.add("tìm phòng trọ");
        arr_pattern.add("giá điện");
        arr_pattern.add("giá nước");
        arr_pattern.add("giá phòng");
        arr_pattern.add("ở ghép");
        arr_pattern.add("tìm nhà");
        arr_pattern.add("triệu");

    }

    public Boolean checkPost (){

        int count = arr_pattern.size();

        for (int i=0;i<count;i++){

            if (post.indexOf(arr_pattern.get(i))>-1){
                return true;
            }

        }

        return false;
    }
}
