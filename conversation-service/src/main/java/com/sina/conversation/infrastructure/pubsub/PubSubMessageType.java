package com.sina.conversation.infrastructure.pubsub;

public enum PubSubMessageType {
    OFFER(PubSubTopicList.OFFER_TOPIC),
    CANDIDATE(PubSubTopicList.CANDIDATE_TOPIC),
    MATCH_TWO_PARTNER(PubSubTopicList.MATCH_TWO_PARTNER_TOPIC),
    SET_OFFER_SDP_TO_SECOND_USER_REMOTE_DESCRIPTION(PubSubTopicList.SET_OFFER_SDP_TO_SECOND_USER_REMOTE_DESCRIPTION_TOPIC),
    ANSWER_SDP(PubSubTopicList.ANSWER_SDP_TOPIC),

    USER_LEFT(PubSubTopicList.USER_LEFT_TOPIC);

    String name;
    PubSubMessageType(String messageName) {
        this.name = messageName;
    }
}
