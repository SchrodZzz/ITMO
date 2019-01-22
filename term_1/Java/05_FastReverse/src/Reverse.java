import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Reverse {
    public static void main(String[] args) throws IOException {

        FastScanner reader = new FastScanner(System.in);
        ArrayList <ArrayList <Integer>> matrix = new ArrayList <>();

        while (reader.fastHasNext()) {
            ArrayList <Integer> curLine = new ArrayList <>();
            while (!reader.fastLineEnd()) {
                try {
                    curLine.add(reader.fastNextInt());
                } catch (NumberFormatException ex) {}
            }
            Collections.reverse(curLine);
            matrix.add(curLine);
        }
        reader.close();

        Collections.reverse(matrix);

        for (ArrayList <Integer> line : matrix) {
            for (Integer num : line) {
                System.out.print(num + " ");
            }
            System.out.print("\n");
        }
    }
}





















/*
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class Reverse {
    public static void main(String[] args) throws IOException {

        InputStream in = System.in;
        ArrayList <ArrayList <Integer>> matrix = new ArrayList <>();
        int cur;
        while ((cur = in.read()) != -1) {
            ArrayList <Integer> curList = new ArrayList <>();
            while (cur > 31) {
                if (!(cur > 47 && cur < 58 || cur == 45)) cur = in.read();
                StringBuilder curNum = new StringBuilder();
                while (cur > 47 && cur < 58 || cur == 45) {
                    curNum.append((char) cur);
                    cur = in.read();
                }
                if (!(curNum.toString().isEmpty())) curList.add(Integer.parseInt(curNum.toString()));
            }
            Collections.reverse(curList);
            matrix.add(curList);
        }
        Collections.reverse(matrix);
        for (ArrayList <Integer> curList : matrix) {
            for (Integer num : curList) {
                System.out.print(num + " ");
            }
            System.out.print("\n");
        }
    }
}
 */


/*
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Reverse {
    public static void main(String[] args) throws IOException {
        FastScanner reader = new FastScanner(new FileInputStream(args[0]));
        ArrayList <String> lines = new ArrayList <>();
        while (reader.fastHasNext()) {
            lines.add(reader.fastNextWord());
        }
        for (String s : lines) {
            System.out.println("->   " + s);
        }
    }
}
 */