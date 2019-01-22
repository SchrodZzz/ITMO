public class Sum {
    public static void main(String[] args) {
        int result = 0;
        for (String currentString : args) {
            for (String currentDigit : currentString.split("[^\\p{Pd}\\p{Digit}]")) {
                if (!currentDigit.isEmpty()) {
                    result += Integer.parseInt(currentDigit);
                }
            }
        }
        System.out.println(result);
    }
}
