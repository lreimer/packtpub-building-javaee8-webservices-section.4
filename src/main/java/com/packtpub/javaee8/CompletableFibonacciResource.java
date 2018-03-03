package com.packtpub.javaee8;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.concurrent.CompletableFuture;

import static com.packtpub.javaee8.FibonacciResource.fibonacci;

@ApplicationScoped
@Path("/completable")
public class CompletableFibonacciResource {

    @Resource
    private ManagedExecutorService executorService;

    @GET
    @Path("/{i}")
    public void completable(@Suspended final AsyncResponse asyncResponse, @PathParam("i") final int i) {
        CompletableFuture
                .runAsync(() -> fibonacci(i), executorService)
                .thenApply(asyncResponse::resume);
    }

}
