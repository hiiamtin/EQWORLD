package com.eq.eq_world.Model;

import java.text.DateFormatSymbols;

public class DaySchedule {
    private String time;
    private String objective;
    private String date;

    public DaySchedule(String time, String objective, String date) {
        this.time = time;
        this.objective = objective;
        this.date = date;
    }

    public String getObjective() {
        return objective;
    }

    public String getTime() {
        if(objective.equals("date")){
            String[] form = time.split("-");
            String realDate = form[2] + " ";
            realDate = realDate + new DateFormatSymbols().getMonths()[Integer.valueOf(form[1])-1];
            realDate = realDate + " " + form[0];
            return realDate;
        }
        return time;
    }

    public String getDate() {
        return date;
    }

}
