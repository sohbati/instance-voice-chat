package com.sina.conversation.infrastructure.pubsub;

public enum PubSubMessageType {
    OFFER(PubSubTopicList.OFFER_TOPIC),
    CANDIDATE(PubSubTopicList.CANDIDATE_TOPIC),
    SET_OFFER_SDP_TO_SECOND_USER_REMOTE_DESCRIPTION(PubSubTopicList.SET_OFFER_SDP_TO_SECOND_USER_REMOTE_DESCRIPTION_TOPIC),
    ANSWER_SDP(PubSubTopicList.ANSWER_SDP_TOPIC),
    SEND_ANSWER_SDP_TO_FIRST_USER(PubSubTopicList.SEND_ANSWER_SDP_TO_FIRST_USER_TOPIC),
    SEND_CANDIDATE_TO_PARTNER_USER(PubSubTopicList.SEND_CANDIDATE_TO_PARTNER_USER_TOPIC),
    USER_LEFT(PubSubTopicList.USER_LEFT_TOPIC),
    CONNECTED(PubSubTopicList.CONNECTED_TOPIC);

    String name;
    PubSubMessageType(String messageName) {
        this.name = messageName;
    }
}
