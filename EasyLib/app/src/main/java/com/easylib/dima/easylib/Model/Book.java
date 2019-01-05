package com.easylib.dima.easylib.Model;

public class Book {

    private String title;
    private String author;
    private String image;
    private int queue_num;

    public Book(String title, String author, String image, int queue_num) {
        this.title = title;
        this.author = author;
        this.image = image;
        this.queue_num = queue_num;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getImage() { return this.image; }

    public int getQueue() { return this.queue_num; }
}
