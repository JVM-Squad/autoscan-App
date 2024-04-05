package com.example.munchkin.model;


import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.networking.WebSocketClient;

import com.example.munchkin.networking.WebSocketMessageHandler;


import java.util.ArrayList;
import java.util.List;


public class WebSocketClientModel {

    private WebSocketClient networkHandler;
    private MessageRouter messageRouter;



    public void setMessageRouter(MessageRouter router) { // Sets the router
        this.messageRouter = router;
    }

    // Updated notify method to route messages directly, without looping through observers
    public void notifyObservers(String message) {
        if(messageRouter != null) {
            messageRouter.routeMessage(message); // Directly route the message
        } else {
            throw new IllegalStateException("MessageRouter not initialized. Cannot route messages without a valid MessageRouter.");
        }
    }


    public WebSocketClientModel() {
        networkHandler = new WebSocketClient(this);
    }

    public void connectToServer(WebSocketMessageHandler<String> messageHandler) {
        networkHandler.connectToServer(messageHandler);
    }

    public void sendMessageToServer(String msg) {
        networkHandler.sendMessageToServer(msg);
    }

}
