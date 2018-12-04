package com.easylib.server.Database.AnswerClasses;

import java.text.SimpleDateFormat;

public class News {
    private int id;
    private String title;
    private SimpleDateFormat post_date;
    private String content;
    private String image_link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SimpleDateFormat getPost_date() {
        return post_date;
    }

    public void setPost_date(SimpleDateFormat post_date) {
        this.post_date = post_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }
}
