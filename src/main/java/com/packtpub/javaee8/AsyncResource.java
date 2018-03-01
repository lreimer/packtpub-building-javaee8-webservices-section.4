package com.packtpub.javaee8;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@Path("async")
@Produces({MediaType.APPLICATION_JSON})
public class AsyncResource {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    private LinkedBlockingQueue<AsyncResponse> responses = new LinkedBlockingQueue<>();

    @GET
    public void lock(@Suspended final AsyncResponse asyncResponse) throws InterruptedException {
        String currentThreadName = getCurrentThreadName();
        LOGGER.log(Level.INFO, "Locking {0} with thread {1}.", new Object[]{asyncResponse, currentThreadName});

        asyncResponse.setTimeout(5, TimeUnit.SECONDS);
        asyncResponse.setTimeoutHandler((response) -> {
            responses.remove(response);
            response.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE).build());
        });

        responses.put(asyncResponse);
    }

    @DELETE
    public Response unlock() {
        String currentThreadName = getCurrentThreadName();
        AsyncResponse asyncResponse = responses.poll();

        if (asyncResponse != null) {
            LOGGER.log(Level.INFO, "Unlocking {0} with thread {1}.", new Object[]{asyncResponse, currentThreadName});
            asyncResponse.resume(Response.ok(Collections.singletonMap("currentThread", currentThreadName)).build());
        }

        return Response.noContent().build();
    }

    private String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }
}
