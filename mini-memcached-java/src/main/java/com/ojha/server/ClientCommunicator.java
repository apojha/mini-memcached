package com.ojha.server;

import com.ojha.cache.CacheEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by alexandra on 04/01/2015.
 */
public class ClientCommunicator {

    private final OutputStream out;
    private final BufferedReader in;

    public ClientCommunicator(OutputStream out, BufferedReader in) {
        this.out = out;
        this.in = in;
    }

    public String readLine() throws IOException {
        return this.in.readLine();
    }

    public void writeCachedValue(CacheEntry cachedVaue) throws IOException {
        out.write(cachedVaue.getResponseToGet().getBytes());
        out.write(cachedVaue.getBytes());
        this.writeCarriageReturn();

        out.write("END".getBytes());
        this.writeCarriageReturn();

        out.flush();
    }

    public byte[] readBytes(int numberOfBytes) throws IOException {
        int byteCount = 0;

        byte[] bytesRead = new byte[numberOfBytes];
        while (byteCount < numberOfBytes) {
            bytesRead[byteCount] = (byte) in.read();
            byteCount++ ;
        }

        in.readLine();

        return bytesRead;
    }

    public void writeStoredSuccessful() throws IOException {
        out.write("STORED".getBytes());
        this.writeCarriageReturn();
        out.flush();
    }

    private void writeCarriageReturn() throws IOException {
        out.write("\r\n".getBytes());
    }
}
