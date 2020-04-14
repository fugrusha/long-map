package de.comparus.opensource.longmap;

import java.util.LinkedList;

public class LongMapImpl<V> implements LongMap<V> {

    private int size;
    private int maxMapSize;
    private final static int INITIAL_MAX_SIZE = 32; // 5^2
    private LinkedList<MapItem<Long, V>>[] mapItems;

    public LongMapImpl() {
        this.size = 0;
        this.maxMapSize = INITIAL_MAX_SIZE;
        this.mapItems = new LinkedList[INITIAL_MAX_SIZE];
    }

    public LongMapImpl(int maxMapSize) {
        if (maxMapSize < 1) {
            throw new IllegalArgumentException("MaxMapSize cannot be less then 1");
        }

        if (maxMapSize < INITIAL_MAX_SIZE) {
            this.maxMapSize = INITIAL_MAX_SIZE;
            this.mapItems = new LinkedList[INITIAL_MAX_SIZE];
        } else {
            this.maxMapSize = maxMapSize;
            this.mapItems = new LinkedList[maxMapSize];
        }
    }

    static class MapItem<Long, V> {
        private final Long key;
        private V value;

        public MapItem(Long key, V value) {
            this.key = key;
            this.value = value;
        }

        public Long getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public boolean hasSameKey(long key) {
            return this.key.equals(key);
        }
    }

    private int getHash(long key) {
        return (int) Math.abs(key % maxMapSize);
    }

    public V put(long key, V value) {
        int hashKey = getHash(key);

        if (mapItems[hashKey] == null) {
            mapItems[hashKey] = new LinkedList<>();
        }

        for (MapItem<Long, V> i : mapItems[hashKey]) {
            if (i.hasSameKey(key)) {
                mapItems[hashKey].remove(i);
                size--;
                break;
            }
        }

        mapItems[hashKey].add(new MapItem<>(key, value));

        size++;
        return value;
    }

    public V get(long key) {
        int hashKey = getHash(key);

        if (mapItems[hashKey] == null) {
            return null;
        }

        for (MapItem<Long, V> i : mapItems[hashKey]) {
            if (i.hasSameKey(key)) {
                return i.getValue();
            }
        }

        return null;
    }

    public V remove(long key) {
        int hashKey = getHash(key);

        if (mapItems[hashKey] == null) {
            return null;
        }

        for (MapItem<Long, V> i : mapItems[hashKey]) {
            if (i.hasSameKey(key)) {
                mapItems[hashKey].remove(i);
                size--;
                return i.getValue();
            }
        }

        return null;
    }

    public boolean containsKey(long key) {
        if (mapItems != null && size > 0) {
            int hashKey = getHash(key);

            if (mapItems[hashKey] == null) {
                return false;
            }

            for (MapItem<Long, V> item : mapItems[hashKey]) {
                if (item.hasSameKey(key)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean containsValue(V value) {
        if (mapItems != null && size > 0) {
            for (LinkedList<MapItem<Long, V>> linkedList : mapItems) {
                if (linkedList != null && !linkedList.isEmpty()) {
                    for (MapItem<Long, V> item : linkedList) {
                        if(item.getValue() == value || item.getValue().equals(value)) {
                            return true;
                        };
                    }
                }
            }
        }

        return false;
    }

    public long[] keys() {
        if (mapItems != null && size > 0) {
            long[] keys = new long[size];
            int i = 0;
            for (LinkedList<MapItem<Long, V>> linkedList : mapItems) {
                if (linkedList != null && !linkedList.isEmpty()) {
                    for (MapItem<Long, V> item : linkedList) {
                        keys[i] = item.getKey();
                        i++;
                    }
                }
            }
            return keys;
        }
        return null;
    }

    public V[] values() {
        if (mapItems != null && size > 0) {
            V[] values = (V[]) new Object[size];
            int i = 0;
            for (LinkedList<MapItem<Long, V>> linkedList : mapItems) {
                if (linkedList != null && !linkedList.isEmpty()) {
                    for (MapItem<Long, V> item : linkedList) {
                        values[i] = item.getValue();
                        i++;
                    }
                }
            }
            return values;
        }
        return null;
    }

    public void clear() {
        size = 0;

        for (int i = 0; i < mapItems.length; i++) {
            if (mapItems[i] != null) {
                mapItems[i] = null;
            }
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public long size() {
        return size;
    }

    public long getMaxMapSize() {
        return maxMapSize;
    }
}
