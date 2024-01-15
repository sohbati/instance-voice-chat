package com.sina.usermanagement.user.api;

import com.sina.usermanagement.user.api.record.QickReqisterUserRequestRecord;
import com.sina.usermanagement.user.api.record.UserRequestRecord;
import com.sina.usermanagement.user.api.record.UserResponseRecord;
import com.sina.usermanagement.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import lombok.extern.log4j.Log4j2;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/users")
@Consumes("application/json")
@Produces("application/json")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * Figma: api#0001
     */
    @GET
    @Path("/{userId}/user-info")
    @Operation(summary = "Figma Code: api#0001", description = " return user information")
    public UserResponseRecord getUserInfo(String userId) {
        return userService.getUser(userId);
    }


    @POST
    @Path("/register")
    public UserResponseRecord registerNewUser(@Valid UserRequestRecord user) {
        String id = userService.save(user);
        return UserResponseRecord.builder().id(id).build();
    }

    @POST
    @Path("/quick-register")
    public UserResponseRecord quickRegisterNewUser(@Valid QickReqisterUserRequestRecord user) {
        String id = userService.quickSave(user);
        return UserResponseRecord.builder().id(id).build();
    }
}
