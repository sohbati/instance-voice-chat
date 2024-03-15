package com.sina.conversation.signaling.voicechat.websocket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import jakarta.websocket.Session;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class WebSocketSessionManager {
    private static Map<String, Session> sessions = new ConcurrentHashMap<>();

    public void addUserSession(String userId, Session session) {
        sessions.put(userId, session);
    }

    public Session getSession(String userId) {
        return sessions.get(userId);
    }

    public void removeSession(String userId) {
        sessions.remove(userId);
    }

    public Set<String> getSessions() {
        return sessions.keySet();
    }
}
