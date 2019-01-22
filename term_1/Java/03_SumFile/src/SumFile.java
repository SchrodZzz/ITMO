import java.io.*;
import java.nio.charset.StandardCharsets;

public class SumFile {
    public static void main ( String[] args) {
        int res = 0;

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
                try {
                    while (reader.ready()) {
                        for (String s : reader.readLine().split("[^0-9.-]")) {
                            res += (!s.isEmpty()) ? Integer.parseInt(s) : 0;
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
