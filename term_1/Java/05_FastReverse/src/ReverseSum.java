import java.io.IOException;
import java.util.ArrayList;

public class ReverseSum {
    public static void main(String[] args) throws IOException {

        FastScanner reader = new FastScanner(System.in);
        ArrayList <ArrayList <Integer>> matrix = new ArrayList <>();
        ArrayList <Integer> sumInRow = new ArrayList <>();
        ArrayList <Integer> sumInColumn = new ArrayList <>();

        while (reader.fastHasNext()) {
            ArrayList <Integer> curLine = new ArrayList <>();
            sumInRow.add(0);
            int columnPointer = 0;
            while (!reader.fastLineEnd()) {
                int curNum;
                try {
                    curNum = reader.fastNextInt();
                    curLine.add(curNum);
                    sumInRow.set(sumInRow.size() - 1, sumInRow.get(sumInRow.size() - 1) + curNum);
                    if (sumInColumn.size() > columnPointer) {
                        sumInColumn.set(columnPointer, sumInColumn.get(columnPointer) + curNum);
                    } else {
                        sumInColumn.add(curNum);
                    }
                    columnPointer += 1;
                } catch (NumberFormatException ex) {
                    break;
                }
            }
            matrix.add(curLine);
        }
        reader.close();

        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                System.out.print(sumInRow.get(i) + sumInColumn.get(j) - matrix.get(i).get(j) + " ");
            }
            System.out.print("\n");
        }

    }
}
























/*
import java.util.*;
import java.lang.*;
import java.io.*;
public class ReverseSum {
    public static void main(String [] args) {
    FastScanner in = new FastScanner(System.in);
    PrintWriter out = new PrintWriter (System.out);

    List <ArrayList<Integer>> a = new ArrayList <ArrayList<Integer>>();
    List <Integer> c = new ArrayList <Integer>();
    List <Integer> d = new ArrayList <Integer>();

    String s;
    while ((s = in.nextLine()) != null) {
        a.add(new ArrayList <Integer>());
        String[] t = s.split("\\p{javaWhitespace}+");
        c.add(0);
        for (int i = 0; i < t.length; ++i)
            if (t[i].length() > 0) {
                int cur = new Integer(t[i]);
                a.get(a.size() - 1).add(cur);
                c.set(c.size() - 1, c.get(c.size() - 1) + cur);
                if (i >= d.size())
                   d.add(0);
                d.set(i, d.get(i) + cur);
            }
    }

    for (int i = 0; i < a.size(); ++i) {
        for (int j = 0; j < a.get(i).size(); ++j) {
            out.print(c.get(i) + d.get(j) - a.get(i).get(j) + " ");
        }
        out.println();
    }
    out.close();
  }

  static class FastScanner {
      private BufferedReader in;
      public FastScanner(InputStream stream) {
          in = new BufferedReader(new InputStreamReader(stream));
      }

      String nextLine() {
          try {
              return in.readLine();
          }
          catch (IOException e) {
              throw new RuntimeException();
          }
      }
  }
}
 */