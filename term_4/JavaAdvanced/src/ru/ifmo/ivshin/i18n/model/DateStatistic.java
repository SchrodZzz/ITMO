package ru.ifmo.ivshin.i18n.model;

import java.util.Calendar;

public class DateStatistic {
    private Calendar value;
    private String source;

    public DateStatistic(Calendar value, String name) {
        this.value = value;
        this.source = name;
    }

    public Calendar getValue() {
        return value;
    }

    public String getSource() {
        return source;
    }

}
