package com.easylib.server.Database.AnswerClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Event_partecipant extends Answer {
    private int event_id;
    private int partecipant_id;

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getPartecipant_id() {
        return partecipant_id;
    }

    public void setPartecipant_id(int partecipant_id) {
        this.partecipant_id = partecipant_id;
    }

    @Override
    public Map<String, Object> getMapAttribute(ArrayList<String> columnsName) {
        Map<String, Object> map = new HashMap<>();
        map.put(columnsName.get(0),getEvent_id());
        map.put(columnsName.get(1), getPartecipant_id());
        return map;
    }
}
