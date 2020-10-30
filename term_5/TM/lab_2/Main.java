import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    // MARK: Lifecycle

    public static void main(String[] args) {
        try (FastScanner fs = new FastScanner(false, "tree")) {
            String data = fs.nextLine();
            Parser parser = new Parser('\t');
            parser.process(data);
            fs.printStr(arrayToString(parser.getTreeImage()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    // MARK: Private

    private static String arrayToString(ArrayList<String> array) {
        StringBuilder sb = new StringBuilder();
        for (String cur : array) {
            sb.append(cur).append('\n');
        }
        return sb.toString();
    }


    // MARK: - Inner classes

    static class FastScanner implements AutoCloseable {
        final BufferedReader br;
        final PrintWriter pw;
        StringTokenizer st;

        boolean isSysRead;

        public FastScanner(boolean isSysRead, String fileName) throws IOException {
            this.isSysRead = isSysRead;
            br = new BufferedReader(new InputStreamReader(System.in));
            pw = new PrintWriter("term_5/TM/lab_2/" + fileName + ".out");
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }

        void printStr(Object str) {
            if (isSysRead) {
                System.out.println(String.valueOf(str));
            } else {
                pw.println(String.valueOf(str));
            }
        }

        public void close() throws IOException {
            if (!isSysRead) {
                pw.close();
            }
            br.close();
        }
    }
}
