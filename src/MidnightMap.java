public interface MidnightMap<K,V> {

    // what functionality should we put into my hashmap?
    // put , get , contains , size

    // there can be many implementation (if we don't consider performance too much)

    int size();
    boolean isEmpty();

    // return old value
    V put(K key,V value);
    V get(K key);

    boolean containsKey(K key);
    boolean containsValue(V value);

    V remove(K key);

    void clear();


}
