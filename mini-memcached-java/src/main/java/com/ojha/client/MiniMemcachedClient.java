package com.ojha.client;

import com.ojha.cache.CacheEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by alexandra on 06/01/2015.
 */
public class MiniMemcachedClient {

    private final BufferedReader bufferedReader;
    private final OutputStream outputStream;

    public MiniMemcachedClient(Socket socket) {

        InputStreamReader inputStreamReader = null;
        try {
            this.outputStream = socket.getOutputStream();
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            this.bufferedReader = new BufferedReader(inputStreamReader);

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void set(CacheEntry cacheEntry) throws IOException {

        StringBuilder builder = new StringBuilder("set ");
        builder.append(cacheEntry.getKey());
        builder.append(" ");
        builder.append(cacheEntry.getFlags());
        builder.append(" ");
        builder.append(cacheEntry.getExpTime());
        builder.append(" ");
        builder.append(cacheEntry.getNumberOfBytes());
        builder.append("\r\n");

        this.outputStream.write(builder.toString().getBytes());
        this.outputStream.write(cacheEntry.getBytes());
        this.outputStream.write("\r\n".getBytes());
        this.outputStream.flush();
    }

    public String readLine() throws IOException {
        return this.bufferedReader.readLine();
    }
}
