package com.sina.conversation.signaling.voicechat.socket.model;


public record WebsocketData(
        WebsocketDataType type,
        String value,
        String userId
) {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        WebsocketData other = (WebsocketData) obj;
        return userId == other.userId;
    }
}
