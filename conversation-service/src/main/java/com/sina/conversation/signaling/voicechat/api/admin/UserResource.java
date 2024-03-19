package com.sina.conversation.signaling.voicechat.api.admin;

import com.sina.conversation.signaling.voicechat.model.ReadyToVoiceChatUserModel;
import com.sina.conversation.signaling.voicechat.service.UsersService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;

@Path("/users")
public class UserResource {

    @Inject
    UsersService usersService;
    @GET
    public List<String> getAllOnlineUsers() {
        return usersService.getAllOnlineUsers();
    }

    @GET
    @Path("/{userId}")
    public ReadyToVoiceChatUserModel getUserInfo(@PathParam("userId") String userId) {
        return usersService.getUserInfo(userId);
    }
}
