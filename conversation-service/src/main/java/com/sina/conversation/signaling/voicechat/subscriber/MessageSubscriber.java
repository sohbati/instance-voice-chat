package com.sina.conversation.signaling.voicechat.subscriber;

import com.sina.conversation.infrastructure.pubsub.PubSubMessage;
import com.sina.conversation.infrastructure.pubsub.PubSubMessageType;
import com.sina.conversation.infrastructure.pubsub.PubSubTopicList;
import com.sina.conversation.signaling.voicechat.model.ReadyToVoiceChatUserModel;
import com.sina.conversation.signaling.voicechat.onlineuser.OnlineUserManagement;
import com.sina.conversation.signaling.voicechat.websocket.VoiceChatResourceWebSocket;
import io.quarkus.logging.Log;
import io.quarkus.vertx.ConsumeEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class MessageSubscriber {

    @Inject
    OnlineUserManagement onlineUserManagement;

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

    @ConsumeEvent(PubSubTopicList.SET_OFFER_SDP_TO_SECOND_USER_REMOTE_DESCRIPTION_TOPIC)
    public void setOfferToSecondUser(PubSubMessage pubSubMessage) {
        webSocket.sendToUser(pubSubMessage);
    }

    @ConsumeEvent(PubSubTopicList.USER_LEFT_TOPIC)
    public void userLeft(String userId) {
        onlineUserManagement.removeUser(userId);
    }

    @ConsumeEvent(PubSubTopicList.SEND_ANSWER_SDP_TO_FIRST_USER_TOPIC)
    public void sendAnswerSdpToFirstUser(String secondUserId) {
        ReadyToVoiceChatUserModel secondUser = onlineUserManagement.getReadyToVoiceChatUser(secondUserId);
        ReadyToVoiceChatUserModel firstUser =
                onlineUserManagement.getReadyToVoiceChatUser(secondUser.getPartnerUserId());

        PubSubMessage message = new PubSubMessage(
                PubSubMessageType.SEND_ANSWER_SDP_TO_FIRST_USER,
                secondUser.getUserAnswerSessionDescriptionProtocol(),
                firstUser.getUserId());
        webSocket.sendToUser(message);
        Log.info(PubSubMessageType.SEND_ANSWER_SDP_TO_FIRST_USER + " Done. userId:" + firstUser.getUserId());
    }

    @ConsumeEvent(PubSubTopicList.SEND_CANDIDATE_TO_PARTNER_USER_TOPIC)
    public void sendCandidateToPartnerUser(PubSubMessage pubSubMessage) {
        webSocket.sendToUser(pubSubMessage);
    }

    @ConsumeEvent(PubSubTopicList.CONNECTED_TOPIC)
    public void connected(PubSubMessage pubSubMessage) {
        String userId = pubSubMessage.userId();
        onlineUserManagement.changeUserStatusToConnected(userId);
    }


}
