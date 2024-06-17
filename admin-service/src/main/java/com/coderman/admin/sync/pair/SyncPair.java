package com.coderman.admin.sync.pair;

import lombok.Data;

/**
 * @author zhangyukang
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
