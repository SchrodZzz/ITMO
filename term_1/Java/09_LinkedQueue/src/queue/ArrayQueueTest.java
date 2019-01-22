package queue;

public class ArrayQueueTest {
    private static void fill(ArrayQueue queue) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
    }

    private static void dump(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size()
                    + " " + queue.element()
                    + " " + queue.dequeue());
        }
    }

    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue();
        /*fill(queue);
        dump(queue);*/
        System.out.println(queue.size + " " + queue.size());
        queue.enqueue(1);
        System.out.println(queue.size + " " + queue.size());
    }
}