package com.ojha;

import com.ojha.cache.CacheEntry;
import com.ojha.cache.CacheEntryBuilder;
import com.ojha.client.MiniMemcachedClient;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

/**
 * Created by alexandra on 02/01/2015.
 */
public class TestClient {

    public static String REAL_MEMCACHED_HOSTNAME = "192.168.1.2";
    public static String MINI_MEMCACHED_HOSTNAME = "localhost";

    public static int MEMCACHED_PORT = 11211;

    private static Socket socket;

    @BeforeClass
    public static void setup() throws IOException {
        socket = new Socket(MINI_MEMCACHED_HOSTNAME, MEMCACHED_PORT);
    }

    @Test
    public void test_set_stores_key_value_pair_successfully() throws IOException {

        MiniMemcachedClient client = new MiniMemcachedClient(this.socket);

        // (key, value) = (rawr_key, 2)

        CacheEntry cacheEntry = new CacheEntryBuilder()
                .withKey("rawr_key")
                .withFlags(0)
                .withExpTime(60)
                .withNumberOfBytes(1)
                .withBytes(new byte[] {2}).build();

        client.set(cacheEntry);

        assertEquals("STORED", client.readLine());
    }

    @Test
    public void test_can_get_key_that_has_been_set() throws IOException {

        // given
        OutputStream outputStream = socket.getOutputStream();

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        outputStream.write("set meow_key 0 60 1\r\n".getBytes());

        outputStream.write(5);
        outputStream.write("\r\n".getBytes());
        outputStream.flush();

        bufferedReader.readLine(); // "STORED"

        // when

        outputStream.write("get meow_key\r\n".getBytes());
        outputStream.flush();

        // then
        assertEquals("VALUE meow_key 0 1", bufferedReader.readLine());
        assertEquals(5, bufferedReader.read());
        bufferedReader.readLine();
        assertEquals("END", bufferedReader.readLine());
    }

    @Test
    public void test_out_of_memory() throws IOException {

        MiniMemcachedClient client = new MiniMemcachedClient(this.socket);

        int i = 0;
        while (true) {
            CacheEntry cacheEntry = new CacheEntryBuilder()
                    .withKey("key_meow" + i)
                    .withFlags(0)
                    .withExpTime(00)
                    .withNumberOfBytes(1)
                    .withBytes(new byte[] {2}).build();

            client.set(cacheEntry);
            i++;

            assertEquals("STORED", client.readLine());

        }

    }
}
