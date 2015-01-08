package com.ojha.server;

import com.ojha.server.cache.Cache;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 * Created by alexandra on 01/01/2015.
 */
public class MiniMemcachedServer {

    private static int PORT = 11211;
    private static String propertiesFileName = "config.properties";


    public static void main(String... args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);

        Cache cache = configureCache();
        ClientRequestProcessor clientRequestProcessor = new ClientRequestProcessor(cache);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            clientRequestProcessor.handle(clientSocket);
        }
    }

    private static Cache configureCache() throws IOException {

        Properties properties = new Properties();

        try (InputStream inputStream = MiniMemcachedServer.class.getClassLoader().getResourceAsStream(propertiesFileName)){

            properties.load(inputStream);
            double minimumFreeSpace = Double.parseDouble((String) properties.get("minimumFreeSpace"));
            double freeSpaceAfterExpiry = (Double.parseDouble((String) properties.get("freeSpaceAfterExpiry")));

            Cache cache = new Cache(minimumFreeSpace, freeSpaceAfterExpiry);

            return cache;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Unable to instantiate cache from properties file");


    }
}
