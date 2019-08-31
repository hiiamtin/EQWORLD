package com.eq.eq_world.Model;


public class ListActItem {

    String Title,Content,Type,Number,Description,Linkyoutube;

    public ListActItem(String title, String content, String type, String number, String description , String linkyoutube) {
        Title = title;
        Content = content;
        Type = type;
        Number = number;
        Description = description;
        Linkyoutube = linkyoutube;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLinkyoutube() {
        return Linkyoutube;
    }

    public void setLinkyoutube(String linkyoutube) {
        Linkyoutube = linkyoutube;
    }
}
