import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;

public class MainClass {
    public static void main(String[] args) {
        ArrayList <CustomPair> indexStringPairs = new ArrayList <>();
        String currentCommand = "";
        try (MyScanner commandScanner = new MyScanner(args[0])) {
            while (commandScanner.hasNextCommand()) {
                currentCommand = commandScanner.readNextCommand();
                String[] slicedCommand = currentCommand.split("[^\\p{L}\\p{Digit}\\p{Pd}]");
                String commandName = slicedCommand[0];
                try {
                    switch (commandName) {
                        case "add":
                            indexStringPairs.add(new CustomPair(getCommandIndex(slicedCommand[1]),
                                    slicedCommand[2]));
                            break;
                        case "remove":
                            for (int i = indexStringPairs.size() - 1; i >= 0; --i) {
                                if (indexStringPairs.get(i).getIndex() == getCommandIndex(slicedCommand[1])) {
                                    indexStringPairs.remove(i);
                                }
                            }
                            break;
                        case "print":
                            try (PrintWriter writer = new PrintWriter(slicedCommand[1])) {
                                indexStringPairs.sort(Comparator.comparing(p -> p.getIndex()));
                                for (CustomPair currentPair : indexStringPairs) {
                                    writer.printf("%d %s\n", currentPair.getIndex(), currentPair.getString());
                                }
                                writer.flush();
                            }
                            break;
                        default:
                            System.out.println("\"" + currentCommand + "\" - Incorrect command");
                    }
                } catch (NumberFormatException e) {
                    System.err.println(currentCommand
                            + " Incorrect index - use integers: -10^9 -- 10^9");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Program can't find file you entered, try to check file name correctness \n"
                    + "File name is implementing in args. \n"
                    + "args EX: \"InputFileName.in\"");
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Incorrect data in args \n"
                    + "args EX: \"InputFileName.in\"");
        } catch (UnsupportedEncodingException e) {
            System.err.println(currentCommand
                    + " Unsupported encoding! \n"
                    + "This program works only with UTF-8");
        } catch (IOException e) {
            System.err.println(currentCommand
                    + " Something went wrong and I didn't expected it \n"
                    + "Probably you don't have an access to create file for output");
        }
    }

    private static int getCommandIndex(String unparsedIndex) throws NumberFormatException {
        return Integer.parseInt(unparsedIndex);
    }
}