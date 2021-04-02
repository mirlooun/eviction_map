import java.util.Date;
import java.util.HashMap;


public class EvictionMap<K, V> {

    private final long duration;
    private final HashMap<K, Entity<K, V>> evictionMap = new HashMap<>();

    public EvictionMap(int duration) {
        this.duration = duration * 1000L;
    }

    public void put(K key, V value) {
        checkToEvict();
        if (evictionMap.containsKey(key)) {
            if (evictionMap.get(key).value == value) {
                evictionMap.get(key).expirationTime = 0;
            } else {
                evictionMap.get(key).value = value;
            }
        } else {
            long expirationTime = getCurrentDateTime() + duration;
            Entity<K, V> entity = new Entity<>(key, value, expirationTime);
            evictionMap.put(key, entity);
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
        evictionMap.entrySet().removeIf(entity -> isExpired(entity.getValue()));
    }

    public int size(){
        return evictionMap.size();
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
