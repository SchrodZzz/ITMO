import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class WordStatLineIndex {
    public static void main(String[] args) throws IOException {

        Map <String, ArrayList <AdditionalPair <Integer, Integer>>> wordStatistic
                = new TreeMap <>();

        try (FastScanner reader = new FastScanner(
                new FileInputStream(args[0]), "UTF-8")) {

            int lineCounter = 0;
            while (reader.hasNext()) {
                int inLineWordsCounter = 0;
                lineCounter++;
                while (reader.onLine()) {
                    String currentWord = reader.nextWord();
                    if (!currentWord.isEmpty()) {
                        inLineWordsCounter++;
                        if (!wordStatistic.containsKey(currentWord)) {
                            wordStatistic.put(currentWord, new ArrayList <>());
                        }
                        wordStatistic.get(currentWord).add(new AdditionalPair <>(lineCounter, inLineWordsCounter));
                    }
                }
            }

            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(args[1]),
                            "UTF-8"))) {

                for (Map.Entry <String, ArrayList <AdditionalPair <Integer, Integer>>> currentWordStatisticPair
                        : wordStatistic.entrySet()) {

                    writer.write(currentWordStatisticPair.getKey() + " ");
                    writer.write(currentWordStatisticPair.getValue().size() + "");

                    for (AdditionalPair <Integer, Integer> currentWordStatistic
                            : currentWordStatisticPair.getValue()) {

                        writer.write(" " + currentWordStatistic.getFirstElement());
                        writer.write(":" + currentWordStatistic.getSecondElement());
                    }

                    writer.write("\n");
                }
            } catch (IOException ex) {
                System.out.println("sorry, but program can't output result \n Try to check args correctness");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("sorry, but program can't find the input file OwO");
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("pls, enter args correctly (Example: \"input.txt\" \"output.txt\")");
        }
    }
}

// Also we can use String ArrayList instead of AdditionalPair, but AdditionalPair solution is more pretty, I think.



/*
catch (Exception e) {
            System.out.println("Usage java : <fileInputName> <fileOutPutName> ");
            System.out.println(e.getClass().getSimpleName());
        }
 */