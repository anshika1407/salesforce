package com.company.model;

public class Producer {

  public void send(CustomEvent event , PubSub broker)
  {
    broker.buffer.add(event);
  }

}
