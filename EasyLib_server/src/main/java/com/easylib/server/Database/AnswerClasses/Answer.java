package com.easylib.server.Database.AnswerClasses;

import java.util.ArrayList;
import java.util.Map;

public abstract class Answer {

    abstract public Map<String, Object> getMapAttribute(ArrayList<String> columnsName);
}
