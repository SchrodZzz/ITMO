import java.util.*;

public class ReverseTranspose {
  public static void main (String[] args) {

    Scanner in = new Scanner(System.in);
    ArrayList<String> lines = new ArrayList<>();

    while (in.hasNextLine()) {
      short c = 0;
      for (String s : in.nextLine().split("\\s")) {
        if (lines.size() <= c) lines.add("");
        if (!s.isEmpty()) lines.set(c, lines.get(c) + s + " ");
        c++;
      }
    }

    for (int i = 0; i<lines.size(); i++) {
      System.out.print(lines.get(i) + "\n");
    }
  }
}
