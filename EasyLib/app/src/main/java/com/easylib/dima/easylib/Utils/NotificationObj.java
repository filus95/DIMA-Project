package com.easylib.dima.easylib.Utils;

public class NotificationObj {

    private String date;
    private String text;
    private Boolean isNew;

    public NotificationObj(String date, String text, Boolean isNew) {
        this.date = date;
        this.text = text;
        this.isNew = isNew;
    }

    public String getDate() {
        return this.date;
    }

    public String getText() {
        return this.text;
    }

    public Boolean getIsNew() { return this.isNew; }

    public void setIsNew(Boolean isNew) { this.isNew = isNew; }
}
