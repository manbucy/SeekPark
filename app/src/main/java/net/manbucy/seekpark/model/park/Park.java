package net.manbucy.seekpark.model.park;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Park
 * Created by yang on 2017/6/28.
 */

public class Park extends BmobObject{
    private String name;
    private String address;
    private String city;
    private BmobGeoPoint location;
    private int number;
    private double price;
    private boolean hasCharging;
    private int chargingNumber;
    private double chargingPrice;
    private String imageUrl;
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BmobGeoPoint getLocation() {
        return location;
    }

    public void setLocation(BmobGeoPoint location) {
        this.location = location;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isHasCharging() {
        return hasCharging;
    }

    public void setHasCharging(boolean hasCharging) {
        this.hasCharging = hasCharging;
    }

    public int getChargingNumber() {
        return chargingNumber;
    }

    public void setChargingNumber(int chargingNumber) {
        this.chargingNumber = chargingNumber;
    }

    public double getChargingPrice() {
        return chargingPrice;
    }

    public void setChargingPrice(double chargingPrice) {
        this.chargingPrice = chargingPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
