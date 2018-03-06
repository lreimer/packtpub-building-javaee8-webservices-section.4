package com.packtpub.javaee8;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

@Path("thread")
@Produces({MediaType.APPLICATION_JSON})
public class ThreadResource {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    @GET
    public void calculate(@Suspended final AsyncResponse asyncResponse) {
        // TODO implement me
        // do some heavy processing in a separate thread
    }

    private String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }
}
