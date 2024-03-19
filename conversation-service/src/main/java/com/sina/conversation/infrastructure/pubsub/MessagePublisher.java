package com.sina.conversation.infrastructure.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sina.conversation.infrastructure.exception.ApplicationException;
import com.sina.conversation.infrastructure.exception.ErrorCodeEnum;
import io.quarkus.logging.Log;
import io.vertx.core.eventbus.EventBus;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class MessagePublisher {
    @Inject
    EventBus eventBus;
    @Inject
    private ObjectMapper objectMapper;

    public void websocketMessageDispatcher(String message) {
        PubSubMessage pubSubMessage;
        try {
            pubSubMessage = objectMapper.readValue(message, PubSubMessage.class);
        } catch (JsonProcessingException e) {
            throw new ApplicationException(ErrorCodeEnum.JSON_DECODE_EXCEPTION, e);
        }

        if (pubSubMessage.type().equals(PubSubMessageType.OFFER)) {
            addNewUserOfferSDP(pubSubMessage);
        }else if (pubSubMessage.type().equals(PubSubMessageType.CANDIDATE)) {
            addCandidate(pubSubMessage);
        }else if (pubSubMessage.type().equals(PubSubMessageType.ANSWER_SDP)) {
            answerSdp(pubSubMessage);
        }else if (pubSubMessage.type().equals(PubSubMessageType.CONNECTED)) {
            connected(pubSubMessage);
        }
    }

    private void addNewUserOfferSDP(PubSubMessage message) {
        eventBus.send(PubSubTopicList.OFFER_TOPIC, message);
    }
    private void connected(PubSubMessage message) {
        eventBus.send(PubSubTopicList.CONNECTED_TOPIC, message);
    }

    private void addCandidate(PubSubMessage message) {
        eventBus.send(PubSubTopicList.CANDIDATE_TOPIC, message);
    }
    private void answerSdp(PubSubMessage message) {
        eventBus.send(PubSubTopicList.ANSWER_SDP_TOPIC, message);
    }

    public void sendOfferSdpToSecondUser(String secondUser, String userSessionDescriptionProtocol) {
        PubSubMessage message = new PubSubMessage(
                PubSubMessageType.SET_OFFER_SDP_TO_SECOND_USER_REMOTE_DESCRIPTION, userSessionDescriptionProtocol, secondUser);
        eventBus.send(PubSubTopicList.SET_OFFER_SDP_TO_SECOND_USER_REMOTE_DESCRIPTION_TOPIC, message);
        Log.info(PubSubMessageType.SET_OFFER_SDP_TO_SECOND_USER_REMOTE_DESCRIPTION + " Done");

    }

    public void userLeftMessage(String userId) {
        eventBus.send(PubSubTopicList.USER_LEFT_TOPIC, userId);
    }

    public void sendAnswerSdpToFirstUser(String secondUserId) {
        eventBus.send(PubSubTopicList.SEND_ANSWER_SDP_TO_FIRST_USER_TOPIC, secondUserId);
    }

    public void sendCandidateToPartnerUser(String userId, String candidate) {
        PubSubMessage message = new PubSubMessage(PubSubMessageType.SEND_CANDIDATE_TO_PARTNER_USER, candidate, userId);

        eventBus.send(PubSubTopicList.SEND_CANDIDATE_TO_PARTNER_USER_TOPIC, message);
    }
}
