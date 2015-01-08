package com.ojha.cache;

/**
 * Created by alexandra on 03/01/2015.
 */
public class CacheEntryBuilder {

    private int flags;
    private String key;
    private int numberOfBytes;
    private byte[] bytes;
    private int expTime;

    public CacheEntryBuilder withFlags(String unparsedFlags) {
        int flags = Integer.parseInt(unparsedFlags);
        this.flags = flags;
        return this;
    }

    public CacheEntryBuilder withFlags(int flags) {
        this.flags = flags;
        return this;
    }

    public CacheEntryBuilder withExpTime(String unparsedExpTime) {
        int expTime = Integer.parseInt(unparsedExpTime);
        this.expTime = expTime;
        return this;
    }

    public CacheEntryBuilder withExpTime(int expTime) {
        this.expTime = expTime;
        return this;
    }

    public CacheEntryBuilder withNumberOfBytes(String unparsedNumberOfBytes) {
        int numberOfBytes = Integer.parseInt(unparsedNumberOfBytes);
        this.numberOfBytes = numberOfBytes;
        return this;
    }

    public CacheEntryBuilder withNumberOfBytes(int numberOfBytes) {
        this.numberOfBytes = numberOfBytes;
        return this;
    }

    public CacheEntryBuilder withKey(String key) {
        this.key = key;
        return this;
    }

    public CacheEntryBuilder withBytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }

    public CacheEntry build() {
        return new CacheEntry(this.key, this.flags, this.expTime, this.bytes, this.numberOfBytes);
    }
}
