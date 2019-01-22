import java.math.BigInteger;

public class SumBigInteger {
    public static void main(String[] args) {

        BigInteger result = BigInteger.valueOf(0);

        for (String currentString : args) {
            for (String currentNumber : currentString.split("[^\\p{Pd}\\p{Digit}]")) {
                if (!currentNumber.isEmpty()) {
                    result = result.add(new BigInteger(currentNumber));
                }
            }
        }
        System.out.print(result);
    }
}
