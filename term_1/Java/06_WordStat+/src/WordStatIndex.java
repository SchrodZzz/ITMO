import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class WordStatIndex {
    public static void main(String[] args) throws IOException {

        Map <String, ArrayList <Integer>> wordStatistic
                = new LinkedHashMap <>();

        try (FastScanner reader = new FastScanner(
                new FileInputStream(args[0]), "UTF-8")) {

            int wordPositionCounter = 0;
            while (reader.hasNext()) {
                while (reader.onLine()) {
                    String currentWord = reader.nextWord();
                    if (!currentWord.isEmpty()) {
                        wordPositionCounter++;
                        if (!wordStatistic.containsKey(currentWord)) {
                            wordStatistic.put(currentWord, new ArrayList <>());
                        }
                        wordStatistic.get(currentWord).add(wordPositionCounter);
                    }
                }
            }

            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(args[1]),
                            "UTF-8"))) {

                for (Map.Entry <String, ArrayList <Integer>> currentWordStatisticPair
                        : wordStatistic.entrySet()) {

                    writer.write(currentWordStatisticPair.getKey() + " ");
                    writer.write(currentWordStatisticPair.getValue().size() + "");

                    for (Integer currentWordStatistic
                            : currentWordStatisticPair.getValue()) {

                        writer.write(" " + currentWordStatistic);
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