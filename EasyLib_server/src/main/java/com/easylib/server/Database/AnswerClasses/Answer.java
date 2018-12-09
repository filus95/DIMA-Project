package com.easylib.server.Database.AnswerClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public abstract class Answer implements Serializable {

    abstract public Map<String, Object> getMapAttribute(ArrayList<String> columnsName);
}
