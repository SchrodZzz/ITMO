package queue;

import static queue.UtilityMethods.*;

//INV: size >= 0 && elements[i] != NULL ∀ i: [head..tail)
public class ArrayQueue {

    private static final int DEFAULT_QUEUE_SIZE = 2;

    private int head = 0, tail = 0, size = 0;
    private Object[] elements = new Object[DEFAULT_QUEUE_SIZE];

    //PRE: element != null
    //POST: size = size' + 1 && elements[size - 1] = element && elements[i] = elements[i]', ∀ i: [0..size')
    public void enqueue(final Object value) {
        assert (value != null) : "Null value found.";

        if (size >= elements.length) {
            elements = ensureCapacity(head, tail, elements);
            head = 0;
            tail = size;
        }
        elements[tail] = value;
        tail = (tail + 1) % elements.length;
        ++size;
    }

    //PRE: size > 0
    //POST: size = size' && elements[i] = elements[i]', ∀ i: [0..size) && ℝ = elements[0]
    public Object element() {
        assert (this.size > 0) : "No elements found.";

        return this.elements[head];
    }

    //PRE: size > 0
    //POST: size = size' - 1 && ℝ = elements[0]' && elements[i] = elements[i+1]', ∀ i: [0..size)
    public Object dequeue() {
        assert (size > 0) : "No elements found.";

        Object element = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        --size;
        return element;
    }

    //POST: size = size' && elements[i] = elements[i]', ∀ i: [0..size) && ℝ = size
    public int size() {
        return size;
    }

    //POST: size = size' && elements[i] = elements[i]', ∀ i: [0..size) && ℝ = (size == 0)
    public boolean isEmpty() {
        return size == 0;
    }

    //POST: size = 0
    public void clear() {
        elements = new Object[DEFAULT_QUEUE_SIZE];
        head = tail = size = 0;
    }

    //PRE: element != null
    //POST: size = size' + 1 && elements[0] = element && elements[i] = elements[i-1]', ∀ i: [0..size')
    public void push(Object value) {
        assert (value != null) : "Null value found.";

        if (size >= elements.length) {
            elements = ensureCapacity(head, tail, elements);
            head = 0;
            tail = size;
        }
        head--;
        if (head < 0) {
            head = elements.length - 1;
        }
        elements[head] = value;
        size++;
    }

    //PRE: size > 0
    //POST: size = size' && elements[i] = elements[i]', ∀ i: [0..size) && ℝ = elements[size - 1]
    public Object peek() {
        assert (size > 0) : "No elements found";

        int position = tail - 1;
        if (position < 0) {
            position = elements.length - 1;
        }
        return elements[position];
    }

    //PRE: size > 0
    //POST: size = size' - 1 && ℝ = elements'[size] && elements[i] = elements[i]', ∀ i: [0..size)
    public Object remove() {
        assert (size > 0) : " No elements found";

        if (--tail < 0) {
            tail = elements.length - 1;
        }
        Object returnValue = elements[tail];
        elements[tail] = null;
        size -= 1;
        return returnValue;
    }

    //POST: size = size' && elements[i] = elements[i]' && newElements[i] = elements[i]', ∀ i: [0..size)
    // && ℝ = newElements[]
    public Object[] toArray() {
        return toArrayImpl(head,tail,size,elements);
    }
}