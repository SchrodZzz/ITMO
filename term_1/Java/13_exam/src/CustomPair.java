public class CustomPair {
    private final int index;
    private final String string;

    CustomPair(int index, String string) {
        this.index = index;
        this.string = string;
    }

    int getIndex() {
        return index;
    }

    String getString() {
        return string;
    }
}