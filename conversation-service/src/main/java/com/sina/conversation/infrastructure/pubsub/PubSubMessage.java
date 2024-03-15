package com.sina.conversation.infrastructure.pubsub;


public record PubSubMessage(
        PubSubMessageType type,
        String value,
        String userId
) { }