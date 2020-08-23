package com.company;

import com.company.model.CustomEvent;
import com.company.model.Producer;
import com.company.model.PubSub;
import com.company.model.Subscriber;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        //Initialize Broker
        PubSub broker = new PubSub();

        //Initialize EventManager which is subscriber and listen to various events from the client
        Subscriber eventmanager = new Subscriber();
        eventmanager.listen("connect", 0);
        eventmanager.listen("ping", 1);


        //Linking Subscriber to Broker
        broker.subscribers.add(eventmanager);

        //Client 1 producing
        //connect event -> for letting subscriber know of the clients presence
        //ping event -> to know the client of its availability within a certain timeduration
        Producer firstClient = new Producer();
        CustomEvent connectevent = new CustomEvent("connect","10.2.3.4");
        CustomEvent pingevent = new CustomEvent("ping","10.2.3.4");

        //Client 1 sending connect and ping events
        firstClient.send(connectevent, broker);
        firstClient.send(pingevent, broker);

        //Client 2 sending connect events only
        Producer secondclient = new Producer();
        CustomEvent connectevent2 = new CustomEvent("connect","192.2.3.4");
        secondclient.send(connectevent2, broker);

        //Broker forwarding message to its subscribers
        broker.connect();


        //Start processing by event manager
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            eventmanager.process();
        });
        executorService.shutdown();

        /*ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        final Future handler = executor.submit(()-> {
            try {
                eventmanager.process();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executor.schedule(() -> handler.cancel(true), 10000, TimeUnit.MILLISECONDS);
        executor.shutdown();*/



        //Second client generates ping event after 5 seconds
        Thread.sleep(5000);
        System.out.println("Publishing ping event after 5 seconds");
        CustomEvent pingevent2 = new CustomEvent("ping","192.2.3.4");
        secondclient.send(pingevent2, broker);

        //Broker forward to Subscriber
        broker.connect();
    }
}
