package com.company.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


public class Subscriber {

  public BlockingQueue<CustomEvent> subscriberEvents = new LinkedBlockingQueue<>();

  //max no of topics a subscriber can subscribe to is 2 in our case
  public String[] topics = new String[2];

  public void listen(String topic, int index)
  {
    topics[index] = topic;
  }

  public void process() {
    CustomEvent event;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    try {
      //System.out.println("Debug log "+ dtf.format(LocalDateTime.now()));
      while(true)
      {
        event = subscriberEvents.poll(10, TimeUnit.SECONDS);
        if(event == null)
        {
          System.out.println("Event Manager exiting after INACTIVITY for 10 seconds at " + dtf.format(LocalDateTime.now()));
          return;
        }
        if (event.getTopic() == "connect") {
          LocalDateTime now = LocalDateTime.now();
          System.out.println(
              "Received connect event for a client " + event.getPayload() + " at " + dtf
                  .format(now));
        } else if (event.getTopic() == "ping") {
          LocalDateTime now = LocalDateTime.now();
          System.out.println(
              "Received ping event for a client " + event.getPayload() + " at " + dtf
                  .format(now));
        }
      }
    }
    catch(InterruptedException ex)
    {
      System.out.println("Received an interrupt");
    }


  }



}
