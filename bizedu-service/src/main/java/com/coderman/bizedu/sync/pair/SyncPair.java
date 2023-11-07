package com.coderman.bizedu.sync.pair;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class SyncPair<K,V> {

    private K key;

    private V value;

    public SyncPair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
