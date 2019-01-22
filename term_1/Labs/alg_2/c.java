import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class c {

    public static void main(String[] args) {
        FastScanner reader = new FastScanner();
        int n = reader.nextInt();
        Q queue = new Q();
        for (int i = 0; i < n; i++) {
            switch (reader.nextInt()) {
                case 1: {
                    queue.push(reader.nextInt());
                    break;
                }
                case 2: {
                    queue.pop();
                    break;
                }
                case 3: {
                    queue.ustal();
                    break;
                }
                case 4: {
                    System.out.println(queue.many(reader.nextInt()));
                    break;
                }
                case 5: {
                    System.out.println(queue.lazy());
                    break;
                }
            }
        }
    }

    static class Q {

        int tail;
        int head;
        int[] body = new int[100001];

        Q() {
            this.tail = 0;
            this.head = 0;
        }

        boolean empty(){
            return head==tail;
        }

        void push(int x) {
            body[tail++] = x;
        }

        int pop() {
            if (empty()) {
                return -1;
            }
            return body[head++];
        }

        int size(){
            return tail-head;
        }

        int many(int x) {
            for (int i = head; i<tail;i++) {
                if (body[i] == x) {
                    x = i;
                    break;
                }
            }
            return x-head;
        }

        int lazy() {
            return body[head];
        }

        void ustal (){
            tail--;
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
