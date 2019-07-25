package com.prowess.services.examples.schemas;

public class PublisherPayload {

    private PubsubMessage[] messages;

    public PubsubMessage[] getMessages() {
        return messages;
    }

    public void setMessages(PubsubMessage[] messages) {
        this.messages = messages;
    }
}
