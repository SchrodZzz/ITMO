package queue;

import java.util.function.Predicate;
import java.util.function.Function;

//INV: size >= 0
public interface Queue {

    //PRE: element != null
    //POST: last = element && size = size' + 1 && elements[i] = elements[i]', ∀ i: [0..size')
    void enqueue(Object element);

    //PRE: size > 0
    //POST: size = size' && elements[i] = elements[i]', ∀ i: [0..size) && ℝ = elements[0]
    Object element();

    //PRE: size > 0
    //POST: size = size' - 1 && ℝ = elements[0]' && elements[i] = elements[i+1]', ∀ i: [0..size)
    Object dequeue();

    //POST: size = size' && elements[i] = elements[i]', ∀ i: [0..size) && ℝ = size
    int size();

    //POST: size = size' && elements[i] = elements[i]', ∀ i: [0..size) && ℝ = (size == 0)
    boolean isEmpty();

    //POST: size = 0
    void clear();

    //POST: size = size' && elements[i] = elements[i]' && newElements[i] = elements[i]', ∀ i: [0..size)
    // && ℝ = newElements[]
    Object[] toArray();

    //POST: elements[i] = elements[i]', ∀ i: [0..size) && size = size' && ℝ = filter(array)
    Queue filter(Predicate <Object> predicate);

    //POST: elements[i] = elements[i]', ∀ i: [0..size) && size = size' &&  ℝ = map(array)
    Queue map(Function <Object, Object> function);

}