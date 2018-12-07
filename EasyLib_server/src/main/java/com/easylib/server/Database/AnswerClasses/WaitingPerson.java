package com.easylib.server.Database.AnswerClasses;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

public class WaitingPerson extends Answer{
    private int reservation_id;
    private int user_id;
    private String book_identifier;
    private int waiting_pos;
    private SimpleDateFormat reservation_date;
    private SimpleDateFormat start_res_date;
    private SimpleDateFormat end_res_date;
    int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

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

    public int getWaiting_pos() {
        return waiting_pos;
    }

    public void setWaiting_pos(int waiting_pos) {
        this.waiting_pos = waiting_pos;
    }

    public SimpleDateFormat getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(SimpleDateFormat reservation_date) {
        this.reservation_date = reservation_date;
    }

    public SimpleDateFormat getStart_res_date() {
        return start_res_date;
    }

    public void setStart_res_date(SimpleDateFormat start_res_date) {
        this.start_res_date = start_res_date;
    }

    public SimpleDateFormat getEnd_res_date() {
        return end_res_date;
    }

    public void setEnd_res_date(SimpleDateFormat end_res_date) {
        this.end_res_date = end_res_date;
    }

    @Override
    public Map<String, Object> getMapAttribute(ArrayList<String> columnsName) {
        Map<String,Object> map = new HashMap<>();

        map.put(columnsName.get(0), getUser_id());
        map.put(columnsName.get(0), getBook_identifier());
        map.put(columnsName.get(0), getWaiting_pos());
        map.put(columnsName.get(0), getReservation_date());
        map.put(columnsName.get(0), getStart_res_date());
        map.put(columnsName.get(0), getEnd_res_date());
        map.put(columnsName.get(0), getQuantity());

        return map;
    }
}
