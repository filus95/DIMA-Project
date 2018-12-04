package com.easylib.server.API;

public class GenericQuery extends QueryConteiner {

    String genericQuery;

    GenericQuery(String genericQuery){
        this.genericQuery = genericQuery;
    }

    @Override
    String createQuery() {
        return genericQuery;
    }
}
