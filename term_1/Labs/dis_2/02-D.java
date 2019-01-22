/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 11.2018
 */

import java.io.*;
import java.util.*;

public class coding_b {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("lzw.in"), "UTF-8"));
        String word = reader.readLine().toLowerCase();
        reader.close();
        Map<String,Integer> dictionary = new HashMap<>();
        int size =0;
        for (char cur : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
            dictionary.put(String.valueOf(cur),size);
            size++;
        }
        StringBuilder answer = new StringBuilder();
        String t = "";
        for (char currentSymbol : word.toCharArray()) {
            String tc = t + currentSymbol;
            if (dictionary.containsKey(tc))
                t = tc;
            else {
                answer.append(dictionary.get(t)+" ");
                dictionary.put(tc, size++);
                t = "" + currentSymbol;
            }
        }
        answer.append(dictionary.get(t)+" ");

        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream("lzw.out"), "UTF-8"));
        writer.write(answer.toString());
        writer.close();
    }
}
