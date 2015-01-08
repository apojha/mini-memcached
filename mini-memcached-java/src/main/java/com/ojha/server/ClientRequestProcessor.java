package com.ojha.server;

import com.ojha.server.cache.Cache;
import com.ojha.cache.CacheEntry;
import com.ojha.cache.CacheEntryBuilder;

import java.io.*;
import java.net.Socket;

/**
 * Created by alexandra on 02/01/2015.
 */
public class ClientRequestProcessor {

    private final Cache cache;

    public ClientRequestProcessor(Cache cache) {
        this.cache = cache;
    }

    public void handle(Socket clientSocket) throws IOException {

        ClientCommunicator talkToClient = new ClientCommunicator(clientSocket.getOutputStream(),
                new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));

        String command;
        while ((command = talkToClient.readLine()) != null) {
            this.parseClientCommand(command, talkToClient);
        }
    }

    private void parseClientCommand(String inputLine, ClientCommunicator talkToClient) throws IOException {
        String[] inputLineFragments = inputLine.split(" ");

        if (inputLineFragments[0].equals("set")) {
            this.set(inputLineFragments, talkToClient);
        }

        if (inputLineFragments[0].equals("get")) {
            this.get(inputLineFragments, talkToClient);
        }
    }

    private void get(String[] inputLineFragments, ClientCommunicator talkToClient) throws IOException {
        String key = inputLineFragments[1];
        CacheEntry cachedVaue = this.cache.get(key);

        talkToClient.writeCachedValue(cachedVaue);

    }

    private void set(String[] inputLineFragments, ClientCommunicator talkToClient) throws IOException {
        if (inputLineFragments.length == 5) {
            int numberOfBytes = Integer.parseInt(inputLineFragments[4]);

            byte[] bytesRead = talkToClient.readBytes(numberOfBytes);

            CacheEntry value = new CacheEntryBuilder()
                    .withBytes(bytesRead)
                    .withKey(inputLineFragments[1])
                    .withFlags(inputLineFragments[2])
                    .withExpTime(inputLineFragments[3])
                    .withNumberOfBytes(inputLineFragments[4]).build();

            this.cache.set(value.getKey(), value);

            talkToClient.writeStoredSuccessful();

        }
    }
}
