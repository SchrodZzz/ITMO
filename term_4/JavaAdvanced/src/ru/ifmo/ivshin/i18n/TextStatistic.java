package ru.ifmo.ivshin.i18n;

import ru.ifmo.ivshin.i18n.model.CurrencyStatistic;
import ru.ifmo.ivshin.i18n.model.DateStatistic;
import ru.ifmo.ivshin.i18n.model.StatisticType;
import ru.ifmo.ivshin.i18n.model.UnitStatistic;

import java.text.*;
import java.util.*;

public class TextStatistic implements ITextStatistic {
    private Locale locale;
    private String text;

    public TextStatistic(Locale locale, String text) {
        this.text = text;
        this.locale = locale;
    }

    public Map<StatisticType, UnitStatistic> getStatisticReport() {
        Map<StatisticType, UnitStatistic> statisticList = new HashMap<>();
        statisticList.put(StatisticType.SENTENCE, getSentenceStatistic());
        statisticList.put(StatisticType.LINE, getLineStatistic());
        statisticList.put(StatisticType.WORD, getWordStatistic());
        statisticList.put(StatisticType.CURRENCY, getCurrencyStatistic());
        statisticList.put(StatisticType.NUMBER, getNumberStatistic());
        statisticList.put(StatisticType.DATE, getDateStatistic());

        return statisticList;
    }

    public UnitStatistic getSentenceStatistic() {
        return getStatistic(BreakIterator.getSentenceInstance(locale), true);
    }

    public UnitStatistic getLineStatistic() {
        return getStatistic(BreakIterator.getLineInstance(locale), false);
    }

    public UnitStatistic getWordStatistic() {
        return getStatistic(BreakIterator.getWordInstance(locale), false);
    }

    private UnitStatistic getStatistic(BreakIterator it, boolean isSentence) {
        Set<String> uniqueElements = new HashSet<>();
        UnitStatistic statistic = new UnitStatistic();
        Collator comparator = Collator.getInstance(locale);

        double len = 0;

        it.setText(text);
        int start = it.first();
        for (int end = it.next(); end != BreakIterator.DONE; start = end, end = it.next()) {
            String curElement = (isSentence) ? text.substring(start, end) :
                    text.substring(start, end).replaceAll("\\s", "");
            if (!curElement.isEmpty()) {
                len += curElement.length();
                statistic.numberOfElements++;
                uniqueElements.add(curElement);
                if (statistic.minLengthElement == null || curElement.length() < statistic.minLength) {
                    statistic.minLengthElement = curElement;
                    statistic.minLength = curElement.length();
                }
                if (statistic.maxLengthElement == null || curElement.length() > statistic.maxLength) {
                    statistic.maxLengthElement = curElement;
                    statistic.maxLength = curElement.length();
                }
                if (statistic.minElement == null || comparator.compare(curElement, statistic.minElement) < 0) {
                    statistic.minElement = curElement;
                }
                if (statistic.maxElement == null || comparator.compare(statistic.minElement, curElement) < 0) {
                    statistic.maxElement = curElement;
                }
            }
        }

        statistic.numberOfUniqueElements = uniqueElements.size();
        statistic.averageLength = len / statistic.numberOfElements;

        return statistic;
    }

    public UnitStatistic getCurrencyStatistic() {
        UnitStatistic currencyStatistic = new UnitStatistic();
        BreakIterator it = BreakIterator.getWordInstance(locale);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        List<CurrencyStatistic> currencyList = new ArrayList<>();

        it.setText(text);
        int start = it.first();
        for (int end = it.next(); end != BreakIterator.DONE; start = end, end = it.next()) {
            String currentElement = text.substring(start, end);
            try {
                Number current = currencyFormat.parse(currentElement);
                currencyList.add(new CurrencyStatistic(current, currentElement));
            } catch (ParseException ignored) {
            }
        }

        if (!currencyList.isEmpty()) {
            CurrencyStatistic dummy = new CurrencyStatistic(0, "");
            currencyStatistic.numberOfElements = currencyList.size();
            currencyStatistic.numberOfUniqueElements = (int) currencyList.stream().distinct().count();
            currencyStatistic.minElement = currencyList.stream()
                    .reduce((e1, e2) -> (e1.getValue().doubleValue() < e2.getValue().doubleValue()) ? e1 : e2).orElse(dummy).getSource();
            currencyStatistic.maxElement = currencyList.stream()
                    .reduce((e1, e2) -> (e1.getValue().doubleValue() > e2.getValue().doubleValue()) ? e1 : e2).orElse(dummy).getSource();
            currencyStatistic.minLengthElement = currencyList.stream().map(CurrencyStatistic::getSource)
                    .min(Comparator.comparing(String::length)).orElse("");
            currencyStatistic.maxLengthElement = currencyList.stream().map(CurrencyStatistic::getSource)
                    .max(Comparator.comparing(String::length)).orElse("");
            currencyStatistic.minLength = currencyStatistic.minLengthElement.length();
            currencyStatistic.maxLength = currencyStatistic.maxLengthElement.length();
            currencyStatistic.averageLength = currencyList.stream()
                    .map(e -> e.getValue().doubleValue())
                    .reduce(Double::sum).orElse(0.0)
                    / currencyStatistic.numberOfElements;
        }

        return currencyStatistic;
    }

    public UnitStatistic getNumberStatistic() {
        UnitStatistic numberStatistic = new UnitStatistic();
        BreakIterator it = BreakIterator.getWordInstance(locale);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        List<Double> numberList = new ArrayList<>();

        it.setText(text);
        int start = it.first();
        for (int end = it.next(); end != BreakIterator.DONE; start = end, end = it.next()) {
            String currentElement = text.substring(start, end);
            try {
                Number current = numberFormat.parse(currentElement);
                numberList.add(current.doubleValue());
            } catch (ParseException ignored) {
            }
        }

        if (!numberList.isEmpty()) {
            numberStatistic.numberOfElements = numberList.size();
            numberStatistic.numberOfUniqueElements = (int) numberList.stream().distinct().count();
            numberStatistic.minElement = numberList.stream().min(Double::compare).get().toString();
            numberStatistic.maxElement = numberList.stream().max(Double::compare).get().toString();
            numberStatistic.minLengthElement = numberList.stream()
                    .map(d -> ((d == Math.floor(d)) && !Double.isInfinite(d)) ? Integer.toString(d.intValue()) : d.toString())
                    .min(Comparator.comparing(String::length)).orElse("");
            numberStatistic.maxLengthElement = numberList.stream()
                    .map(d -> ((d == Math.floor(d)) && !Double.isInfinite(d)) ? Integer.toString(d.intValue()) : d.toString())
                    .max(Comparator.comparing(String::length)).orElse("");
            numberStatistic.minLength = numberStatistic.minLengthElement.length();
            numberStatistic.maxLength = numberStatistic.maxLengthElement.length();
            numberStatistic.averageLength = numberList.stream()
                    .reduce(Double::sum).orElse(0.0) / numberStatistic.numberOfElements;
        }

        return numberStatistic;
    }

    public UnitStatistic getDateStatistic() {
        UnitStatistic dateStatistic = new UnitStatistic();
        BreakIterator it = BreakIterator.getLineInstance(locale);
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        List<DateStatistic> dateList = new ArrayList<>();

        it.setText(text);
        int start = it.first();
        for (int end = it.next(); end != BreakIterator.DONE; start = end, end = it.next()) {
            String currentLine = text.substring(start, end);
            try {
                Calendar current = Calendar.getInstance(locale);
                current.setTime(dateFormat.parse(currentLine));
                dateList.add(new DateStatistic(current, currentLine));
            } catch (ParseException ignored) {
            }
        }

        if (!dateList.isEmpty()) {
            DateStatistic dummy = new DateStatistic(Calendar.getInstance(), "");
            dateStatistic.numberOfElements = dateList.size();
            dateStatistic.numberOfUniqueElements = (int) dateList.stream().distinct().count();
            dateStatistic.minElement = dateList.stream()
                    .reduce((e1, e2) -> (e1.getValue().before(e2.getValue())) ? e1 : e2).orElse(dummy).getSource();
            dateStatistic.maxElement = dateList.stream()
                    .reduce((e1, e2) -> (e1.getValue().after(e2.getValue())) ? e1 : e2).orElse(dummy).getSource();
            dateStatistic.minLengthElement = dateList.stream().map(DateStatistic::getSource)
                    .min(Comparator.comparing(String::length)).orElse("");
            dateStatistic.maxLengthElement = dateList.stream().map(DateStatistic::getSource)
                    .max(Comparator.comparing(String::length)).orElse("");
            dateStatistic.minLength = dateStatistic.minLengthElement.length();
            dateStatistic.maxLength = dateStatistic.maxLengthElement.length();
            dateStatistic.averageLength = dateList.stream()
                    .map(e -> (double) e.getValue().getTime().getTime())
                    .reduce(Double::sum).orElse(0.0)
                    / dateStatistic.numberOfElements;
        }

        return dateStatistic;
    }

}
