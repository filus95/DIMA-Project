package com.easylib.server.Database.AnswerClasses;

import java.util.ArrayList;

public class LibraryContent extends Answer {

    private String lib_name;
    private ArrayList<News> news;
    private ArrayList<Event> events;
    private ArrayList<Book> books;
    private int idLib;

    public String getLib_name() {
        return lib_name;
    }

    public void setLib_name(String lib_name) {
        this.lib_name = lib_name;
    }

    public ArrayList<News> getNews() {
        return news;
    }

    public void setNews(ArrayList<News> news) {
        this.news = news;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public int getIdLib() {
        return idLib;
    }

    public void setIdLib(int idLib) {
        this.idLib = idLib;
    }
}
