package com.easylib.server.Database.AnswerClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LibraryDescriptor extends Answer {
    private int id_lib;
    private String lib_name;
    private String schema_name;
    private String image_link;
    private String telephone_number;
    private String address;
    private String email;
    private String description;

    public int getId_lib() {
        return id_lib;
    }

    public void setId_lib(int id_lib) {
        this.id_lib = id_lib;
    }

    public String getLib_name() {
        return lib_name;
    }

    public void setLib_name(String lib_name) {
        this.lib_name = lib_name;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getTelephone_number() {
        return telephone_number;
    }

    public void setTelephone_number(String telephone_number) {
        this.telephone_number = telephone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Map<String, Object> getMapAttribute(ArrayList<String> columnsName) {
        Map<String, Object> map = new HashMap<>();
        map.put(columnsName.get(0), getLib_name());
        map.put(columnsName.get(1), getSchema_name());
        map.put(columnsName.get(2), getImage_link());
        map.put(columnsName.get(3), getTelephone_number());
        map.put(columnsName.get(4), getAddress());
        map.put(columnsName.get(5), getEmail());
        map.put(columnsName.get(6), getDescription());

        return map;
    }

    public String getSchema_name() {
        return schema_name;
    }

    public void setSchema_name(String schema_name) {
        this.schema_name = schema_name;
    }
}
