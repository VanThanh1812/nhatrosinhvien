package com.mnetwork.app.nhatrosv.model;

/**
 * Created by vanthanhbk on 27/08/2016.
 */
public class ImageRoom {
    private int image_id;
    private String image_link;
    private int room_id;

    public ImageRoom() {
    }

    public ImageRoom(int image_id, String image_link, int room_id) {
        this.image_id = image_id;
        this.image_link = image_link;
        this.room_id = room_id;
    }

    @Override
    public String toString() {
        return "ImageRoom{" + image_id +
                ",'" + image_link + '\'' +
                "," + room_id +
                '}';
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }
}
