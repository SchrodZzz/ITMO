package queue;

//INV: size >= 0 && elements[i] != NULL ∀ i: [head..tail)
public class ArrayQueueModule {

    private static final int DEFAULT_QUEUE_SIZE = 2;

    private static int head = 0, tail = 0, size = 0;
    private static Object[] elements = new Object[DEFAULT_QUEUE_SIZE];

    //PRE: element != null
    //POST: size = size' + 1 && elements[size - 1] = element && elements[i] = elements[i]', ∀ i: [0..size')
    public static void enqueue(final Object value) {
        assert (value != null) : "Null value found.";

        if (size >= elements.length) {
            elements = UtilityMethods.ensureCapacity(head, tail, elements);
            head = 0;
            tail = size;
        }
        elements[tail] = value;
        tail = (tail + 1) % elements.length;
        ++size;
    }

    //PRE: size > 0
    //POST: size = size' && elements[i] = elements[i]', ∀ i: [0..size) && ℝ = elements[0]
    public static Object element() {
        assert (size > 0) : "No elements found.";

        return elements[head];
    }

    //PRE: size > 0
    //POST: size = size' - 1 && ℝ = elements[0]' && elements[i] = elements[i+1]', ∀ i: [0..size)
    public static Object dequeue() {
        assert (size > 0) : "No elements found.";

        Object element = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        --size;
        return element;
    }

    //POST: size = size' && elements[i] = elements[i]', ∀ i: [0..size) && ℝ = size
    public static int size() {
        return size;
    }

    //POST: size = size' && elements[i] = elements[i]', ∀ i: [0..size) && ℝ = (size == 0)
    public static boolean isEmpty() {
        return size == 0;
    }

    //POST: size = 0
    public static void clear() {
        elements = new Object[DEFAULT_QUEUE_SIZE];
        head = tail = size = 0;
    }

    //PRE: element != null
    //POST: size = size' + 1 && elements[0] = element && elements[i] = elements[i-1]', ∀ i: [0..size')
    public static void push(Object value) {
        assert (value != null) : "Null value found.";

        if (size >= elements.length) {
            elements = UtilityMethods.ensureCapacity(head, tail, elements);
            head = 0;
            tail = size;
        }
        head -= 1;
        if (head < 0) {
            head = elements.length - 1;
        }
        elements[head] = value;
        size += 1;
    }

    //PRE: size > 0
    //POST: size = size' && elements[i] = elements[i]', ∀ i: [0..size) && ℝ = elements[size - 1]
    public static Object peek() {
        assert (size > 0) : "No elements found";

        int position = tail - 1;
        if (position < 0) {
            position = elements.length - 1;
        }
        return elements[position];
    }

    //PRE: size > 0
    //POST: size = size' - 1 && ℝ = elements'[size] && elements[i] = elements[i]', ∀ i: [0..size)
    public static Object remove() {
        assert (size > 0) : " No elements found";

        tail -= 1;
        if (tail < 0) {
            tail = elements.length - 1;
        }
        Object returnValue = elements[tail];
        elements[tail] = null;
        size -= 1;
        return returnValue;
    }

    //POST: size = size' && elements[i] = elements[i]' && newElements[i] = elements[i]', ∀ i: [0..size)
    // && ℝ = newElements[]
    public static Object[] toArray() {
        return UtilityMethods.toArrayImpl(head, tail, size, elements);
    }
}