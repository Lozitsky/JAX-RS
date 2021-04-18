package com.kirilo.ws;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("tutorials")
@ApplicationScoped
public class ItemController {

    @Inject
    private TutorialService tutorialService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTutorials() {
        return Response.ok(tutorialService.findAllItems()).build();
    }

    @GET
    @Path("{id}")
    public Response getTutorialById(@PathParam("id") String id) {
        Item item = tutorialService.findItemById(id);
        if (item == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(item).build();
    }
}
