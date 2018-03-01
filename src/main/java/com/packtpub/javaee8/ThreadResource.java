package com.packtpub.javaee8;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("thread")
@Produces({MediaType.APPLICATION_JSON})
public class ThreadResource {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    @GET
    public void calculate(@Suspended final AsyncResponse asyncResponse) {
        asyncResponse.setTimeout(5, TimeUnit.SECONDS);

        final String requestThreadName = getCurrentThreadName();

        new Thread(() -> {
            try {
                // simulate heavy processing here
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "Could not wait for 3s.", e);
            }

            final String responseThreadName = getCurrentThreadName();

            Map<String, String> response = new HashMap<>();
            response.put("requestThread", requestThreadName);
            response.put("responseThread", responseThreadName);

            asyncResponse.resume(Response.ok(response).build());
        }).start();
    }

    private String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }
}
