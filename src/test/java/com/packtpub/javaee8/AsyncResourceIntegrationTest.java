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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AsyncResourceIntegrationTest {

    private Client client;
    private WebTarget webTarget;

    @Before
    public void setUp() throws Exception {
        client = ClientBuilder.newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS)
                .build();

        webTarget = client.target("http://localhost:8080").path("/async-service/api").path("/async");
    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }

    @Test
    public void v1UsingMediaType() throws Exception {
        Future<Response> futureResponse = webTarget.request().accept(MediaType.APPLICATION_JSON).async().get();
        Response response = futureResponse.get(1, TimeUnit.SECONDS);

        assertEquals(200, response.getStatus());
    }

    private GenericType<Map<String, String>> genericMap() {
        return new GenericType<Map<String, String>>() {
        };
    }
}
