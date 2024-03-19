package com.sina.conversation.infrastructure.pubsub;

public class PubSubTopicList {
    private PubSubTopicList() {

    }
    public static final String OFFER_TOPIC = "OFFER";
    public static final String CANDIDATE_TOPIC = "ADD_CANDIDATE";
    public static final String SET_OFFER_SDP_TO_SECOND_USER_REMOTE_DESCRIPTION_TOPIC = "SET_OFFER_SDP_TO_SECOND_USER_REMOTE_DESCRIPTION";
    public static final String USER_LEFT_TOPIC = "USER_LEFT";
    public static final String ANSWER_SDP_TOPIC = "ANSWER_SDP";
    public static final String SEND_ANSWER_SDP_TO_FIRST_USER_TOPIC = "SEND_ANSWER_SDP_TO_FIRST_USER";
    public static final String SEND_CANDIDATE_TO_PARTNER_USER_TOPIC = "SEND_CANDIDATE_TO_SECOND_USER";
    public static final String CONNECTED_TOPIC = "CONNECTED";
}
