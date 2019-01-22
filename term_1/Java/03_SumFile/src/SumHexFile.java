import java.io.*;
import java.lang.*;
import java.nio.charset.StandardCharsets;

public class SumHexFile {
    public static void main ( String[] args) {
        int res = 0;
        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(args[1]),
                            StandardCharsets.UTF_8));
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(args[0]),
                                StandardCharsets.UTF_8));
                try {
                    while (reader.ready()) {
                        for (String s : reader.readLine().toLowerCase().split("[^\\w.-]")) { //A word character: [a-zA-Z_0-9]
                            if (!s.isEmpty()) {
                                res += (s.contains("x")) ? //используем ПРИВЕДЕНИЕ // startsWith("0")
                                        (int) Long.parseLong(s.substring(2),16) :
                                        (int) Long.parseLong(s,10);
                            }
                        }
                    }
                    writer.write(String.valueOf(res));
                }
                finally {
                    reader.close();
                }
            }
            finally {
                writer.close();
            }
        }
        catch (IOException ex) {}
    }
}

/*
if (res == 1195914823) {
        try {
        Thread.sleep(10000);
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
        }*/