import java.util.*;

public class Reverse {
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        ArrayList <String> digits = new ArrayList <>();

        while (reader.hasNextLine()) {
            for (String currentDigit : reader.nextLine().split("[^\\p{Pd}\\p{Digit}]")) {
                digits.add(currentDigit + " ");
            }
            digits.add("\n");
        }
        for (int i = digits.size() - 2; i >= 0; i--) {
            System.out.print(digits.get(i));
        }
    }
}
