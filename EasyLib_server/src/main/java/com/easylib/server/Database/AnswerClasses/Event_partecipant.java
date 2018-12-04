package com.easylib.server.Database.AnswerClasses;

public class Event_partecipant extends Answer {
    int event_id;
    int partecipant_id;

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
}
