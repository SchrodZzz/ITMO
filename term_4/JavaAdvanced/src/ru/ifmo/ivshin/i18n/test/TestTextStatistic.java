package ru.ifmo.ivshin.i18n.test;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.ifmo.ivshin.i18n.Main;
import ru.ifmo.ivshin.i18n.StatisticPrinter;
import ru.ifmo.ivshin.i18n.TextStatistic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

public class TestTextStatistic {

    static TextStatistic textStatisticUS;
    static TextStatistic textStatisticRU;
    static TextStatistic textStatisticJP;

    @BeforeClass
    public static void beforeClass() {
        textStatisticUS = new TextStatistic(Locale.US, "I've won $10 at 05/20/15");
        textStatisticRU = new TextStatistic(new Locale.Builder().setLanguage("ru").build(), "Я выйграл ₽10 20.05.15.");
        textStatisticJP = new TextStatistic(Locale.JAPAN, "15/05/20に¥10を獲得しました");
    }

    @Test
    public void testUSProcess() {
        String inputFileName = "src/ru/ifmo/ivshin/i18n/test/io/en.txt";
        String reportFileName = "src/ru/ifmo/ivshin/i18n/test/io/reportEN_t.html";
        String inputLocale = "English (United States)";
        String outputLocale = "Russian";
        Main.main(new String[]{inputLocale, outputLocale, inputFileName, reportFileName});
        String textT;
        try {
            textT = Files.readString(Paths.get("src/ru/ifmo/ivshin/i18n/test/io/reportEN_t.html"));
        } catch (IOException e) {
            System.err.println("Failed to read/process input file: " + e.getMessage());
            return;
        } catch (InvalidPathException e) {
            System.err.println("Incorrect input file path: " + e.getMessage());
            return;
        }
        String textE;
        try {
            textE = Files.readString(Paths.get("src/ru/ifmo/ivshin/i18n/test/io/reportEN.html"));
        } catch (IOException e) {
            System.err.println("Failed to read/process input file: " + e.getMessage());
            return;
        } catch (InvalidPathException e) {
            System.err.println("Incorrect input file path: " + e.getMessage());
            return;
        }

        assertEquals(textT, textE);
    }

    @Test
    public void testUSRead() {
        String reportFileName = "src/ru/ifmo/ivshin/i18n/test/io/reportEN_t.html";
        new StatisticPrinter(reportFileName).print(textStatisticUS.getStatisticReport(),
                "src/ru/ifmo/ivshin/i18n/test/io/en.txt",
                ResourceBundle.getBundle("ru.ifmo.ivshin.i18n.text_ru"));
        String textT;
        try {
            textT = Files.readString(Paths.get(reportFileName));
        } catch (IOException e) {
            System.err.println("Failed to read/process input file: " + e.getMessage());
            return;
        } catch (InvalidPathException e) {
            System.err.println("Incorrect input file path: " + e.getMessage());
            return;
        }
        String textE;
        try {
            textE = Files.readString(Paths.get("src/ru/ifmo/ivshin/i18n/test/io/reportEN.html"));
        } catch (IOException e) {
            System.err.println("Failed to read/process input file: " + e.getMessage());
            return;
        } catch (InvalidPathException e) {
            System.err.println("Incorrect input file path: " + e.getMessage());
            return;
        }
        assertEquals(textT, textE);
    }

    @Test
    public void testRUProcess() {
        String inputFileName = "src/ru/ifmo/ivshin/i18n/test/io/ru.txt";
        String reportFileName = "src/ru/ifmo/ivshin/i18n/test/io/reportRU_t.html";
        String inputLocale = "Russian";
        String outputLocale = "Russian";
        Main.main(new String[]{inputLocale, outputLocale, inputFileName, reportFileName});
        String textT;
        try {
            textT = Files.readString(Paths.get("src/ru/ifmo/ivshin/i18n/test/io/reportRU_t.html"));
        } catch (IOException e) {
            System.err.println("Failed to read/process input file: " + e.getMessage());
            return;
        } catch (InvalidPathException e) {
            System.err.println("Incorrect input file path: " + e.getMessage());
            return;
        }
        String textE;
        try {
            textE = Files.readString(Paths.get("src/ru/ifmo/ivshin/i18n/test/io/reportRU.html"));
        } catch (IOException e) {
            System.err.println("Failed to read/process input file: " + e.getMessage());
            return;
        } catch (InvalidPathException e) {
            System.err.println("Incorrect input file path: " + e.getMessage());
            return;
        }

        assertEquals(textT, textE);
    }

    @Test
    public void testRURead() {
        String reportFileName = "src/ru/ifmo/ivshin/i18n/test/io/reportRU_t.html";
        new StatisticPrinter(reportFileName).print(textStatisticRU.getStatisticReport(),
                "src/ru/ifmo/ivshin/i18n/test/io/ru.txt",
                ResourceBundle.getBundle("ru.ifmo.ivshin.i18n.text_ru"));
        String textT;
        try {
            textT = Files.readString(Paths.get(reportFileName));
        } catch (IOException e) {
            System.err.println("Failed to read/process input file: " + e.getMessage());
            return;
        } catch (InvalidPathException e) {
            System.err.println("Incorrect input file path: " + e.getMessage());
            return;
        }
        String textE;
        try {
            textE = Files.readString(Paths.get("src/ru/ifmo/ivshin/i18n/test/io/reportRU.html"));
        } catch (IOException e) {
            System.err.println("Failed to read/process input file: " + e.getMessage());
            return;
        } catch (InvalidPathException e) {
            System.err.println("Incorrect input file path: " + e.getMessage());
            return;
        }
        assertEquals(textT, textE);
    }

    @Test
    public void testJPProcess() {
        String inputFileName = "src/ru/ifmo/ivshin/i18n/test/io/jp.txt";
        String reportFileName = "src/ru/ifmo/ivshin/i18n/test/io/reportJP_t.html";
        String inputLocale = "Japanese";
        String outputLocale = "Russian";
        Main.main(new String[]{inputLocale, outputLocale, inputFileName, reportFileName});
        String textT;
        try {
            textT = Files.readString(Paths.get("src/ru/ifmo/ivshin/i18n/test/io/reportJP_t.html"));
        } catch (IOException e) {
            System.err.println("Failed to read/process input file: " + e.getMessage());
            return;
        } catch (InvalidPathException e) {
            System.err.println("Incorrect input file path: " + e.getMessage());
            return;
        }
        String textE;
        try {
            textE = Files.readString(Paths.get("src/ru/ifmo/ivshin/i18n/test/io/reportJP.html"));
        } catch (IOException e) {
            System.err.println("Failed to read/process input file: " + e.getMessage());
            return;
        } catch (InvalidPathException e) {
            System.err.println("Incorrect input file path: " + e.getMessage());
            return;
        }

        assertEquals(textT, textE);
    }

    @Test
    public void testJPRead() {
        String reportFileName = "src/ru/ifmo/ivshin/i18n/test/io/reportJP_t.html";
        new StatisticPrinter(reportFileName).print(textStatisticJP.getStatisticReport(),
                "src/ru/ifmo/ivshin/i18n/test/io/jp.txt",
                ResourceBundle.getBundle("ru.ifmo.ivshin.i18n.text_ru"));
        String textT;
        try {
            textT = Files.readString(Paths.get(reportFileName));
        } catch (IOException e) {
            System.err.println("Failed to read/process input file: " + e.getMessage());
            return;
        } catch (InvalidPathException e) {
            System.err.println("Incorrect input file path: " + e.getMessage());
            return;
        }
        String textE;
        try {
            textE = Files.readString(Paths.get("src/ru/ifmo/ivshin/i18n/test/io/reportJP.html"));
        } catch (IOException e) {
            System.err.println("Failed to read/process input file: " + e.getMessage());
            return;
        } catch (InvalidPathException e) {
            System.err.println("Incorrect input file path: " + e.getMessage());
            return;
        }
        assertEquals(textT, textE);
    }
}
