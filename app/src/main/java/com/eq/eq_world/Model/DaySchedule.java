package com.eq.eq_world.Model;

import java.util.HashMap;
import java.util.Iterator;

public class DaySchedule {
    private String date;
    public HashMap<String,String> events = new HashMap<>();

    public DaySchedule() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWholeDay(){
        String text = "";
        Iterator<String> vMap = this.events.keySet().iterator();
        while (vMap.hasNext()){
            String key = vMap.next();
            text = text + key + "  -  ";
            text = text + events.get(key) + "\n" ;
        }
        return text;
    }
}
