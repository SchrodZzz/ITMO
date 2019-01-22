package queue;

public class QueueTest {
    private static void fill(Queue queue) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
    }

    private static void dump(Queue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size()
                    + " " + queue.element()
                    + " " + queue.dequeue());
        }
    }

    private static void test(Queue queue) {
        fill(queue);
        dump(queue);
        System.out.println("<---------->");
    }

    public static void main(String[] args) {
        test(new ArrayQueue());
        test(new LinkedQueue());
    }
}