package ru.ifmo.ivshin.i18n.model;

public class CurrencyStatistic {
    private Number value;
    private String source;

    public CurrencyStatistic(Number value, String source) {
        this.value = value;
        this.source = source;
    }

    public Number getValue() {
        return value;
    }

    public String getSource() {
        return source;
    }

}
