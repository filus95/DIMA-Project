package com.easylib.server.Database.AnswerClasses;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class News extends Answer{
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

    @Override
    public Map<String, Object> getMapAttribute(ArrayList<String> columnsName) {
        Map<String, Object> map = new HashMap<>();
        map.put(columnsName.get(0), getTitle());
        map.put(columnsName.get(1), getPost_date());
        map.put(columnsName.get(1), getContent());
        map.put(columnsName.get(1), getImage_link());

        return map;
    }
}
