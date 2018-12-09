package com.easylib.server.Database.AnswerClasses;

import java.util.ArrayList;
import java.util.Map;

public class Book extends Answer {
    private String bookId_lib;
    private String title;
    private String id_type;
    private String identifier;
    private int pageCount;
    private String imageLink;
    private String description;
    private String publisher;
    private Float averageRating;
    private String langauge;
    private String publishedDate;
    private ArrayList<String> authors;
    private ArrayList<String> categories;
    private boolean reserved;
    private boolean waiting_list;


    public String getBookId_lib() {
        return bookId_lib;
    }

    public void setBookId_lib(String bookId_lib) {
        this.bookId_lib = bookId_lib;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public String getLangauge() {
        return langauge;
    }

    public void setLangauge(String langauge) {
        this.langauge = langauge;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(String author_1, String author_2, String author_3, String author_4) {
        authors = new ArrayList<>();
        if (author_1 != null)
            authors.add(author_1);
        if (author_2 != null)
            authors.add(author_2);
        if (author_3 != null)
            authors.add(author_3);
        if (author_4 != null)
            authors.add(author_4);
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(String category_1, String category_2, String category_3) {
       categories = new ArrayList<>();
       if (category_1 != null)
           categories.add(category_1);
       if (category_2 != null)
           categories.add(category_2);
       if (category_3 != null)
           categories.add(category_3);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Map<String, Object> getMapAttribute(ArrayList<String> columnsName) {
        return null;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public boolean isWaiting_list() {
        return waiting_list;
    }

    public void setWaiting_list(boolean waiting_list) {
        this.waiting_list = waiting_list;
    }
}
