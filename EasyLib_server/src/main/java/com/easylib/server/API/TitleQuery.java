package com.easylib.server.API;

public class TitleQuery extends QueryConteiner {

    private String title;

    TitleQuery(String title){
        this.title = title;
    }

    @Override
    String createQuery() {
        return "intitle:"+title;
    }
}
