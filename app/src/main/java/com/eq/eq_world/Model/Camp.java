package com.eq.eq_world.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Camp {
    private String campID;
    private String passWord;
    private String name;
    private String date;
    private String location;
    private String image;
    private String creator;

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
        //ref.child(getCampID()).child("name").setValue(this.name);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        //ref.child(getCampID()).child("date").setValue(this.date);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        //ref.child(getCampID()).child("location").setValue(this.location);
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void assignRole(String uID, String role){
        ref.child(getCampID()).child("members").child(uID).setValue(role);
    }
}


