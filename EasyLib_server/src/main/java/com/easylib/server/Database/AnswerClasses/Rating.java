package com.easylib.server.Database.AnswerClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Rating extends Answer {

    private int user_id;
    private String book_identifier;
    private int rating;


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getBook_identifier() {
        return book_identifier;
    }

    public void setBook_identifier(String book_identifier) {
        this.book_identifier = book_identifier;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public Map<String, Object> getMapAttribute(ArrayList<String> columnsName) {
        Map<String, Object> map = new HashMap<>();

        map.put(columnsName.get(0), getUser_id());
        map.put(columnsName.get(1), getBook_identifier());
        map.put(columnsName.get(2), getRating());

        return map;
    }
}
