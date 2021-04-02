public class Entity<K, V> {
    public final K key;
    public V value;
    public long expirationTime;

    public Entity(K key, V value, long expirationTime) {
        this.key = key;
        this.value = value;
        this.expirationTime = expirationTime;
    }
}
