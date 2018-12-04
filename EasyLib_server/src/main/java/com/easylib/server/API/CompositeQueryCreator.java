package com.easylib.server.API;

import java.util.ArrayList;

public class CompositeQueryCreator extends QueryConteiner {
    private ArrayList<QueryConteiner> queriesType;

    CompositeQueryCreator(ArrayList<QueryConteiner> queriesType){
           this.queriesType = queriesType;
    }

    @Override
    String createQuery() {
        String query = null;
        for ( QueryConteiner queryType: queriesType){
            query+=(queryType.createQuery());
        }
        return query;
    }
}
