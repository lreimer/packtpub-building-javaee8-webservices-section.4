package com.packtpub.javaee8;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FibonacciResourceIntegrationTest {

    private Client client;
    private WebTarget webTarget;

    @Before
    public void setUp() {
        client = ClientBuilder.newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
                .build();

        webTarget = client.target("http://localhost:8080").path("/async-service/api").path("/fibonacci");
    }

    @After
    public void tearDown() {
        client.close();
    }

    @Test
    public void fibonacci17() {
        Long fibonacci = webTarget.path("/17").request().get(Long.class);
        assertEquals(1597, (long) fibonacci);
    }

    @Test
    public void fibonacci42() {
        Long fibonacci = webTarget.path("/42").request().get(Long.class);
        assertEquals(267914296, (long) fibonacci);
    }

    @Test
    public void fibonacci49() {
        Response fibonacci = webTarget.path("/49").request().get();
        assertEquals(202, fibonacci.getStatus());
    }
}
