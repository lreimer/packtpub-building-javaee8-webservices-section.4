package com.packtpub.javaee8;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AsyncWebServiceClientIntegrationTest {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    private Client client;
    private WebTarget webTarget;

    @Before
    public void setUp() {
        client = ClientBuilder.newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
                .build();

        webTarget = client.target("http://localhost:8080").path("/async-service/api");
    }

    @After
    public void tearDown() {
        client.close();
    }

    @Test
    public void fibonacci17() throws Exception {
        Future<Long> fibonacci = webTarget.path("/fibonacci/17").request(MediaType.TEXT_PLAIN_TYPE).async().get(Long.class);
        assertEquals(1597, (long) fibonacci.get());

    }

    @Test
    public void fibonacci17WithCallback() throws Exception {
        Future<Long> fibonacci = webTarget.path("/fibonacci/17").request(MediaType.TEXT_PLAIN_TYPE).async().get(new InvocationCallback<Long>() {
            @Override
            public void completed(Long aLong) {
                LOGGER.log(Level.INFO, "Completed Fibonacci 17 with {0}.", aLong);
            }

            @Override
            public void failed(Throwable throwable) {
                LOGGER.log(Level.WARNING, "Completed Fibonacci 17 with error.", throwable);
            }
        });
        assertEquals(1597, (long) fibonacci.get());
    }

    @Test
    public void fibonacci3_4_5_6_8_21() throws Exception {

        CompletableFuture<Long> fibonacci =
                Futures.toCompletable(webTarget.path("/fibonacci/{i}").resolveTemplate("i", 3)
                        .request(MediaType.TEXT_PLAIN_TYPE).async().get(Long.class))
                        .thenApply(i -> webTarget.path("/fibonacci/{i}").resolveTemplate("i", i + 2)
                                .request(MediaType.TEXT_PLAIN_TYPE).get(Long.class))
                        .thenApply(i -> webTarget.path("/fibonacci/{i}").resolveTemplate("i", i + 2)
                                .request(MediaType.TEXT_PLAIN_TYPE).get(Long.class))
                        .thenApply(i -> webTarget.path("/fibonacci/{i}").resolveTemplate("i", i + 1)
                                .request(MediaType.TEXT_PLAIN_TYPE).get(Long.class))
                        .thenApply(i -> webTarget.path("/fibonacci/{i}").resolveTemplate("i", i)
                                .request(MediaType.TEXT_PLAIN_TYPE).get(Long.class))
                        .thenApply(i -> webTarget.path("/fibonacci/{i}").resolveTemplate("i", i)
                                .request(MediaType.TEXT_PLAIN_TYPE).get(Long.class));

        assertEquals(10946, (long) fibonacci.get());
    }

    @Test
    public void fibonacci49WithCallback() throws Exception {
        Future<Response> fibonacci = webTarget.path("/fibonacci/49").request().async().get(new InvocationCallback<Response>() {
            @Override
            public void completed(Response Response) {
                LOGGER.log(Level.INFO, "Completed Fibonacci 49 with {0}.", Response);
            }

            @Override
            public void failed(Throwable throwable) {
                LOGGER.log(Level.WARNING, "Completed Fibonacci 49 with error.", throwable);
            }
        });
        assertEquals(202, fibonacci.get().getStatus());
    }

    /**
     * Transforms Future<T> to CompletableFuture<T>.
     */
    static class Futures {
        static <T> CompletableFuture<T> toCompletable(Future<T> future) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
