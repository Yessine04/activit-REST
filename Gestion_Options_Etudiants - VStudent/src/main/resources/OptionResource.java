```java
        package resources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/options")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OptionResource {

    private static final OptionBusiness optionBusiness =
            new OptionBusiness();

    // GET /options
    // GET /options?domaine=Informatique
    @GET
    public List<Option> getOptions(
            @QueryParam("domaine") String domaine) {

        if (domaine != null && !domaine.trim().isEmpty()) {
            return optionBusiness.getOptionsByDomaine(domaine);
        }

        return optionBusiness.getListeOptions();
    }

    // GET /options/1
    @GET
    @Path("/{code}")
    public Response getOptionByCode(
            @PathParam("code") int code) {

        Option option = optionBusiness.getOptionByCode(code);

        if (option == null) {
            return Response.status(
                    Response.Status.NOT_FOUND).build();
        }

        return Response.ok(option).build();
    }

    // POST /options
    @POST
    public Response addOption(Option option) {

        if (option == null) {
            return Response.status(
                    Response.Status.BAD_REQUEST).build();
        }

        if (optionBusiness.getOptionByCode(
                option.getCodeOption()) != null) {
            return Response.status(
                    Response.Status.CONFLICT).build();
        }

        optionBusiness.addOption(option);

        return Response.status(
                        Response.Status.CREATED)
                .entity(option).build();
    }

    // PUT /options/1
    @PUT
    @Path("/{code}")
    public Response updateOption(
            @PathParam("code") int code,
            Option option) {

        if (option == null) {
            return Response.status(
                    Response.Status.BAD_REQUEST).build();
        }

        option.setCodeOption(code);

        boolean updated =
                optionBusiness.updateOption(code, option);

        if (!updated) {
            return Response.status(
                    Response.Status.NOT_FOUND).build();
        }

        return Response.ok(option).build();
    }

    // DELETE /options/1
    @DELETE
    @Path("/{code}")
    public Response deleteOption(
            @PathParam("code") int code) {

        boolean deleted =
                optionBusiness.deleteOption(code);

        if (!deleted) {
            return Response.status(
                    Response.Status.NOT_FOUND).build();
        }

        return Response.ok().build();
    }
}
```
