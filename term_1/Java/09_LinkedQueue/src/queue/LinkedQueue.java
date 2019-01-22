package queue;

public class LinkedQueue extends AbstractQueue implements Queue {
    private Node head, tail;

    public LinkedQueue() {
        head = new Node(null, null);
        tail = head;
    }

    public void enqueueImpl(Object element) {
        tail.value = element;
        Node currentNode = new Node(null, null);
        tail.next = currentNode;
        tail = currentNode;
    }

    public Object elementImpl() {
        return head.value;
    }

    public void dequeueImpl() {
        head = head.next;
    }

    public void clearImpl() {
        head = new Node(null, null);
        tail = head;
    }

    public Object[] toArrayImpl(Object[] result) {
        Node temporaryNode = head;
        int i = 0;
        while (temporaryNode.value != null && temporaryNode.next != null) {
            result[i] = temporaryNode.value;
            i++;
            temporaryNode = temporaryNode.next;
        }
        return result;
    }

    protected LinkedQueue copy() {
        LinkedQueue buff = new LinkedQueue();
        for (int i = 0; i < size; i++) {
            Object res = dequeue();
            buff.enqueue(res);
            enqueue(res);
        }
        return buff;
    }
}