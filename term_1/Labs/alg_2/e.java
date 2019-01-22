import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class e {

    public static void main(String[] args) {
        FastScanner reader = new FastScanner();
        Stackky stack = new Stackky();
        String[] eevryNyan = reader.nextLine().split(" ");
        for (int i = 0; i < eevryNyan.length; i++) {
            int tmp;
            if (Character.isDigit(eevryNyan[i].charAt(0))) {
                tmp = Integer.parseInt(eevryNyan[i]);
                stack.push(tmp);
            } else {
                int frst = stack.pop();
                int scnd = stack.pop();
                if (Objects.equals(eevryNyan[i], "+")) {
                    tmp = scnd + frst;
                    stack.push(tmp);
                } else if (Objects.equals(eevryNyan[i], "-")) {
                    tmp = scnd - frst;
                    stack.push(tmp);
                } else if (Objects.equals(eevryNyan[i], "*")) {
                    tmp = scnd * frst;
                    stack.push(tmp);
                }
            }
        }
        System.out.println(stack.pop());
    }

    static class Stackky {

        private int[] s = new int[1000];
        private int top = -1;

        boolean isEmpty() {
            return (top == -1);
        }

        void push(int i) {
            s[++top] = i;
        }

        int pop() {
            if (isEmpty())
                return ('-');
            else {
                return s[top--];
            }
        }

    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
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
    }
}
