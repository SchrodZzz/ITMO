/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 11.2018
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class coding_b {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("mtf.in"), "UTF-8"));
        String word = reader.readLine().toLowerCase();
        reader.close();
        StringBuilder letters = new StringBuilder("abcdefghijklmnopqrstuvwxyz");
        StringBuilder answer = new StringBuilder();
        for(char currentSymbol : word.toCharArray()){
            int k = letters.indexOf("" + currentSymbol)+1;
            answer.append(k+" ");
            letters = letters.deleteCharAt(k-1).insert(0, currentSymbol);
        }
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream("mtf.out"), "UTF-8"));
        writer.write(answer.toString());
        writer.close();
    }
}
