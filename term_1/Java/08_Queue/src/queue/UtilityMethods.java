package queue;

class UtilityMethods {

    private UtilityMethods() {
    }

    //POST : elements.length >= size' && elements[i] = elements[i]', ∀ i: [0..size) && size = tail
    static Object[] ensureCapacity(int head, int tail, Object[] elements) {
        int newSize = elements.length * 2;
        Object[] newArray = new Object[newSize];
        if (tail <= head) {
            System.arraycopy(elements, head, newArray, 0, elements.length - head);
            System.arraycopy(elements, 0, newArray, elements.length - head, tail);
        } else {
            System.arraycopy(elements, head, newArray, 0, tail - head);
        }

        return newArray;
    }

    //POST: size = size' && elements[i] = elements[i]' && newElements[i] = elements[i]', ∀ i: [0..size)
    // && ℝ = newElements[]
    static Object[] toArrayImpl(int head, int tail, int size, Object[] elements) {
        Object[] newElements = new Object[size];
        if (head < tail) {
            System.arraycopy(elements, head, newElements, 0, size);
        } else if (size != 0) {
            System.arraycopy(elements, head, newElements, 0, elements.length - head);
            System.arraycopy(elements, 0, newElements, elements.length - head, tail);
        }
        return newElements;
    }

}
