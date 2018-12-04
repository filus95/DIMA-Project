package com.easylib.server.API;

public class AuthorQuery extends QueryConteiner{
    private String author;

    AuthorQuery(String author){
        this.author = author;
    }

    @Override
    String createQuery() {
        return "inauthor:"+author;
    }
}
