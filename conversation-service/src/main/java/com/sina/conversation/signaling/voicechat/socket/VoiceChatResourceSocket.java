package com.sina.conversation.signaling.voicechat.socket;

import com.sina.conversation.signaling.voicechat.socket.model.UserOfferModel;
import com.sina.conversation.signaling.voicechat.service.VoiceChatSignalingService;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/users/{userId}/voice-chat-signaling")
public class VoiceChatResourceSocket {

    @Inject
    private VoiceChatSignalingService voiceChatSignalingService;
    private Map<String, Session> sessions = new ConcurrentHashMap<>();

//    @POST
//    @Path("/add-user-offer-session-description")
//    @Operation(summary = "Figma Code: api#????", description = "Add User offer to available list of users")
//    public String addUserOfferSessionDescription(UserOfferModel model) {
//        voiceChatSignalingService.setUserOfferSessionDescription(model.userId(), model.userOfferSessionDescription());
//        return model.userId();
//    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        broadcast("User " + userId + " joined");
        sessions.put(userId, session);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("userId") String userId) {
        Log.info(message);
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        sessions.remove(userId);
        broadcast("User " + userId + " left");
    }

    @OnError
    public void onError(Session session, @PathParam("userId") String userId, Throwable throwable) {
        sessions.remove(userId);
        broadcast("User " + userId + " left on error: " + throwable);
    }

    private void broadcast(String message) {
        Log.info(message);
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    Log.info("Unable to send message: " + result.getException());
                }
            });
        });
    }
}
