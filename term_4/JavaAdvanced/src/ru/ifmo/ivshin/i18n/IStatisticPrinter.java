package ru.ifmo.ivshin.i18n;

import ru.ifmo.ivshin.i18n.model.StatisticType;
import ru.ifmo.ivshin.i18n.model.UnitStatistic;

import java.util.Map;
import java.util.ResourceBundle;

public interface IStatisticPrinter {

    void print(Map<StatisticType, UnitStatistic> statistics, String analyzedFileName, ResourceBundle bundle);

}
