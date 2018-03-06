package com.packtpub.javaee8;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/fibonacci")
public class FibonacciResource {

    static final Logger LOGGER = Logger.getLogger(FibonacciResource.class.getName());

    // TODO uncomment me
    // @Resource
    // private ManagedExecutorService executorService;

    @GET
    @Path("/{i}")
    public void fibonacci(@Suspended final AsyncResponse asyncResponse, @PathParam("i") final int i) {
        LOGGER.log(Level.INFO, "Calculating Fibonacci {0} for {1}.", new Object[]{i, asyncResponse});

        // TODO implement Fibonacci calculation with executor
    }

    static long fibonacci(int n) {
        if (n <= 1) return n;
            // very inefficient
        else return fibonacci(n - 1) + fibonacci(n - 2);
    }

    // TODO add CompletionCallback and ConnectionCallback implementations

}
