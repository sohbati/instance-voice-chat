package com.sina.conversation.signaling.voicechat.api.admin;

import com.sina.conversation.signaling.voicechat.websocket.WebSocketSessionManager;
import jakarta.inject.Inject;
import jakarta.websocket.Session;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;

@Path("/sessions")
public class UserSessions {

    @Inject
    WebSocketSessionManager sessionManager;
    @GET
    public List<String> getAll() {
        return sessionManager.getSessions().stream().toList();
    }

    @GET
    @Path("/{userId}")
    public Session getUserInfo(@PathParam("userId") String userId) {
        return sessionManager.getSession(userId);
    }
}
