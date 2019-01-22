import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class WordStatWords {
    public static void main(String[] args) throws IOException {
        TreeMap <String, Integer> wordsAmountPairs = new TreeMap <>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(args[0]),
                        StandardCharsets.UTF_8))) {
            try (PrintWriter out = new PrintWriter(args[1], "UTF-8")) {
                while (reader.ready()) {
                    for (String s : reader.readLine().toLowerCase().split("[^\\p{Pd}\\p{L}']")) {
                        if (!s.isEmpty()) {
                            if (wordsAmountPairs.containsKey(s)) {
                                wordsAmountPairs.replace(s, wordsAmountPairs.get(s) + 1);
                            } else {
                                wordsAmountPairs.put(s, 1);
                            }
                        }
                    }
                }
                for (Map.Entry <String, Integer> pairs : wordsAmountPairs.entrySet()) {
                    out.println(pairs.getKey() + " " + pairs.getValue());
                }
            } catch (IOException ex) {
                System.out.println("sorry, but program can't output result \n Try to check args correctness");
                System.exit(1);
            } catch (OutOfMemoryError ex) {
                System.out.println("WHAT DID YOU ENTER?! It's too big!");
                System.exit(1);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("sorry, but program can't find the input file OwO");
            System.exit(1);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("pls, enter args correctly (Example: \"input.txt\" \"output.txt\")");
            System.exit(1);
        }
    }
}

// tested on 350mb txt file

//out of memory Err ~2_147_483_647 elements, so ...
// to call: long[] l = new long[Integer.MAX_VALUE];