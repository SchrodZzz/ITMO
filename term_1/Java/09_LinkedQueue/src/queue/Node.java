package queue;

class Node {
    Object value;
    Node next;

    Node(Object value, Node next) {
        this.value = value;
        this.next = next;
    }
}