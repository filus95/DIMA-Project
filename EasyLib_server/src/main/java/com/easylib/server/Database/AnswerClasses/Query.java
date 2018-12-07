package com.easylib.server.Database.AnswerClasses;

import java.util.ArrayList;
import java.util.Map;

public class Query extends Answer{
    private String title;
    private String author;
    private String category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public Map<String, Object> getMapAttribute(ArrayList<String> columnsName) {
        return null;
    }
}
