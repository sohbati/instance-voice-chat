package com.sina.conversation.signaling.voicechat.service;

import com.sina.conversation.signaling.voicechat.model.UserOfferAnswerAndCandidateModel;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;

@ApplicationScoped
public class VoiceChatSignalingHelper {
    private static Map<String, UserOfferAnswerAndCandidateModel> instanceVoiceChatUserMap = new HashMap<>();

    public void addOfferSDP(String userId, String offerSDP) {
        instanceVoiceChatUserMap.remove(userId);
        UserOfferAnswerAndCandidateModel model =
                UserOfferAnswerAndCandidateModel.withOfferAndCandidates(userId, offerSDP, null);
        instanceVoiceChatUserMap.put(userId, model);
    }

    public void addAnswerSDP(String userId, String answerSDP) {
        UserOfferAnswerAndCandidateModel model = instanceVoiceChatUserMap.get(userId);
        model.setUserAnswerSessionDescription(answerSDP);
    }

    public void addCandidate(String userId, List<String> candidate) {
        UserOfferAnswerAndCandidateModel model = instanceVoiceChatUserMap.get(userId);
        model.setCandidates(candidate);
    }

    public UserOfferAnswerAndCandidateModel removeUser(String userId) {
        return instanceVoiceChatUserMap.remove(userId);
    }
}
