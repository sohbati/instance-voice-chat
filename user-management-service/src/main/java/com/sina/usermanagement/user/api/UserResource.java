package com.sina.usermanagement.user.api;

import com.sina.usermanagement.user.api.record.QickReqisterUserRequestRecord;
import com.sina.usermanagement.user.api.record.UserRequestRecord;
import com.sina.usermanagement.user.api.record.UserResponseRecord;
import com.sina.usermanagement.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;

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
    @Path("/{userName}/user-info")
    public UserResponseRecord getUserInfo(String userName) {
        return userService.getUser(userName);
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
