import java.util.Arrays;
import java.util.Objects;

public class JianghongHashMap<K,V> implements MidnightMap<K,V> {

    public static final int DEFAULT_CAPACITY = 16;
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node<K,V>[] array;
    private int size;

    private float loadFactor;

    public JianghongHashMap() {
        this(DEFAULT_CAPACITY,DEFAULT_LOAD_FACTOR);
    }

    public JianghongHashMap(int initCapacity) {
        this(initCapacity,DEFAULT_LOAD_FACTOR);
    }

    public JianghongHashMap(float loadFactor) {
        this(DEFAULT_CAPACITY,loadFactor);
    }

    public JianghongHashMap(int initCapacity, float loadFactor) {
        if (initCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity must not be less than 0");
        }
        this.loadFactor = loadFactor <= 1? loadFactor : DEFAULT_LOAD_FACTOR;
        this.array = (Node<K,V>[]) (new Node[initCapacity]);
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V put(K key, V value) {
        int index = getIndex(key);
        Node<K,V> head = array[index];
        Node<K,V> node = head;
        while (node != null) {
            if (Objects.equals(node.getKey(),key)) {
                V oldValue = node.getValue();
                node.setValue(value);
                return oldValue;
            }
            node = node.next;
        }

        Node<K,V> newHead = new Node<>(key,value);
        newHead.next = head;
        array[index] = newHead;
        size++;

        if (needRehash()) {
            rehash();
        }

        return null;
    }

    @Override
    public V get(K key) {
        Node<K,V> node = array[getIndex(key)];
        while (node != null) {
            if (Objects.equals(node.getKey(),key)) {
                return node.getValue();
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        Node<K,V> node = array[getIndex(key)];
        while (node != null) {
            if (Objects.equals(node.getKey(),key)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        for (Node<K,V> node : array) {
            while (node != null) {
                if (Objects.equals(node.getValue(),value)) {
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }

    @Override
    public V remove(K key) {
        int index = getIndex(key);
        Node<K,V> prev = null;
        Node<K,V> curr = array[index];
        while (curr != null) {
            if (Objects.equals(curr.getKey(),key)) {
                if (prev == null) {
                    array[index] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                size--;
                return curr.getValue();
            }
            prev = curr;
            curr = curr.next;
        }
        return null;
    }

    @Override
    public void clear() {
        Arrays.fill(array,null);
        size = 0;
    }

    private int getIndex(K key){
        if (key == null) {
            return 0;
        }
        return (key.hashCode() & Integer.MAX_VALUE) % array.length;
    }

    private boolean needRehash() {
        float ratio = (size+0.0f)/array.length;
        return ratio >= loadFactor;
    }

    private void rehash() {
        Node<K,V>[] oldArray = array;
        array = (Node<K,V>[]) (new Node[(int)(array.length*1.5)]);
        for (Node<K,V> node : oldArray) {
            while (node != null) {
                put(node.getKey(), node.getValue());
                node = node.next;
            }
        }

    }
    // static inner class
    public static class Node<K,V> {
        // because key is final there is no setter for key
        final K key;
        V value;
        Node<K,V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }



    // public double hashCode() { return 2.5;}

    public static void main(String[] args) {

    }
}


