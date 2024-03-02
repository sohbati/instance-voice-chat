package com.sina.conversation.signaling.voicechat.model;


import java.util.List;

public record InstanceVoiceChatModel(
        String userId,
        String userOfferSessionDescription,
        String userAnswerSessionDescription,
        List<String> candidates
)
{

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        InstanceVoiceChatModel other = (InstanceVoiceChatModel) obj;
        return userId == other.userId;
    }
}
