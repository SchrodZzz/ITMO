package ru.ifmo.ivshin.arrayset;

import java.util.*;

public class ArraySet<T> extends AbstractSet<T> implements SortedSet<T> {

    private List<T> data;
    private Comparator<? super T> comparator;

    public ArraySet() {
        this(new ArrayList<>(), null);
    }

    public ArraySet(Collection<? extends T> collection) {
        this(new ArrayList<>(collection), null);
    }

    public ArraySet(Collection<? extends T> collection, Comparator<? super T> comparator) {
        TreeSet<T> tmp = new TreeSet<>(comparator);
        tmp.addAll(collection);

        this.data = new ArrayList<>(tmp);
        this.comparator = comparator;
    }


    private ArraySet(Comparator<? super T> comparator) {
        this(new ArrayList<>(), comparator);
    }


    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public Comparator<? super T> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        int l = Collections.binarySearch(data, fromElement, comparator);
        int r = Collections.binarySearch(data, toElement, comparator);
        return subSet(l, r);
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        int r = Collections.binarySearch(data, toElement, comparator);
        return data.isEmpty() ? new ArraySet<T>(comparator) : subSet(0, r);
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        int l = Collections.binarySearch(data, fromElement, comparator);
        return data.isEmpty() ? new ArraySet<T>(comparator) : subSet(l, data.size());
    }

    @Override
    public T first() {
        if (!data.isEmpty()) {
            return data.get(0);
        } else {
            throw new NoSuchElementException("Array is empty");
        }
    }

    @Override
    public T last() {
        if (!data.isEmpty()) {
            return data.get(data.size() - 1);
        } else {
            throw new NoSuchElementException("Array is empty");
        }
    }

    @SuppressWarnings("unchecked cast")
    public boolean contains(Object o) {
        return Collections.binarySearch(data, (T) o, comparator) >= 0;
    }

    private SortedSet<T> subSet(int l, int r) {
        if (l < 0) {
            l = -(l + 1);
        }
        if (r < 0) {
            r = -(r + 1);
        }
        return (l < r) ? new ArraySet<>(data.subList(l, r), comparator) : new ArraySet<>(comparator);
    }
}
