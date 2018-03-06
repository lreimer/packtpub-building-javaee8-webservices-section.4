package com.packtpub.javaee8;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

@ApplicationScoped
@Path("/completable")
public class CompletableFibonacciResource {

    // TODO uncomment me
    // @Resource
    // private ManagedExecutorService executorService;

    @GET
    @Path("/{i}")
    public void completable(@Suspended final AsyncResponse asyncResponse, @PathParam("i") final int i) {
        // TODO implement me using CompletableFuture
    }

}
