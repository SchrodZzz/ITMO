import java.io.*;

public class FileFiller {
    public static void main(String[] args) throws IOException {
        try (FastScanner reader = new FastScanner(
                new FileInputStream(args[0]),"UTF-8")) {
            while (reader.hasNext()) {
                while (reader.onLine()) {
                    System.out.print(reader.nextWord() + " ");
                }
                System.out.print('\n');
            }
        } catch (FileNotFoundException ex) {
            System.out.print("oops");
        } catch (IndexOutOfBoundsException ex) {
            System.out.print("invalid args");
        }
    }
}
