package io.akoserwal;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.akoserwal.model.User;
import io.quarkus.security.Authenticated;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Path("/user")
@Authenticated
@EnableSwagger2
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final User user;

    public UserResource() {
        user = new User();
        user.setEmail("tyler.karol@eupraxialabs.com");
        user.setName("Tyler Karol");
    }

    @GET
    public Response user(){
        return Response.ok(user).build();
    }
}