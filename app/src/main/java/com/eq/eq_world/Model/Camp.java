package com.eq.eq_world.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Camp {
    private String campID;
    private String passWord;
    private String name;
    private String date;
    private String location;
    private String image;
    private String detail;
    private List<String> members;

    private final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Camps");

    public Camp(String campID) {
        this.campID = campID;
    }

    public String getCampID() {
        return campID;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCampID(String campID) {
        this.campID = campID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}


