package com.easylib.server.Database.AnswerClasses;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Reservation extends Answer{
    private int reservation_id;
    private int user_id;
    private String book_idetifier;
    private String book_title;
    private Date reservation_date;
    private Date start_res_date;
    private Date end_res_date;
    private int quantity;

    public Map<String, Object> getMapAttribute(ArrayList<String> columnsName){
        Map<String, Object> map = new HashMap<>();
        map.put(columnsName.get(0), getUser_id());
        map.put(columnsName.get(1), getBook_idetifier());
        map.put(columnsName.get(2), getBook_title());
        map.put(columnsName.get(3), getReservation_date());
        map.put(columnsName.get(4), getStart_res_date());
        map.put(columnsName.get(5), getEnd_res_date());
        map.put(columnsName.get(6), getQuantity());
        map.put(columnsName.get(7), getUser_id());

        return map;

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

    public String getBook_idetifier() {
        return book_idetifier;
    }

    public void setBook_idetifier(String book_idetifier) {
        this.book_idetifier = book_idetifier;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public Date getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(Date reservation_date) {
        this.reservation_date = reservation_date;
    }

    public Date getStart_res_date() {
        return start_res_date;
    }

    public void setStart_res_date(Date start_res_date) {
        this.start_res_date = start_res_date;
    }

    public Date getEnd_res_date() {
        return end_res_date;
    }

    public void setEnd_res_date(Date end_res_date) {
        this.end_res_date = end_res_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
