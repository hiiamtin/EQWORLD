package com.eq.eq_world.Adapter;

public class FaqItem {

    String Title,Content;
    int userPhoto;

    public FaqItem() {
    }


    public FaqItem(String title, String content, int userPhoto) {
        Title = title;
        Content = content;
        this.userPhoto = userPhoto;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setUserPhoto(int userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }

    public int getUserPhoto() {
        return userPhoto;
    }
}
