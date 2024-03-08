package com.sina.conversation.signaling.voicechat.socket.model;


public record UserOfferModel(
        String userOfferSessionDescription,
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
        UserOfferModel other = (UserOfferModel) obj;
        return userId == other.userId;
    }
}
