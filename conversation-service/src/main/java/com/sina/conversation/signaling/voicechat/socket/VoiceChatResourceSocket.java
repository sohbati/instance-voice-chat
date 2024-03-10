package com.sina.conversation.signaling.voicechat.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sina.conversation.signaling.voicechat.service.VoiceChatSignalingService;
import com.sina.conversation.signaling.voicechat.socket.model.WebsocketData;
import com.sina.conversation.signaling.voicechat.socket.model.WebsocketDataType;
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
    ObjectMapper objectMapper;

    @Inject
    private VoiceChatSignalingService voiceChatSignalingService;
    private Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        broadcast("User " + userId + " joined");
        sessions.put(userId, session);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("userId") String userId) {
        Log.info(message);
        messageDispatcher(message);

    }

    private void messageDispatcher(String message) {
        WebsocketData value;
        try {
            value = objectMapper.readValue(message, WebsocketData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (value.type().equals(WebsocketDataType.OFFER)) {
            voiceChatSignalingService.setUserOfferSessionDescription(value.userId(), value.value());
        }else if (value.type().equals(WebsocketDataType.CANDIDATE)) {
            voiceChatSignalingService.addCandidate(value.userId(), value.value());
        }
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
