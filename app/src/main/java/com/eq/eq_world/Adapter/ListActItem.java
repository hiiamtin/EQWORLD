package com.eq.eq_world.Adapter;

public class ListActItem {

    String Title,Content;

    public ListActItem() {
    }


    public ListActItem(String title, String content) {
        Title = title;
        Content = content;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }
}
