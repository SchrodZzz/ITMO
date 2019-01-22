import java.io.IOException;
import java.util.ArrayList;

public class ReverseMin {
    public static void main(String[] args) throws IOException {

        FastScanner reader = new FastScanner(System.in);
        ArrayList <Integer> minInRow = new ArrayList <>();
        ArrayList <Integer> minInColumn = new ArrayList <>();
        ArrayList <ArrayList <Integer>> matrix = new ArrayList <>();

        while (reader.hasNextLine()) {
            ArrayList <Integer> curLine = new ArrayList <>();
            minInRow.add(Integer.MAX_VALUE);
            int columnPointer = 0;
            for (String s : reader.nextLine().split("\\s")) {
                int curNum;
                try {
                    curNum = Integer.parseInt(s);
                    curLine.add(curNum);
                    if (minInColumn.size() <= columnPointer) {
                        minInColumn.add(Integer.MAX_VALUE);
                    }
                    if (minInColumn.get(columnPointer) > curNum) {
                        minInColumn.set(columnPointer, curNum);
                    }
                    if (minInRow.get(minInRow.size() - 1) > curNum) {
                        minInRow.set(minInRow.size() - 1, curNum);
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
                if (minInColumn.get(j) > minInRow.get(i)) {
                    System.out.print(minInRow.get(i) + " ");
                } else {
                    System.out.print(minInColumn.get(j) + " ");
                }
            }
            System.out.print("\n");
        }

    }
}