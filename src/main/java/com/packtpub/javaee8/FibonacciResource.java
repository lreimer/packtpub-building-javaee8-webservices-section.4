package com.packtpub.javaee8;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.ConnectionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/fibonacci")
public class FibonacciResource {

    static final Logger LOGGER = Logger.getLogger(FibonacciResource.class.getName());

    @Resource
    private ManagedExecutorService executorService;

    @GET
    @Path("/{i}")
    public void fibonacci(@Suspended final AsyncResponse asyncResponse, @PathParam("i") final int i) {
        LOGGER.log(Level.INFO, "Calculating Fibonacci {0} for {1}.", new Object[]{i, asyncResponse});

        asyncResponse.setTimeout(10, TimeUnit.SECONDS);
        asyncResponse.setTimeoutHandler((r) -> {
            r.resume(Response.accepted(UUID.randomUUID().toString()).build()); //sending HTTP 202 (Accepted)
        });

        asyncResponse.register(LoggingCompletionCallback.class);
        asyncResponse.register(LoggingConnectionCallback.class);

        executorService.execute(() -> {
            asyncResponse.resume(Response.ok(fibonacci(i)).build());
            LOGGER.log(Level.INFO, "Calculated Fibonacci for {0}.", asyncResponse);
        });
    }

    static long fibonacci(int n) {
        if (n <= 1) return n;
            // very inefficient
        else return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /**
     * A request processing callback that receives request processing completion events.
     */
    static class LoggingCompletionCallback implements CompletionCallback {

        @Override
        public void onComplete(Throwable throwable) {
            LOGGER.log(Level.INFO, "Completed processing.", throwable);
        }
    }

    /**
     * Asynchronous request processing lifecycle callback that receives connection related lifecycle events.
     * According to JSR-339 support for ConnectionCallback is OPTIONAL.
     */
    static class LoggingConnectionCallback implements ConnectionCallback {

        @Override
        public void onDisconnect(AsyncResponse disconnected) {
            LOGGER.log(Level.INFO, "Client disconnected on {0}.", disconnected);
        }
    }
}
