import java.util.*;


public class EvictionMap<K, V> {

    private final long duration; // defines the duration of entity expiration time (after what time entity has to be evicted)
    private final HashMap<K, Entity<K, V>> evictionMap = new HashMap<>(); // EvictionMap class "engine"
    private final PriorityQueue<Entity<K,V>> onCheck = new PriorityQueue<>(Comparator.comparing(x -> x.expirationTime));


    public EvictionMap(int duration) {
        this.duration = duration * 1000L; // in seconds
    }

    public void put(K key, V value) {
        checkToEvict();
        if (evictionMap.containsKey(key)) {
            if (evictionMap.get(key).value == value) {
                evictionMap.remove(key);
            } else {
                evictionMap.get(key).value = value;
            }
        } else {
            long expirationTime = getCurrentDateTime() + duration;
            Entity<K, V> entity = new Entity<>(key, value, expirationTime);
            evictionMap.put(key, entity);
            onCheck.add(entity);
        }
    }

    public V get(K key) {
        checkToEvict();
        var entity = evictionMap.get(key);
        if (entity == null) return null;
        return isExpired(entity) ? null : entity.value;
    }

    private boolean isExpired(Entity<K, V> entity) {
        return entity.expirationTime < getCurrentDateTime();
    }

    private long getCurrentDateTime() {
        return new Date().getTime();
    }

    private void checkToEvict() {
        if (onCheck.size() == 0) return;
        while(true) {
            assert onCheck.peek() != null;
            if (!(onCheck.peek().expirationTime < getCurrentDateTime())) break;
            evictionMap.remove(Objects.requireNonNull(onCheck.poll()).key);

        }
    }

    @Override
    public String toString() {
        StringBuilder a = new StringBuilder();
        for (var entry: evictionMap.entrySet()) {
            a.append(entry.getKey().toString()).append(" - ").append(entry.getValue().value.toString()).append("\n");
        }

        return a.toString();
    }
}
