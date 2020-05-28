package ru.ifmo.ivshin.i18n;

import ru.ifmo.ivshin.i18n.model.StatisticType;
import ru.ifmo.ivshin.i18n.model.UnitStatistic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

public class StatisticPrinter implements IStatisticPrinter {

    String reportFileName;

    public StatisticPrinter(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    public void print(Map<StatisticType, UnitStatistic> statistics, String analyzedFileName, ResourceBundle bundle) {
        UnitStatistic sentencesStatistic = statistics.get(StatisticType.SENTENCE);
        UnitStatistic linesStatistic = statistics.get(StatisticType.LINE);
        UnitStatistic wordsStatistic = statistics.get(StatisticType.WORD);
        UnitStatistic numbersStatistic = statistics.get(StatisticType.NUMBER);
        UnitStatistic currencyStatistic = statistics.get(StatisticType.CURRENCY);
        UnitStatistic dateStatistic = statistics.get(StatisticType.DATE);

        StringBuilder report = new StringBuilder();
        report.append(getHTMLHeader());
        report.append(getStatHeader(bundle.getString("analyzedFile"), analyzedFileName));
        report.append(String.format(getCommonStatisticBlockTemplate(), bundle.getString("commonStat"),
                bundle.getString("Number"), bundle.getString("words"), wordsStatistic.numberOfElements,
                bundle.getString("Number"), bundle.getString("sentences"), sentencesStatistic.numberOfElements,
                bundle.getString("Number"), bundle.getString("lines"), linesStatistic.numberOfElements,
                bundle.getString("Number"), bundle.getString("dates"), dateStatistic.numberOfElements,
                bundle.getString("Number"), bundle.getString("numberel"), numbersStatistic.numberOfElements,
                bundle.getString("Number"), bundle.getString("currency"), currencyStatistic.numberOfElements));
        report.append(String.format(getStatisticBlockTemplate(true), bundle.getString("sentencesStat"),
                bundle.getString("Number"), bundle.getString("sentences"), sentencesStatistic.numberOfElements, sentencesStatistic.numberOfUniqueElements, bundle.getString("unique"),
                bundle.getString("min"), bundle.getString("sentence"), sentencesStatistic.minElement,
                bundle.getString("max"), bundle.getString("sentence"), sentencesStatistic.maxElement,
                bundle.getString("maxya"), bundle.getString("length"), bundle.getString("sentencesya"), sentencesStatistic.maxLength, sentencesStatistic.maxLengthElement,
                bundle.getString("minya"), bundle.getString("length"), bundle.getString("sentencesya"), sentencesStatistic.minLength, sentencesStatistic.minLengthElement,
                bundle.getString("averageya"), bundle.getString("length"), bundle.getString("sentencesya"), ((int) sentencesStatistic.averageLength)));
        report.append(String.format(getStatisticBlockTemplate(true), bundle.getString("wordsStat"),
                bundle.getString("Number"), bundle.getString("words"), wordsStatistic.numberOfElements, wordsStatistic.numberOfUniqueElements, bundle.getString("unique"),
                bundle.getString("min"), bundle.getString("wordo"), wordsStatistic.minElement,
                bundle.getString("max"), bundle.getString("wordo"), wordsStatistic.maxElement,
                bundle.getString("maxya"), bundle.getString("length"), bundle.getString("word"), wordsStatistic.maxLength, wordsStatistic.maxLengthElement,
                bundle.getString("minya"), bundle.getString("length"), bundle.getString("word"), wordsStatistic.minLength, wordsStatistic.minLengthElement,
                bundle.getString("averageya"), bundle.getString("length"), bundle.getString("word"), ((int) wordsStatistic.averageLength)));
        report.append(String.format(getStatisticBlockTemplate(true), bundle.getString("linesStat"),
                bundle.getString("Number"), bundle.getString("lines"), linesStatistic.numberOfElements, linesStatistic.numberOfUniqueElements, bundle.getString("unique"),
                bundle.getString("minya"), bundle.getString("linea"), linesStatistic.minElement,
                bundle.getString("maxya"), bundle.getString("linea"), linesStatistic.maxElement,
                bundle.getString("maxya"), bundle.getString("length"), bundle.getString("line"), linesStatistic.maxLength, linesStatistic.maxLengthElement,
                bundle.getString("minya"), bundle.getString("length"), bundle.getString("line"), linesStatistic.minLength, linesStatistic.minLengthElement,
                bundle.getString("averageya"), bundle.getString("length"), bundle.getString("line"), ((int) linesStatistic.averageLength)));
        report.append(String.format(getStatisticBlockTemplate(false), bundle.getString("currencyStat"),
                bundle.getString("Number"), bundle.getString("currency"), currencyStatistic.numberOfElements, currencyStatistic.numberOfUniqueElements, bundle.getString("unique"),
                bundle.getString("minya"), bundle.getString("sum"), currencyStatistic.minElement,
                bundle.getString("maxya"), bundle.getString("sum"), currencyStatistic.maxElement,
                bundle.getString("maxya"), bundle.getString("length"), bundle.getString("currency"), currencyStatistic.maxLength, currencyStatistic.maxLengthElement,
                bundle.getString("minya"), bundle.getString("length"), bundle.getString("currency"), currencyStatistic.minLength, currencyStatistic.minLengthElement,
                bundle.getString("averageya"), bundle.getString("sum"), currencyStatistic.averageLength));
        report.append(String.format(getStatisticBlockTemplate(false), bundle.getString("numbersStat"),
                bundle.getString("Number"), bundle.getString("numberel"), numbersStatistic.numberOfElements, numbersStatistic.numberOfUniqueElements, bundle.getString("unique"),
                bundle.getString("min"), bundle.getString("number"), numbersStatistic.minElement,
                bundle.getString("max"), bundle.getString("number"), numbersStatistic.maxElement,
                bundle.getString("maxya"), bundle.getString("length"), bundle.getString("numbers"), numbersStatistic.maxLength, numbersStatistic.maxLengthElement,
                bundle.getString("minya"), bundle.getString("length"), bundle.getString("numbers"), numbersStatistic.minLength, numbersStatistic.minLengthElement,
                bundle.getString("average"), bundle.getString("number"), numbersStatistic.averageLength));
        report.append(String.format(getStatisticBlockTemplate(false), bundle.getString("datesStat"),
                bundle.getString("Number"), bundle.getString("dates"), dateStatistic.numberOfElements, dateStatistic.numberOfUniqueElements, bundle.getString("unique"),
                bundle.getString("minya"), bundle.getString("datea"), dateStatistic.minElement,
                bundle.getString("maxya"), bundle.getString("datea"), dateStatistic.maxElement,
                bundle.getString("maxya"), bundle.getString("length"), bundle.getString("date"), dateStatistic.maxLength, dateStatistic.maxLengthElement,
                bundle.getString("minya"), bundle.getString("length"), bundle.getString("date"), dateStatistic.minLength, dateStatistic.minLengthElement,
                bundle.getString("average"), bundle.getString("time"), dateStatistic.averageLength));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFileName))) {
            writer.write(report.toString());
        } catch (IOException e) {
            System.err.println("Failed to create/write output file: " + e.getMessage());
        }
    }

    private String getHTMLHeader() {
        return "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>Text statistic</title>\n" +
                "</head>\n" +
                "<body>\n\n";
    }

    private String getStatHeader(String prefix, String analyzedFileName) {
        return String.format("<h1>%s: %s</h1>\n", prefix, analyzedFileName);
    }

    private String getCommonStatisticBlockTemplate() {
        return "<p>\n" +
                "<b>%s:</b><br>\n" +
                "%s %s: %d<br>\n" +
                "%s %s: %d<br>\n" +
                "%s %s: %d<br>\n" +
                "%s %s: %d<br>\n" +
                "%s %s: %d<br>\n" +
                "%s %s: %d<br>\n" +
                "</p>\n\n";
    }

    private String getStatisticBlockTemplate(boolean key) {
        return "<p>\n" +
                "<b>%s:</b><br>\n" +
                "%s %s: %d (%d %s)<br>\n" +
                "%s %s: %s<br>\n" +
                "%s %s: %s<br>\n" +
                "%s %s %s: %d (%s)<br>\n" +
                "%s %s %s: %d (%s)<br>\n" +
                (key ? "%s %s %s: %d<br>\n" : "%s %s: %f<br>\n") +
                "</p>\n\n";

    }

    private String getHTMLFooter() {
        return "</body>\n" +
                "</html>\n";
    }

}
