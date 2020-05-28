package ru.ifmo.ivshin.i18n;

import ru.ifmo.ivshin.i18n.model.StatisticType;
import ru.ifmo.ivshin.i18n.model.UnitStatistic;

import java.util.Map;

public interface ITextStatistic {

    Map<StatisticType, UnitStatistic> getStatisticReport();

}
