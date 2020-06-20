package com.example.tlocal_linux.MODELS;

import java.util.List;

public class localInfo {
    private String _id;
    private String nameLocal;
    private String description;
    private String address;
    private String phoneNumber;
    private String namePersonal;
    private String openTime;
    private String category;
    private List<String> delivery = null;
    private String dirImage;
    private List<String> location = null;


    public localInfo(String _id, String nameLocal, String description, String address, String phoneNumber, String namePersonal, String openTime, String category, List<String> delivery, String dirImage, List<String> location) {
        this._id = _id;
        this.nameLocal = nameLocal;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.namePersonal = namePersonal;
        this.openTime = openTime;
        this.category = category;
        this.delivery = delivery;
        this.dirImage = dirImage;
        this.location = location;
    }
    public localInfo() {

    }






    public String getDirImage() {
        return dirImage;
    }

    public void setDirImage(String dirImage) {
        this.dirImage = dirImage;
    }

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public String getdirImage() {
        return dirImage;
    }

    public void setdirImage(String dirImage) {
        this.dirImage = dirImage;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNameLocal() {
        return nameLocal;
    }

    public void setNameLocal(String nameLocal) {
        this.nameLocal = nameLocal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNamePersonal() {
        return namePersonal;
    }

    public void setNamePersonal(String namePersonal) {
        this.namePersonal = namePersonal;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getDelivery() {
        return delivery;
    }

    public void setDelivery(List<String> delivery) {
        this.delivery = delivery;
    }
}
