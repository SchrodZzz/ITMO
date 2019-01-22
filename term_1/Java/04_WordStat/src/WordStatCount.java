/*import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;

public class WordStatCount {
    public static void main(String[] args) throws IOException {

        LinkedHashMap <String, Integer> wordsCountHashMap = new LinkedHashMap <>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(args[0]),
                        StandardCharsets.UTF_8))){
            try {
                while (reader.ready()) {
                    for (String s : reader.readLine().toLowerCase().split("[^\\p{Pd}\\p{L}']")) { //Latter and PunctuationDash
                        if (!s.isEmpty()) {
                            if (wordsCountHashMap.containsKey(s)) {
                                wordsCountHashMap.replace(s, wordsCountHashMap.get(s) + 1);
                            } else wordsCountHashMap.put(s, 1);
                        }
                    }
                }
            } catch (InputMismatchException ex) {
                System.out.println("InputMismatchException");
                System.exit(1);
            } catch (IllegalStateException ex) {
                System.out.println("IllegalStateException");
                System.exit(1);
            } catch (NoSuchElementException ex) {
                System.out.println("NoSuchElementException");
                System.exit(1);
            } finally {
                try {
                    PrintWriter out = new PrintWriter(args[1], "UTF-8");
                    List <Entry <String, Integer>> sortedData = new ArrayList <>(wordsCountHashMap.entrySet());
                    sortedData.sort(Entry.comparingByValue());
                    for (Map.Entry <String, Integer> pairs : sortedData) {
                        out.println(pairs.getKey() + " " + pairs.getValue());
                    }
                    out.close();
                } catch (IOException ex) {
                    System.out.println("Got problem while writing the result number");
                    System.exit(1);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
            System.exit(1);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("ArrayIndexOutOfBoundsException");
            System.exit(1);
        }
    }
}

//https://www.regular-expressions.info/unicode.html





/*import java.io.*;
import java.util.*;
import java.util.Map.*;
public class WordStatCount {
    public static void main ( String[] args) throws IOException {

        LinkedHashMap < String, Integer > words= new LinkedHashMap<>();// жизнь - боль

        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(args[1]),
                        "utf-8"));
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(args[0]),
                            "utf-8"));
            try {
                while (reader.ready()) {
                    for (String s : reader.readLine().toLowerCase().split("[^\\p{Pd}\\p{L}']")) { //Latter and PunctuationDash
                        if (!s.isEmpty()) {
                            if (words.containsKey(s)) words.replace(s, words.get(s) + 1);
                            else words.put(s, 1);
                        }
                    }
                }
                List<Entry<String,Integer>> list = new ArrayList<>(words.entrySet());
                list.sort(Entry.comparingByValue());
                for (Map.Entry<String,Integer> pairs: list) {
                    writer.write(pairs.getKey() + " " + pairs.getValue() + "\n");
                }
            }
            finally {
                reader.close();
            }
        }
        finally {
            writer.close();
        }
    }
}*/
