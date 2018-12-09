package com.easylib.server.Database.AnswerClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserPreferences extends Answer{

    private int id_lib1;
    private int id_lib2;
    private int id_lib3;

    public int getId_lib1() {
        return id_lib1;
    }

    public void setId_lib1(int id_lib1) {
        this.id_lib1 = id_lib1;
    }

    public int getId_lib2() {
        return id_lib2;
    }

    public void setId_lib2(int id_lib2) {
        this.id_lib2 = id_lib2;
    }

    public int getId_lib3() {
        return id_lib3;
    }

    public void setId_lib3(int id_lib3) {
        this.id_lib3 = id_lib3;
    }

    @Override
    public Map<String, Object> getMapAttribute(ArrayList<String> columnsName) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(columnsName.get(0), getId_lib1());
        map.put(columnsName.get(1), getId_lib2());
        map.put(columnsName.get(2), getId_lib3());

        return map;
    }
}
