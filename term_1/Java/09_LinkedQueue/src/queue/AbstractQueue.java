package queue;

import java.util.function.Predicate;
import java.util.function.Function;

public abstract class AbstractQueue implements Queue {
    protected int size;

    public void enqueue(Object value) {
        assert value != null : "Null value found.";

        enqueueImpl(value);
        size++;
    }

    public Object element() {
        assert size > 0 : "No elements found.";

        return elementImpl();
    }

    public Object dequeue() {
        assert size > 0 : "No elements found.";

        Object result = element();
        size--;
        dequeueImpl();
        return result;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        clearImpl();
    }

    public Object[] toArray() {
        assert size >= 0 : "No elements found.";

        Object[] result = new Object[size];
        return toArrayImpl(result);
    }

    public Queue filter(Predicate <Object> predicate) {
        Queue buffer = copy();
        for (int i = 0; i < size; i++) {
            Object element = buffer.dequeue();
            if (predicate.test(element))
                buffer.enqueue(element);
        }
        return buffer;
    }

    public Queue map(Function <Object, Object> function) {
        Queue buffer = copy();
        for (int i = 0; i < size; i++) {
            Object element = buffer.dequeue();
            buffer.enqueue(function.apply(element));
        }
        return buffer;
    }

    protected abstract void enqueueImpl(Object value);

    protected abstract Object elementImpl();

    protected abstract void dequeueImpl();

    protected abstract void clearImpl();

    protected abstract Object[] toArrayImpl(Object[] result);

    protected abstract Queue copy();
}