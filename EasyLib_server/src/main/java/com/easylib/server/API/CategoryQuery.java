package com.easylib.server.API;

public class CategoryQuery extends QueryConteiner{
    private String category;

    CategoryQuery(String category){
        this.category = category;
    }

    @Override
    String createQuery() {
        return "subject:"+category;
    }
}
