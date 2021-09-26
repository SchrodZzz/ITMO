import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class LRUCacheTest {

    private LRUCache<Integer, Integer> cache;

    @BeforeEach
    public void prepare() {
        cache = new LinkedHashMapLRUCache<>(2);
    }

    @Test
    public void testEmpty() {
        assertFalse(cache.get(0).isPresent());
    }

    @Test
    public void testGet() {
        cache.put(0, 0);

        assertEquals(Optional.of(0), cache.get(0));
    }

    @Test
    public void testUpdate() {
        cache.put(0, 0);
        cache.put(0, 1);

        assertEquals(Optional.of(1), cache.get(0));
    }

    @Test
    public void testRemove() {
        cache.put(0, 0);    // [0]
        cache.put(1, 1);    // [1, 0]
        cache.put(2, 2);    // [2, 1]

        assertFalse(cache.get(0).isPresent());
    }

    @Test
    public void testPushByGet() {
        cache.put(0, 0);    // [0]
        cache.put(1, 1);    // [1, 0]
        cache.get(0);       // [0, 1]
        cache.put(2, 2);    // [2, 0]

        assertFalse(cache.get(1).isPresent());
    }

    @Test
    public void testPushByPut() {
        cache.put(0, 0);    // [0]
        cache.put(1, 1);    // [1, 0]
        cache.put(0, 0);    // [0, 1]
        cache.put(2, 2);    // [2, 0]

        assertFalse(cache.get(1).isPresent());
    }

    @Test
    public void testContracts() {
        for (int i = 0; i < 1000000; i++) {
            Random rand = new Random();
            if (rand.nextBoolean()) {
                cache.put(rand.nextInt() % 20, rand.nextInt());
            } else {
                cache.get(rand.nextInt() % 20);
            }
        }
    }

}