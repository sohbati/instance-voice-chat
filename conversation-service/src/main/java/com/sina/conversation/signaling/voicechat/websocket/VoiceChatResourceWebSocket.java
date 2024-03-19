package com.sina.conversation.signaling.voicechat.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sina.conversation.infrastructure.exception.ApplicationException;
import com.sina.conversation.infrastructure.exception.ErrorCodeEnum;
import com.sina.conversation.infrastructure.pubsub.MessagePublisher;
import com.sina.conversation.infrastructure.pubsub.PubSubMessage;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;


@ServerEndpoint("/users/{userId}/voice-chat-signaling")
public class VoiceChatResourceWebSocket {

    @Inject
    WebSocketSessionManager sessionManager;
    @Inject
    MessagePublisher messagePublisher;

    @Inject
    ObjectMapper objectMapper;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        Log.info(String.format("User %s joined", userId));
        sessionManager.addUserSession(userId, session);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("userId") String userId) {
        if(Log.isDebugEnabled()) {
            Log.debug(String.format("Received Message: %s", message));
        }
        messagePublisher.websocketMessageDispatcher(message);
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        sessionManager.removeSession(userId);
        messagePublisher.userLeftMessage(userId);
        Log.info("User " + userId + " left");
    }

    @OnError
    public void onError(Session session, @PathParam("userId") String userId, Throwable throwable) {
        sessionManager.removeSession(userId);
        Log.info("User " + userId + " left on error: " + throwable);
    }



    public void sendToUser(PubSubMessage message) {
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new ApplicationException(ErrorCodeEnum.JSON_DECODE_EXCEPTION, e);
        }

        Session userSession = sessionManager.getSession(message.userId());
        userSession.getAsyncRemote().sendObject(jsonString);
    }
}
