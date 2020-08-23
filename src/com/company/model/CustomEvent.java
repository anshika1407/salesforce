package com.company.model;

public class CustomEvent {

  public String getTopic() {
    return topic;
  }

  public String getPayload() {
    return payload;
  }

  private String topic;

  private String payload;

  public CustomEvent(String topic, String payload) {
    this.topic = topic;
    this.payload = payload;
  }
}
