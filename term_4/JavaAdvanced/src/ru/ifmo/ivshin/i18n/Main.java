package ru.ifmo.ivshin.i18n;

import ru.ifmo.ivshin.i18n.model.StatisticType;
import ru.ifmo.ivshin.i18n.model.UnitStatistic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Main {

    public static void main(String[] args) {
        if (args == null || args.length != 4) {
            System.err.println("Usage: <input_text_locale> <report_text_locale> <input_text_file> <report_text_file>.");
            System.err.println("If locale has more than one word - use quote symbol: \"; Ex: \"English (United States)\"");
            System.err.println("Available locales:");
            Arrays.stream(Locale.getAvailableLocales()).map(Locale::getDisplayName).sorted().forEachOrdered(System.err::println);
            return;
        }

        Locale inFileLocale = parseLocale(args[0]);
        Locale reportFileLocale = parseLocale(args[1]);
        String inFileName = args[2];
        String reportFileName = args[3];

        assert inFileLocale != null && reportFileLocale != null : "Incorrect locale";

        String text;
        try {
            text = Files.readString(Paths.get(inFileName));
        } catch (IOException e) {
            System.err.println("Failed to read/process input file: " + e.getMessage());
            return;
        } catch (InvalidPathException e) {
            System.err.println("Incorrect input file path: " + e.getMessage());
            return;
        }

        TextStatistic statistics = new TextStatistic(inFileLocale, text);
        Map<StatisticType, UnitStatistic> statisticsReport = statistics.getStatisticReport();

        //TODO: improve report locale add process ~(move into IStatisticPrinter.print())
        ResourceBundle bundle;
        switch (reportFileLocale.getLanguage()) {
            case "en":
                bundle = ResourceBundle.getBundle("ru.ifmo.ivshin.i18n.text_en");
                break;
            case "ru":
                bundle = ResourceBundle.getBundle("ru.ifmo.ivshin.i18n.text_ru");
                break;
            default:
                System.err.println("Unsupported locale: " + reportFileLocale.getLanguage());
                System.err.println("Supported locales: [en, ru]");
                return;
        }

        IStatisticPrinter printer = new StatisticPrinter(reportFileName);
        printer.print(statisticsReport, inFileName, bundle);
    }

    private static Locale parseLocale(String str) {
        return Arrays.stream(Locale.getAvailableLocales())
                .filter(locale -> locale.getDisplayName().equals(str)).findFirst().orElse(null);
    }
}
