import java.util.Optional;

public abstract class LRUCache<K, V> {
    protected Node head = null;
    protected Node tail = null;
    protected final int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public void put(K key, V value) {
        assert key != null : AssertionMassages.KEY_IS_NULL.toString();
        assert value != null : AssertionMassages.VALUE_IS_NULL.toString();
        putImplementation(key, value);
        assert head != null && tail != null : AssertionMassages.INSERT_PROBLEM.toString();
        assert head.value == value : AssertionMassages.PUSH_PROBLEM.toString();
        assert size() <= capacity : AssertionMassages.TOO_BIG.toString();
    }

    public Optional<V> get(K key) {
        assert key != null : AssertionMassages.KEY_IS_NULL.toString();
        int oldSize = size();
        Optional<V> res = getImplementation(key);
        assert (res.isEmpty() || head.value == res.get()) : AssertionMassages.PUSH_PROBLEM.toString();
        assert oldSize == size() : AssertionMassages.TOO_BIG.toString();
        return res;
    }

    protected int size() {
        int res = 0;
        Node cur = head;
        while (cur != null) {
            cur = cur.nxt;
            res++;
        }
        return res;
    }

    protected abstract void putImplementation(K key, V value);
    protected abstract Optional<V> getImplementation(K key);

    protected enum AssertionMassages {
        KEY_IS_NULL("Key mustn't be null"),
        VALUE_IS_NULL("Value mustn't be null"),
        INSERT_PROBLEM("Value insert problem"),
        PUSH_PROBLEM("Value isn't pushed"),
        TOO_BIG("Current size is too big");

        private String body;

        AssertionMassages(String body) {
            this.body = body;
        }

        @Override
        public String toString() {
            return body;
        }
    }

    protected class Node {
        final K key;
        final V value;
        Node prv, nxt;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
