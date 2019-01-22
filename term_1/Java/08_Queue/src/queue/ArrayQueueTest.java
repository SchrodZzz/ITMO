package queue;

import java.io.IOException;

public class ArrayQueueTest {

    private static void fill(ArrayQueue queue) {
        for (int i = 9; i >= 0; i--) {
            queue.enqueue(i);
        }
        System.out.println("Queue has been filled - current size: " + queue.size());
        border();
    }

    private static void dump(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(
                    queue.size() + " " +
                            queue.element() + " " +
                            queue.dequeue()
            );
        }
        System.out.println("Queue is empty");
        border();
    }

    private static void deleteAllElements(ArrayQueue queue) {
        queue.clear();
        System.out.println("All elements in queue has been deleted");
        border();
    }

    private static void border() {
        System.out.println("<-------->");
    }

    private static void makeSomeCustomTests(ArrayQueue queue, MyScanner reader) throws IOException {
        System.out.println("Would you like to make some custom tests? (Y/N)");
        switch (reader.readNextString().toUpperCase()) {
            case "Y": {
                customTest(queue, reader);
                break;
            }
            case "N": {
                System.out.println("Tests completed");
                break;
            }
            default: {
                System.out.println("Incorrect input");
                break;
            }
        }
        border();
    }

    private static void customTest(ArrayQueue queue, MyScanner reader) throws IOException {
        System.out.println("Welcome to custom test!\nPlease, enter operation: ");
        int elementToAdd = 0;
        while (true) {
            switch (reader.readNextString()) {
                case "enqueue": {
                    queue.enqueue(elementToAdd++);
                    break;
                }
                case "element": {
                    System.out.println(queue.element());
                    break;
                }
                case "dequeue": {
                    System.out.println(queue.dequeue());
                    break;
                }
                case "size": {
                    System.out.println(queue.size());
                    break;
                }
                case "isEmpty": {
                    System.out.println(queue.isEmpty());
                    break;
                }
                case "clear": {
                    queue.clear();
                    break;
                }
                case "push": {
                    queue.push(elementToAdd++);
                    break;
                }
                case "peek": {
                    System.out.println(queue.peek());
                    break;
                }
                case "remove": {
                    System.out.println(queue.remove());
                    break;
                }
                case "toArray": {
                    Object[] copiedArray = queue.toArray();
                    System.out.println("Copied queue: ");
                    for (Object currentObject : copiedArray) {
                        System.out.print(currentObject + " ");
                    }
                    System.out.print("\n");
                    break;
                }
                case "stop": {
                    border();
                    return;
                }
                default: {
                    System.out.println("Incorrect operation");
                }
            }

            System.out.println("To stop test - type \"stop\"");
        }
    }

    public static void main(String[] args) throws IOException {
        MyScanner reader = new MyScanner();
        ArrayQueue queue = new ArrayQueue();
        border();
        fill(queue);
        dump(queue);
        fill(queue);
        deleteAllElements(queue);
        dump(queue);
        makeSomeCustomTests(queue, reader);
    }
}