package com.sina.conversation.infrastructure.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            throw new RuntimeException(e);
        }

        if (pubSubMessage.type().equals(PubSubMessageType.OFFER)) {
            addNewUserOfferSDP(pubSubMessage);
        }else if (pubSubMessage.type().equals(PubSubMessageType.CANDIDATE)) {
            addCandidate(pubSubMessage);
        }else if (pubSubMessage.type().equals(PubSubMessageType.ANSWER_SDP)) {
            answerSdp(pubSubMessage);
        }
    }

    public void matchTowPartnersMessage(String firstUser, String secondUser) {
        PubSubMessage message = new PubSubMessage(PubSubMessageType.MATCH_TWO_PARTNER, secondUser, firstUser);
        eventBus.send(PubSubTopicList.MATCH_TWO_PARTNER_TOPIC, message);
    }

    private void addNewUserOfferSDP(PubSubMessage message) {
        eventBus.send(PubSubTopicList.OFFER_TOPIC, message);
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
    }

    public void userLeftMessage(String userId) {
        eventBus.send(PubSubTopicList.USER_LEFT_TOPIC, userId);

    }
}
