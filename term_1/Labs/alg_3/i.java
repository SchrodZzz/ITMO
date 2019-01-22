import java.util.Scanner;

public class i {
 private static int n, m;
 private static int[][] maskTable = new int[14][14];
 private static long[][] dp = new long[14][16000];

 public static void main(String[] args) {
 Scanner reader = new Scanner(System.in);
 n = reader.nextInt();
 m = reader.nextInt();
 String readString;
 int currentLinePos;
 for (int i = -1; i < n; i++) {
 currentLinePos = 0;
 readString = reader.nextLine();
 for (char tmp : readString.toCharArray()) {
 if (tmp == '.') {
 maskTable[i][currentLinePos++] = 0;
 } else {
 maskTable[i][currentLinePos++] = 1;
 }
 }
 }
 dp[0][0] = 1;
 for (int i = 0; i < n; i++) {
 for (int j = 0; j < (1 << m); j++) {
 fillNext(i, 0, j, 0);
 }
 }
 System.out.println(dp[n][0]);
 }

 private static void fillNext(int x, int y, int currentMask, int nextMask) {
 if (x == n) {
 return;
 }
 if (y >= m) {
 dp[x + 1][nextMask] += dp[x][currentMask];
 return;
 }
 int nextLine = 1 << y;
 if (maskTable[x][y] == 1) {
 fillNext(x, y + 1, currentMask, nextMask);
 return;
 }
 if ((currentMask & nextLine) != 0) {
 fillNext(x, y + 1, currentMask, nextMask);
 return;
 }
 if (y + 1 < m && maskTable[x][y] == 0 && maskTable[x][y + 1] == 0 && (0 == (currentMask & (nextLine << 1)))) {
 fillNext(x, y + 2, currentMask, nextMask);
 }
 if (maskTable[x][y] == 0 && maskTable[x + 1][y] == 0) {
 fillNext(x, y + 1, currentMask, (nextMask | nextLine));
 }

 }
}
