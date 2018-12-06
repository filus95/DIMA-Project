package com.easylib.server.Database.AnswerClasses;

public class LibraryDescriptor extends Answer {
    private int id_lib;
    private String lib_name;
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
}
