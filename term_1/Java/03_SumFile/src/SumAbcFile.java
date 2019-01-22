import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;

public class SumAbcFile {
    public static void main(String[] args) throws IOException {

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(args[0]),
                        StandardCharsets.UTF_8))) {

            try (PrintWriter out = new PrintWriter(args[1], "UTF-8")) {
                int res = 0;
                while (reader.ready()) {
                    for (String s : reader.readLine().toLowerCase().split("[^\\p{L}\\p{Pd}]")) {
                        if (!s.isEmpty()) {
                            StringBuilder curNumber = new StringBuilder();
                            for (char ch : s.toCharArray()) {
                                if (Character.toString(ch).matches("[^a-j-]")) {
                                    throw new InputMismatchException();
                                }
                                if (Character.toString(ch).matches("[\\p{Pd}]")) {
                                    curNumber.append('-');
                                    continue;
                                }
                                curNumber.append(ch - 'a');
                            }
                            res += Integer.parseInt(curNumber.toString());
                        }
                    }
                }
                out.println(res);
            } catch (InputMismatchException ex) {
                System.out.println("pls, use chars from \'a\' to \'j\' otherwise it wouldn't work UwU");
                System.exit(1);
            } catch (NumberFormatException ex) {
                System.out.println("sorry, but program can't contain number you entered, use integers up to 2 billion");
                System.exit(1);
            } catch (IOException ex) {
                System.out.println("sorry, but program can't output result \n Try to check args correctness");
                System.exit(1);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("sorry, but program can't find the input file OwO");
            System.exit(1);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("pls, enter args correctly (Example: \"input.txt\" \"output.txt\")");
            System.exit(1);
        }
        System.out.println();
    }
}


/*
Catch (Exception ex) {
    System.out.println(ex);
}
*/

// current

//out of memory Err ~2_147_483_647 elements, so ... int32 < 5_000_000

// IllegalStateException - bug in code, usually
// NoSuchElementException - out of input data

/*
if (!s.isEmpty()) {
                            int coefficient = (s.charAt(0) == '-') ? -1 : 1;
                            for (int i = s.length() - 1; i >= 0; i--) {
                                if (s.charAt(i) == '-') {
                                    break;
                                }
                                if (!(s.charAt(i) <= 'j' && s.charAt(i) >= 'a')) {
                                    throw new InputMismatchException();
                                }
                                res += (s.charAt(i) - 'a') * coefficient;
                                coefficient *= 10;
 */