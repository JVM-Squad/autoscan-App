package com.example.munchkin.controller;

import com.example.munchkin.MessageFormat.MessageFormatter;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.ConnectToServerView;
import org.json.JSONException;
import org.json.JSONObject;



public class ConnectToServerController extends BaseController {

    private WebSocketClientModel model;
    private ConnectToServerView view;


    public ConnectToServerController(WebSocketClientModel model, ConnectToServerView view) {
        super(model);
        this.model = model;
        this.view = view;
        setupController();
    }

    private void setupController() {
        model.connectToServer(this::messageReceivedFromServer);
    }

    public void reconnectToServer() {
        model.connectToServer(this::messageReceivedFromServer);
    }

    public void registerUserMessage(String username) {
        String message = MessageFormatter.registerUserMessage(username);
        model.sendMessageToServer(message);
    }



    private void messageReceivedFromServer(String message) {

    }

    @Override
    public void handleMessage(String message) {
        try {
            JSONObject jsonResponse = new JSONObject(message);
            String messageType = jsonResponse.getString("type");
            switch (messageType) {
                case "REGISTER_USERNAME":
                    handleRegisterUsernameMessage(jsonResponse);
                    break;
                case "LOBBY_ASSIGNED":
                    handleLobbyAssignedMessage(jsonResponse);
                    break;

                default:
                    break;
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleMessage/ConnectToServerController");
        }
    }



    private void handleRegisterUsernameMessage(JSONObject jsonResponse) {

        String accepted = "accepted";
        try{
            String serverResponse = jsonResponse.getString("response");

            if(serverResponse.equals(accepted)){
               view.updateServerResponse(serverResponse);
            }
            else {
                throw new IllegalArgumentException("User konnte nicht erstellt werden");
            }
        }
        catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleRegisterUsernameMessage/ConnectToServerController");
        }

    }
    private void handleLobbyAssignedMessage(JSONObject jsonResponse) {

        try {
            String type = jsonResponse.getString("type");

        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei der Verarbeitung der Lobby-Zuweisungsnachricht", e);
        }

    }
}
