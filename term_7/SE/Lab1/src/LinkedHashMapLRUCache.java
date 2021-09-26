import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LinkedHashMapLRUCache<K, V> extends LRUCache<K, V> {
    private Map<K, Node> keyToNode = new HashMap<>();

    public LinkedHashMapLRUCache(int capacity) {
        super(capacity);
    }

    protected void putImplementation(K key, V value) {
        remove(key);
        add(key, value);
    }

    protected Optional<V> getImplementation(K key) {
        if (!keyToNode.containsKey(key)) return Optional.empty();

        V res = keyToNode.get(key).value;
        remove(key);
        add(key, res);

        return Optional.of(res);
    }

    @Override
    protected int size() {
        return keyToNode.size();
    }

    private void remove(K key) {
        if (!keyToNode.containsKey(key)) return;

        Node cur = keyToNode.get(key);

        if (cur.nxt != null) {
            cur.nxt.prv = cur.prv;
        } else {
            tail = cur.prv;
        }

        if (cur.prv != null) {
            cur.prv.nxt = cur.nxt;
        } else {
            head = cur.nxt;
        }

        keyToNode.remove(key);
    }

    private void add(K key, V value) {
        Node newNode = new Node(key, value);
        newNode.nxt = head;

        if (head != null) {
            head.prv = newNode;
        }

        head = newNode;
        if (tail == null) {
            tail = newNode;
        }

        keyToNode.put(key, newNode);
        if (size() > capacity) {
            remove(tail.key);
        }
    }
}