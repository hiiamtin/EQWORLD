package com.eq.eq_world.Model;

public class SongItem {
    String Name,Linkyoutube;

    public SongItem() {
    }

    public SongItem(String name, String linkyoutube) {
        Name = name;
        Linkyoutube = linkyoutube;
    }


    public String getLinkyoutube() {
        return Linkyoutube;
    }

    public void setLinkyoutube(String linkyoutube) {
        Linkyoutube = linkyoutube;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
