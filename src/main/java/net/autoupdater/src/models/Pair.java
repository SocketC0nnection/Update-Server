package net.autoupdater.src.models;

/**
 * @author SocketConnection
 * @github https://github.com/socketc0nnection
 **/

public final class Pair<K, V> {

    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public K getKey() {
        return key;
    }
}
