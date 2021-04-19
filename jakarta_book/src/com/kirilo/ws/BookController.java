package com.kirilo.ws;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Path("books")
@ApplicationScoped
public class BookController {

    @Inject
    private BookService bookService;

    @Context
    private HttpServletResponse context;

    @GET
    @Path("xml")
    @Produces(MediaType.APPLICATION_XML)
    public List<Book> getAllBooksXML() {
        return bookService.findAllBooks();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        return Response.ok(bookService.findAllBooks()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@PathParam("id") String id) {
        Book book = bookService.findBookById(id);

        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(book).build();
    }

    @GET
    @Path("stream")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooksStream() {
        return Response.ok(bookService.createBooksStream()).build();
    }

    //    https://www.adam-bien.com/roller/abien/entry/asynchronous_jax_rs_timeout_configuration
    //    https://blog.allegro.tech/2014/10/async-rest.html
    //    https://docs.jboss.org/resteasy/docs/4.5.5.Final/userguide/html/Asynchronous_HTTP_Request_Processing.html
    //    https://eclipse-ee4j.github.io/jersey.github.io/documentation/2.11/async.html
    @GET
    @Path("async_stream")
    @Produces(MediaType.APPLICATION_JSON)
    public void getAsyncBooksStream(@Suspended final AsyncResponse response) {
        response.setTimeoutHandler(asyncResponse -> asyncResponse
                .resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Operation time out.").build()));
        response.setTimeout(20, TimeUnit.SECONDS);

        new Thread(() -> {
            Response jaxrs = Response.ok(bookService.createBooksStream()).type(MediaType.APPLICATION_JSON).build();
            response.resume(jaxrs);
        }).start();
    }

    private static int numberOfSuccessResponses = 0;
    private static int numberOfFailures = 0;
    private static Throwable lastException = null;

    @GET
    @Path("callback")
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncGetWithTimeout(@Suspended final AsyncResponse asyncResponse) {
        asyncResponse.register(new CompletionCallback() {
            @Override
            public void onComplete(Throwable throwable) {
                if (throwable == null) {
                    // no throwable - the processing ended successfully
                    // (response already written to the client)
                    numberOfSuccessResponses++;
                } else {
                    numberOfFailures++;
                    lastException = throwable;
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response jaxrs = Response.ok(bookService.createBooksStream()).build();
                asyncResponse.resume(jaxrs);
            }

        }).start();
    }
}
