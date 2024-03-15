package com.sina.conversation.signaling.voicechat.subscriber;

import com.sina.conversation.infrastructure.pubsub.PubSubMessage;
import com.sina.conversation.infrastructure.pubsub.PubSubTopicList;
import com.sina.conversation.signaling.voicechat.partnermatching.MatchTwoPartners;
import com.sina.conversation.signaling.voicechat.onlineuser.OnlineUserManagement;
import com.sina.conversation.signaling.voicechat.websocket.VoiceChatResourceWebSocket;
import io.quarkus.vertx.ConsumeEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class MessageSubscriber {

    @Inject
    OnlineUserManagement onlineUserManagement;

    @Inject
    MatchTwoPartners matchTwoPartners;

    @Inject
    VoiceChatResourceWebSocket webSocket;

    @ConsumeEvent(PubSubTopicList.OFFER_TOPIC)
    public void addNewUserOffer(PubSubMessage pubSubMessage) {
        onlineUserManagement.addOfferSDP(pubSubMessage.userId(), pubSubMessage.value());
    }

    @ConsumeEvent(PubSubTopicList.CANDIDATE_TOPIC)
    public void addCandidate(PubSubMessage pubSubMessage) {
        onlineUserManagement.addCandidate(pubSubMessage.userId(), pubSubMessage.value());
    }
    @ConsumeEvent(PubSubTopicList.ANSWER_SDP_TOPIC)
    public void answerSdp(PubSubMessage pubSubMessage) {
        onlineUserManagement.setAnswerSdp(pubSubMessage.userId(), pubSubMessage.value());
    }

    @ConsumeEvent(PubSubTopicList.MATCH_TWO_PARTNER_TOPIC)
    public void matchTwoPartner(PubSubMessage pubSubMessage) {
        matchTwoPartners.makeTwoUserAsPartners(pubSubMessage.userId(), pubSubMessage.value());
    }

    @ConsumeEvent(PubSubTopicList.SET_OFFER_SDP_TO_SECOND_USER_REMOTE_DESCRIPTION_TOPIC)
    public void setOfferToSecondUser(PubSubMessage pubSubMessage) {
        webSocket.sendToUser(pubSubMessage);
    }

    @ConsumeEvent(PubSubTopicList.USER_LEFT_TOPIC)
    public void userLeft(String userId) {
        onlineUserManagement.removeUser(userId);
    }


}
