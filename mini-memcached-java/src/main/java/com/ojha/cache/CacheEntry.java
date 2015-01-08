package com.ojha.cache;

/**
 * Created by alexandra on 03/01/2015.
 */
public class CacheEntry {

    private final String key;
    private final int flags;
    private final int expTime;
    private final byte[] bytes;
    private final int numberOfBytes;

    public CacheEntry(String key, int flags, int expTime, byte[] bytes, int numberOfBytes) {
        this.key = key;
        this.flags = flags;
        this.expTime = expTime;
        this.bytes = bytes;
        this.numberOfBytes = numberOfBytes;
    }

    public String getKey() {
        return key;
    }

    public int getFlags() {
        return flags;
    }

    public int getExpTime() {
        return expTime;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public int getNumberOfBytes() {
        return numberOfBytes;
    }

    public String getResponseToGet() {
        StringBuilder builder = new StringBuilder("VALUE ");
        builder.append(this.key);
        builder.append(" ");
        builder.append(this.flags);
        builder.append(" ");
        builder.append(this.numberOfBytes);
        builder.append("\r\n");

        return builder.toString();
    }


}
