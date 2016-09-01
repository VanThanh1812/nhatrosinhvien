package com.mnetwork.app.nhatrosv.model;

/**
 * Created by vanthanhbk on 26/08/2016.
 */
public class HouseOwner {
    private int owner_id;
    private String owner_name;
    private int owner_age;
    private String owner_phone;
    private String owner_email;
    private String owner_address;

    public HouseOwner() {
    }

    public HouseOwner(int owner_id, String owner_name,String owner_phone, int owner_age, String owner_address, String owner_email) {
        this.owner_id = owner_id;
        this.owner_name = owner_name;
        this.owner_phone = owner_phone;
        this.owner_age = owner_age;
        this.owner_address = owner_address;
        this.owner_email = owner_email;
    }

    @Override
    public String toString() {
        return "HouseOwner{" +
                "owner_address='" + owner_address + '\'' +
                ", owner_id=" + owner_id +
                ", owner_name='" + owner_name + '\'' +
                ", owner_age=" + owner_age +
                ", owner_phone='" + owner_phone + '\'' +
                ", owner_email='" + owner_email + '\'' +
                '}';
    }

    public String getOwner_address() {
        return owner_address;
    }

    public void setOwner_address(String owner_address) {
        this.owner_address = owner_address;
    }

    public int getOwner_age() {
        return owner_age;
    }

    public void setOwner_age(int owner_age) {
        this.owner_age = owner_age;
    }

    public String getOwner_email() {
        return owner_email;
    }

    public void setOwner_email(String owner_email) {
        this.owner_email = owner_email;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getOwner_phone() {
        return owner_phone;
    }

    public void setOwner_phone(String owner_phone) {
        this.owner_phone = owner_phone;
    }
}
