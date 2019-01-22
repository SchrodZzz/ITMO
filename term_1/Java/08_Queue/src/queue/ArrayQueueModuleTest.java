package queue;

public class ArrayQueueModuleTest {
    private static void fill() {
        for (int i = 5; i >= 0; i--) {
            ArrayQueueModule.enqueue(i);
        }
        System.out.println("Queue has been filled - current size: " + ArrayQueueModule.size());
        border();
    }

    private static void dump() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(
                    ArrayQueueModule.size() + " " +
                            ArrayQueueModule.element() + " " +
                            ArrayQueueModule.dequeue()
            );
        }
        System.out.println("Queue is empty");
        border();
    }

    private static void deleteAllElements() {
        ArrayQueueModule.clear();
        System.out.println("All elements in queue has been deleted");
        border();
    }

    private static void border() {
        System.out.println("<-------->");
    }

    public static void main(String[] args) {
        border();
        fill();
        dump();
        fill();
        deleteAllElements();
        dump();
    }
}