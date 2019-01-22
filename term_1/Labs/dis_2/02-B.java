/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 11.2018
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class coding_b {
    public static void main (String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("bwt.in"),"UTF-8"));
        StringBuilder word = new StringBuilder(reader.readLine());
        reader.close();
        ArrayList<String> permutations = new ArrayList <>();
        permutations.add(word.toString());
        for (int i = 1; i < word.length(); i++) {
            word.append(word.charAt(0));
            word.delete(0,1);
            permutations.add(word.toString());
        }
        Collections.sort(permutations);
        StringBuilder answer = new StringBuilder();
        for (String currentPermutation: permutations) {
            answer.append(currentPermutation.charAt(word.length()-1));
        }
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream("bwt.out"),"UTF-8"));
        writer.write(answer.toString());
        writer.close();
    }
}
