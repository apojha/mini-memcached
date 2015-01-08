package com.ojha.server.cache;

import com.ojha.cache.CacheEntry;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by alexandra on 03/01/2015.
 */
public class Cache {

    private final HashMap<String, CacheEntry> map;
    private final Queue<String> log;
    private final double freeSpaceThreshold;
    private final double freeSpaceTarget;

    /*
        eg minimumFreeSpace = 0.2, fractionFreeSpaceAfterExpiry = 0.4
           => When 20% of the heap is free, expire cache entries until
              40% of the heap is free

              eg minimumFreeSpace = 0.1, fractionFreeSpaceAfterExpiry = 0.3
           => When 10% of the heap is free, expire cache entries until
              30% of the heap is free
     */
    public Cache(double minimumFreeSpace, double fractionFreeSpaceAfterExpiry) {

        double heapMaxSize = (double) Runtime.getRuntime().maxMemory();
        this.freeSpaceThreshold = heapMaxSize * minimumFreeSpace; // in bytes
        this.freeSpaceTarget = heapMaxSize * fractionFreeSpaceAfterExpiry; // in bytes

        this.map = new HashMap<>();
        this.log = new LinkedList<>();
    }

    public void set(String key, CacheEntry value) {
        checkCapacity();
        this.map.put(key, value);
        this.log.add(key);
    }

    public CacheEntry get(String key) {
        return this.map.get(key);
    }

    public void checkCapacity() {

        double heapFreeSize = (double) Runtime.getRuntime().freeMemory();

        if (heapFreeSize < this.freeSpaceThreshold) {
            while (heapFreeSize < this.freeSpaceTarget) {
                if (this.log.size() > 0) {
                    this.log.remove();
                }

                heapFreeSize = (double) Runtime.getRuntime().freeMemory();
            }
        }

    }
}
