package queue;

public class ArrayQueueADTTest {
    private static void fill(ArrayQueueADT queue) {
        for (int i = 7; i >= 0; i--) {
            ArrayQueueADT.enqueue(queue, i);
        }
        System.out.println("Queue has been filled - current size: " + ArrayQueueADT.size(queue));
        border();
    }

    private static void dump(ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(
                    ArrayQueueADT.size(queue) + " " +
                            ArrayQueueADT.element(queue) + " " +
                            ArrayQueueADT.dequeue(queue)
            );
        }
        System.out.println("Queue is empty");
        border();
    }

    private static void deleteAllElements(ArrayQueueADT queue) {
        ArrayQueueADT.clear(queue);
        System.out.println("All elements in queue has been deleted");
        border();
    }

    private static void border() {
        System.out.println("<-------->");
    }

    public static void main(String[] args) {
        ArrayQueueADT queue = new ArrayQueueADT();
        border();
        fill(queue);
        dump(queue);
        fill(queue);
        deleteAllElements(queue);
        dump(queue);
    }
}
