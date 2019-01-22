import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class f {
    public static void main (String[] args) {
        FastScanner reader = new FastScanner();
        int n = reader.nextInt();
        int[] values = new int[n];
        int[] expAns = new int[n];
        Stack<Integer> val = new Stack <>();
        ArrayList<String> ans = new ArrayList <>();
        int pivot = 0;
        for (int i = 0; i < n; i++) {
            values[i] = reader.nextInt();
            expAns[i] = values[i];
        }
        Arrays.sort(expAns);
        String a = "push";
        String b = "pop";
        val.push(-1);
        int i = -1;
        while (pivot<values.length) {
            while (val.peek() != expAns[pivot]) {
                if (++i==expAns.length) {
                    System.out.println("impossible");
                    return;
                }
                val.push(values[i]);
                //System.out.println(val.peek()+"  "+expAns[pivot]+" "+val.size());
                ans.add(a);
            }
            //System.out.println("qq");
            while (val.peek() == expAns[pivot]) {
                val.pop();
                pivot++;
                ans.add(b);
                if (pivot == expAns.length) break;
            }
        }
        for (String s : ans) {
            System.out.println(s);
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
    }
}
