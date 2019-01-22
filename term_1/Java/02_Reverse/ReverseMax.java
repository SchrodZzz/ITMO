import java.util.Scanner;
import java.util.ArrayList;

public class ReverseMax {
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        ArrayList <Integer> maxInRow = new ArrayList <>();
        ArrayList <Integer> maxInColumn = new ArrayList <>();
        ArrayList <ArrayList <Integer>> digitMatrix = new ArrayList <>();

        while (reader.hasNextLine()) {
            ArrayList <Integer> currentRow = new ArrayList <>();
            maxInRow.add(Integer.MIN_VALUE);
            int columnPointer = 0;
            for (String currentStringNumber : reader.nextLine().split("[^\\p{Pd}\\p{Digit}]")) {
                if (!currentStringNumber.isEmpty()) {
                    int currentIntegerNumber = Integer.parseInt(currentStringNumber);
                    currentRow.add(currentIntegerNumber);
                    if (maxInColumn.size() <= columnPointer) {
                        maxInColumn.add(Integer.MIN_VALUE);
                    }
                    if (maxInColumn.get(columnPointer) < currentIntegerNumber) {
                        maxInColumn.set(columnPointer, currentIntegerNumber);
                    }
                    if (maxInRow.get(maxInRow.size() - 1) < currentIntegerNumber) {
                        maxInRow.set(maxInRow.size() - 1, currentIntegerNumber);
                    }
                    columnPointer += 1;
                }
            }
            digitMatrix.add(currentRow);
        }
        reader.close();

        for (int i = 0; i < digitMatrix.size(); i++) {
            for (int j = 0; j < digitMatrix.get(i).size(); j++) {
                if (maxInColumn.get(j) < maxInRow.get(i)) {
                    System.out.print(maxInRow.get(i) + " ");
                } else {
                    System.out.print(maxInColumn.get(j) + " ");
                }
            }
            System.out.print("\n");
        }

    }
}
