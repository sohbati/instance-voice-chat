package com.sina.conversation.signaling.voicechat.service;


import com.sina.conversation.signaling.voicechat.model.InstanceVoiceChatModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

@Log4j2
@ApplicationScoped
public class VoiceChatSignalingService {
    private static Set<InstanceVoiceChatModel> instanceVoiceChatUserMap = new HashSet<>();
    //log.info("tet");
    public void setUserOfferSessionDescription(String userId, String sessionDescriptionProtocol) {

        InstanceVoiceChatModel chatModel =
                new InstanceVoiceChatModel(userId, sessionDescriptionProtocol, null, null);
        instanceVoiceChatUserMap.add(chatModel);
    }
}
