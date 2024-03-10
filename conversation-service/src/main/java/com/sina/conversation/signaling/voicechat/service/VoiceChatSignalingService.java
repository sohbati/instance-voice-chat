package com.sina.conversation.signaling.voicechat.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class VoiceChatSignalingService {
    @Inject
    VoiceChatSignalingHelper  voiceChatSignalingHelper;

    public void setUserOfferSessionDescription(String userId, String offerSDP) {
        voiceChatSignalingHelper.addOfferSDP(userId, offerSDP);
    }
    public void addCandidate(String userId, String candidate) {
        voiceChatSignalingHelper.addCandidate(userId, candidate);
    }
}
