import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class d {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        ArrayDeque<Integer> liftVals = new ArrayDeque<>();
        ArrayDeque<Integer> riVals = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            String cmd = reader.readLine();
            if (cmd.charAt(0) == '+') {
                liftVals.addFirst(Integer.parseInt(cmd.substring(2)));
                if (liftVals.size() > riVals.size() && liftVals.size() > 0) {
                    riVals.addFirst(liftVals.pollLast());
                }
            } else if (cmd.charAt(0) == '*') {
                if (liftVals.size() >= riVals.size()) {
                    riVals.addFirst(Integer.parseInt(cmd.substring(2)));
                } else {
                    liftVals.addLast(Integer.parseInt(cmd.substring(2)));
                }
            } else if (cmd.charAt(0) == '-') {
                if (riVals.size() == 0) riVals.addFirst(liftVals.pollLast());
                System.out.print(riVals.pollLast() + "\n");
                if (liftVals.size() > riVals.size() && liftVals.size() > 0) {
                    riVals.addFirst(liftVals.pollLast());
                }
            }
        }
    }
}

/*
7
+ 1
+ 2
-
+ 3
+ 4
-
-

 */
