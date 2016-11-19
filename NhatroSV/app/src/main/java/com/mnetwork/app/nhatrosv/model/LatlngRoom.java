package com.mnetwork.app.nhatrosv.model;

/**
 * Created by vanthanhbk on 27/08/2016.
 */
public class LatlngRoom {
    private int latlog_id;
    private double latlog_lat;
    private double latlog_log;

    public LatlngRoom() {
    }

    public LatlngRoom(int latlog_id, double latlog_log, double latlog_lat) {
        this.latlog_id = latlog_id;
        this.latlog_lat = latlog_lat;
        this.latlog_log = latlog_log;
    }

    @Override
    public String toString() {
        return "LatlngRoom{" +
                "latlog_id=" + latlog_id +
                ", latlog_lat=" + latlog_lat +
                ", latlog_log=" + latlog_log +
                '}';
    }

    public int getLatlog_id() {
        return latlog_id;
    }

    public void setLatlog_id(int latlog_id) {
        this.latlog_id = latlog_id;
    }

    public double getLatlog_lat() {
        return latlog_lat;
    }

    public void setLatlog_lat(double latlog_lat) {
        this.latlog_lat = latlog_lat;
    }

    public double getLatlog_log() {
        return latlog_log;
    }

    public void setLatlog_log(double latlog_log) {
        this.latlog_log = latlog_log;
    }

}
