package com.easylib.server.Database.AnswerClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Event extends Answer{
    private int id;
    private String title;
    private String description;
    private String image_link;
    private int seats;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public Map<String, Object> getMapAttribute(ArrayList<String> columnsName) {
        Map<String, Object> map = new HashMap<>();
        map.put(columnsName.get(0), getTitle());
        map.put(columnsName.get(1), getDescription());
        map.put(columnsName.get(2), getImage_link());
        map.put(columnsName.get(3), getSeats());

        return map;
    }
}
