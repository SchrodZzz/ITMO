package search;

public class BinarySearchMissing {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Enter numbers in argument line");
            return;
        }

        int x = Integer.parseInt(args[0]);
        int[] array = new int[args.length - 1];
        for (int i = 1; i < args.length; ++i) {
            array[i - 1] = Integer.parseInt(args[i]);
        }

        int index = BinarySearchMethods.find(array, -1, array.length, x, true);
        if (index == array.length || array[index] != x) {
            index = -index - 1;
        }
        System.out.println(index);
    }
}