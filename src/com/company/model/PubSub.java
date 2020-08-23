package com.company.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PubSub {

  //this receive all the messages from different clients and acts as buffer
  public Queue<CustomEvent> buffer = new LinkedList<>();

  //Broker needs to know of all of his subscribers so that they can be informed if there are any messages
  public List<Subscriber> subscribers = new LinkedList<>();

  //this function passes all the events in the broker to the respective subscribers
  public void connect() throws InterruptedException {
    while(!buffer.isEmpty()) {

      CustomEvent event = buffer.remove();
      for (Subscriber sub : subscribers) {
        for(int i=0; i< sub.topics.length; i++) {
          if (event.getTopic() == sub.topics[i]) {
            //System.out.println("Pushing to subscriberevents queue");
            sub.subscriberEvents.put(event);
          }
        }
      }
    }
  }


}
