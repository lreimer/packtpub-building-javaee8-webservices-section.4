package com.packtpub.javaee8;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AsyncResourceIntegrationTest {

    private Client client;
    private WebTarget webTarget;

    @Before
    public void setUp() {
        client = ClientBuilder.newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS)
                .build();

        webTarget = client.target("http://localhost:8080").path("/async-service/api").path("/async");
    }

    @After
    public void tearDown() {
        client.close();
    }

    @Test
    public void sync503() {
        Response response = webTarget.request().accept(MediaType.APPLICATION_JSON).get();
        assertEquals(503, response.getStatus());
    }

    @Test
    public void async503() throws Exception {
        Future<Response> responseFuture = webTarget.request().accept(MediaType.APPLICATION_JSON).async().get();
        Response response = responseFuture.get(6, TimeUnit.SECONDS);
        assertEquals(503, response.getStatus());
    }

    @Test
    public void lockUnlock() throws Exception {
        Future<Response> responseFuture = webTarget.request().accept(MediaType.APPLICATION_JSON).async().get();

        TimeUnit.SECONDS.sleep(2);

        Response delete = webTarget.request().delete();
        assertEquals(204, delete.getStatus());

        Response response = responseFuture.get(5, TimeUnit.SECONDS);
        assertEquals(200, response.getStatus());

        Map<String, String> entity = response.readEntity(genericMap());
        assertNotNull(entity.get("currentThread"));
    }

    private GenericType<Map<String, String>> genericMap() {
        return new GenericType<Map<String, String>>() {
        };
    }
}
