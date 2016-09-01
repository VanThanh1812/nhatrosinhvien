package com.mnetwork.app.nhatrosv.model;

/**
 * Created by vanthanhbk on 26/08/2016.
 */
public class MotelRoom {
    private int room_id;
    private String room_address;
    private String room_type;
    private double room_price;
    private double room_electric_price;
    private double room_water_price;
    private double room_acreage;
    private String room_describe;
    private int room_rate;
    private String room_status;
    private int room_id_owner;

    public MotelRoom() {
    }

    public MotelRoom(int room_id, String room_address, String room_type, double room_price, double room_electric_price, double room_water_price, double room_acreage, String room_describe, int room_rate,String status, int room_id_owner) {
        this.room_id = room_id;
        this.room_address=room_address;
        this.room_type = room_type;
        this.room_price = room_price;
        this.room_electric_price = room_electric_price;
        this.room_water_price = room_water_price;
        this.room_acreage = room_acreage;
        this.room_describe = room_describe;
        this.room_rate = room_rate;
        this.room_status = status;
        this.room_id_owner = room_id_owner;
    }

    @Override
    public String toString() {
        return "MotelRoom{" +
                "room_acreage=" + room_acreage +
                ", room_id=" + room_id +
                ", room_address='" + room_address + '\'' +
                ", room_type='" + room_type + '\'' +
                ", room_price=" + room_price +
                ", room_electric_price=" + room_electric_price +
                ", room_water_price=" + room_water_price +
                ", room_describe='" + room_describe + '\'' +
                ", room_rate=" + room_rate +
                ", room_status='" + room_status + '\'' +
                ", room_id_owner=" + room_id_owner +
                '}';
    }

    public double getRoom_acreage() {
        return room_acreage;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getRoom_id_owner() {
        return room_id_owner;
    }

    public void setRoom_id_owner(int room_id_owner) {
        this.room_id_owner = room_id_owner;
    }

    public void setRoom_acreage(double room_acreage) {
        this.room_acreage = room_acreage;
    }

    public String getRoom_address() {
        return room_address;
    }

    public void setRoom_address(String room_address) {
        this.room_address = room_address;
    }

    public String getRoom_describe() {
        return room_describe;
    }

    public void setRoom_describe(String room_describe) {
        this.room_describe = room_describe;
    }

    public double getRoom_electric_price() {
        return room_electric_price;
    }

    public void setRoom_electric_price(double room_electric_price) {
        this.room_electric_price = room_electric_price;
    }
    public double getRoom_price() {
        return room_price;
    }

    public void setRoom_price(double room_price) {
        this.room_price = room_price;
    }

    public int getRoom_rate() {
        return room_rate;
    }

    public void setRoom_rate(int room_rate) {
        this.room_rate = room_rate;
    }

    public String getRoom_status() {
        return room_status;
    }

    public void setRoom_status(String room_status) {
        this.room_status = room_status;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public double getRoom_water_price() {
        return room_water_price;
    }

    public void setRoom_water_price(double room_water_price) {
        this.room_water_price = room_water_price;
    }
}
