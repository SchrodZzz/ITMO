package queue;

import static queue.UtilityMethods.*;

//INV: size >= 0 && elements[i] != NULL ∀ i: [head..tail)
public class ArrayQueueADT {

    private static final int DEFAULT_QUEUE_SIZE = 2;

    private int head = 0, tail = 0, size = 0;
    private Object[] elements = new Object[DEFAULT_QUEUE_SIZE];

    //PRE: element != null
    //POST: size = size' + 1 && elements[size - 1] = element && elements[i] = elements[i]', ∀ i: [0..size')
    public static void enqueue(ArrayQueueADT queue, final Object value) {
        assert (value != null) : "Null value found.";

        if (queue.size >= queue.elements.length) {
            queue.elements = ensureCapacity(queue.head, queue.tail, queue.elements);
            queue.head = 0;
            queue.tail = queue.size;
        }
        queue.elements[queue.tail] = value;
        queue.tail = (queue.tail + 1) % queue.elements.length;
        ++queue.size;
    }

    //PRE: size > 0
    //POST: size = size' && elements[i] = elements[i]', ∀ i: [0..size) && ℝ = elements[0]
    public static Object element(ArrayQueueADT queue) {
        assert (queue.size > 0) : "No elements found.";

        return queue.elements[queue.head];
    }

    //PRE: size > 0
    //POST: size = size' - 1 && ℝ = elements[0]' && elements[i] = elements[i+1]', ∀ i: [0..size)
    public static Object dequeue(ArrayQueueADT queue) {
        assert (queue.size > 0) : "No elements found.";

        Object element = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        --queue.size;
        return element;
    }

    //POST: size = size' && elements[i] = elements[i]', ∀ i: [0..size) && ℝ = size
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    //POST: size = size' && elements[i] = elements[i]', ∀ i: [0..size) && ℝ = (size == 0)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

    //POST: size = 0
    public static void clear(ArrayQueueADT queue) {
        queue.elements = new Object[DEFAULT_QUEUE_SIZE];
        queue.head = queue.tail = queue.size = 0;
    }

    //PRE: element != null
    //POST: size = size' + 1 && elements[0] = element && elements[i] = elements[i-1]', ∀ i: [0..size')
    public static void push(ArrayQueueADT queue, Object value) {
        assert (value != null) : "Null value found.";

        if (queue.size >= queue.elements.length) {
            queue.elements = ensureCapacity(queue.head, queue.tail, queue.elements);
            queue.head = 0;
            queue.tail = queue.size;
        }
        queue.head -= 1;
        if (queue.head < 0) {
            queue.head = queue.elements.length - 1;
        }
        queue.elements[queue.head] = value;
        queue.size += 1;
    }

    //PRE: size > 0
    //POST: size = size' && elements[i] = elements[i]', ∀ i: [0..size) && ℝ = elements[size - 1]
    public static Object peek(ArrayQueueADT queue) {
        assert (queue.size > 0) : "No elements found";

        int position = queue.tail - 1;
        if (position < 0) {
            position = queue.elements.length - 1;
        }
        return queue.elements[position];
    }

    //PRE: size > 0
    //POST: size = size' - 1 && ℝ = elements'[size] && elements[i] = elements[i]', ∀ i: [0..size)
    public static Object remove(ArrayQueueADT queue) {
        assert (queue.size > 0) : " No elements found";

        queue.tail -= 1;
        if (queue.tail < 0) {
            queue.tail = queue.elements.length - 1;
        }
        Object returnValue = queue.elements[queue.tail];
        queue.elements[queue.tail] = null;
        queue.size -= 1;
        return returnValue;
    }

    //POST: size = size' && elements[i] = elements[i]' && newElements[i] = elements[i]', ∀ i: [0..size)
    // && ℝ = newElements[]
    public static Object[] toArray(ArrayQueueADT queue) {
        return toArrayImpl(queue.head,queue.tail,queue.size,queue.elements);
    }
}