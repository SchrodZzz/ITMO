package ru.ifmo.ivshin.concurrent;

import info.kgeorgiy.java.advanced.concurrent.ScalarIP;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Implementation of the {@code ScalarIP} interface, providing methods for parallel data processing.
 *
 * @author Fat Lion
 * @see ScalarIP
 */
// Overridden methods are already have javadoc comments from interface
public class IterativeParallelism implements ScalarIP {

    @Override
    public <T> T maximum(int threads, List<? extends T> values, Comparator<? super T> comparator) throws InterruptedException {
        return parallelReduce(threads, values,
                stream -> max(stream, comparator),
                stream -> max(stream, comparator));
    }

    @Override
    public <T> T minimum(int threads, List<? extends T> values, Comparator<? super T> comparator) throws InterruptedException {
        return maximum(threads, values, Collections.reverseOrder(comparator));
    }

    @Override
    public <T> boolean all(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return parallelReduce(threads, values,
                stream -> stream.allMatch(predicate),
                stream -> stream.allMatch(Boolean::booleanValue));
    }

    @Override
    public <T> boolean any(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return !all(threads, values, predicate.negate());
    }

    /**
     * Returns max value of {@code stream} by provided {@code comparator}.
     *
     * @param stream     the stream of values
     * @param comparator the comparator
     * @param <T>        value type
     * @return maximum value
     * @throws NoSuchElementException if no values are given
     * @see Stream#max(Comparator)
     */
    private <T> T max(Stream<T> stream, Comparator<? super T> comparator) {
        return stream.max(comparator).get();
    }

    /**
     * Reduces the given values by provided reducing functions. Concurrently processes the provided
     * {@code values} by splitting it into several blocks, processing each in a separate {@code Thread},
     * and combines the result.
     *
     * @param threadsCount number of concurrent threads
     * @param values       values to reduce
     * @param threadReduce a {@code Function}, capable of reducing a {@code Stream} of values of type {@code T}
     *                     to a single value of type {@code U}
     * @param resReduce    a {@code Function}, capable of reducing a {@code Stream} of values of type {@code U}
     *                     *                     to a single value of type {@code U}
     * @param <T>          type of element in the {@code List}
     * @param <U>          result type
     * @return the result of reducing
     * @throws InterruptedException if executing thread was interrupted
     */
    public <T, U> U parallelReduce(int threadsCount, List<? extends T> values,
                                   Function<Stream<? extends T>, U> threadReduce,
                                   Function<Stream<U>, U> resReduce) throws InterruptedException {
        List<Stream<? extends T>> chunks = split(values, threadsCount);
        List<Thread> threads = new ArrayList<>();
        List<U> res = new ArrayList<>(Collections.nCopies(chunks.size(), null));
        for (int i = 0; i < chunks.size(); i++) {
            final int idx = i;
            Thread thread = new Thread(() -> res.set(idx, threadReduce.apply(chunks.get(idx))));
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        return resReduce.apply(res.stream());
    }

    /**
     * Splits the given {@code values}. Returns a {@code List} with {@code blocks} amount of parts,
     * or less, if {@code blocks} is greater than the amount elements in {@code values}.
     *
     * @param values values to split
     * @param blocks amount of resulting blocks
     * @param <T>    type of element in the {@code List}
     * @return a {@code List} of blocks, each one represented by a {@code Stream} of values
     */
    private <T> List<Stream<? extends T>> split(List<? extends T> values, int blocks) {
        List<Stream<? extends T>> chunks = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < blocks; i++) {
            int end = start + values.size() / blocks;
            if (i < values.size() % blocks) {
                end++;
            }
            if (start < end) {
                chunks.add(values.subList(start, end).stream());
            }
            start = end;
        }
        return chunks;
    }
}
