package com.eq.eq_world.Model;

import java.util.Comparator;

public class SortByTime implements Comparator<DaySchedule> {
    @Override
    public int compare(DaySchedule d1, DaySchedule d2) {
        int a = d1.getTime().charAt(0);
        a = (a*10)+d1.getTime().charAt(1);
        a = (a*10)+d1.getTime().charAt(3);
        a = (a*10)+d1.getTime().charAt(4);

        int b = d2.getTime().charAt(0);
        b = (b*10)+d2.getTime().charAt(1);
        b = (b*10)+d2.getTime().charAt(3);
        b = (b*10)+d2.getTime().charAt(4);

        return a-b;
    }
}
