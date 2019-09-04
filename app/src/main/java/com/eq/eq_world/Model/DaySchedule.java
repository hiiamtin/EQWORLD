package com.eq.eq_world.Model;

import java.text.DateFormatSymbols;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class DaySchedule {
    private String date;
    public HashMap<String,String> events = new HashMap<>();

    public DaySchedule() {
    }

    public String getDate() {
        String[] form = date.split("-");
        String realDate = form[2] + " ";
        realDate = realDate + new DateFormatSymbols().getMonths()[Integer.valueOf(form[1])-1];
        realDate = realDate + " " + form[0];
        return realDate;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWholeDay(){
        Map<String,String> treeMap = new TreeMap<>(this.events);
        String text = "";
        Iterator<String> vMap = treeMap.keySet().iterator();
        while (vMap.hasNext()){
            String key = vMap.next();
            text = text + key + "    ";
            text = text + treeMap.get(key) + "\n" ;
        }
        return text;
    }

}
