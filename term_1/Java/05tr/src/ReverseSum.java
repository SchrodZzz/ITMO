import java.io.IOException;
import java.util.ArrayList;

public class ReverseSum {
    public static void main(String[] args) throws IOException {

        FastScanner reader = new FastScanner(System.in);
        ArrayList <ArrayList <Integer>> matrix = new ArrayList <>();
        ArrayList <Integer> sumInRow = new ArrayList <>();
        ArrayList <Integer> sumInColumn = new ArrayList <>();

        while (reader.hasNextLine()) {
            ArrayList <Integer> curLine = new ArrayList <>();
            sumInRow.add(0);
            int columnPointer = 0;
            for (String s : reader.nextLine().split("[^0-9.-]")) {
                try {
                    int curNum = Integer.parseInt(s);
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
