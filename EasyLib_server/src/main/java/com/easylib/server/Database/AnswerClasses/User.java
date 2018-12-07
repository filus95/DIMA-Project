package com.easylib.server.Database.AnswerClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User extends Answer {

    private int user_id;
    private String username;
    private String email;
    private String hashed_pd;
    private String salt;
    private String plainPassword;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Map<String, Object> getMapAttribute(ArrayList<String> columnsName) {
        Map<String, Object> map = new HashMap<>();
        map.put(columnsName.get(0), getUsername());
        map.put(columnsName.get(1), getEmail());
        map.put(columnsName.get(2), getHashed_pd());
        map.put(columnsName.get(3), getSalt());

        return map;
    }

    public String getHashed_pd() {
        return hashed_pd;
    }

    public void setHashed_pd(String hashed_pd) {
        this.hashed_pd = hashed_pd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }
}
