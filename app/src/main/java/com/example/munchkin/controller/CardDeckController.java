package com.example.munchkin.controller;

import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import com.example.munchkin.DTO.ActionCardDTO;
import com.example.munchkin.MessageFormat.MessageFormatter;

import com.example.munchkin.Player.PlayerHand;
import com.example.munchkin.activity.CarddeckActivity;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.CarddeckView;

import org.json.JSONException;
import org.json.JSONObject;

public class CardDeckController extends BaseController {

    WebSocketClientModel websocket;
    private PlayerHand playerHand;
    private CarddeckView carddeckView;

    private CarddeckActivity carddeckActivity;

    public CardDeckController(WebSocketClientModel model, CarddeckView carddeckView) {
        super(model);
        websocket=model;
        this.playerHand = new PlayerHand();
        this.carddeckView = carddeckView;
    }

    public void switchcardMeassage(String text, String id) {

        ActionCardDTO toremove = new ActionCardDTO();
        for(ActionCardDTO a: playerHand.getCards()){
            if(a.getId() == Integer.parseInt(id)){
                toremove=a;
            }
        }
        playerHand.removeCard(toremove);

        if(text.equals("Kartenstapel")) {
            String message = MessageFormatter.createSwitchCardsDeckMessage(id);
            System.out.println(message);
            websocket.sendMessageToServer(message);
        }
        else{
            System.out.println("Das muss noch ausimplementiert werden Carddeckcontroller java");
        }

    }


    @Override
    public void handleMessage(String message) {
        try {
            System.out.println("Carddeckcontroller message received");
            JSONObject jsonResponse = new JSONObject(message);
            String messageType = jsonResponse.getString("type");
            switch (messageType) {
                case "SWITCH_CARD_PLAYER_RESPONSE":
                case "SWITCH_CARD_DECK_RESPONSE":
                    updatehanddeck(jsonResponse);
                    break;
                case "REQUEST_USERNAMES":
                    //handleUserName(jsonResponse);
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleMessage/CardDeckController");
        }
    }

    private void updatehanddeck(JSONObject jsonResponse) {


        try{

            int id = Integer.parseInt(jsonResponse.getString("id"));
            String name = jsonResponse.getString("name");
            int zone = Integer.parseInt(jsonResponse.getString("zone"));
            ActionCardDTO karte = new ActionCardDTO(name, zone,id);
            System.out.println(karte.getName());
            playerHand.addCard(karte);


            //carddeckView.updatenachtauschen();


        }
        catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei updatehanddeck/Cardddeckcontroller");
        }

    }
    /*public void sendSwitchCardsPlayerMessage(String username, String givenCard, String receivedCard) {
        String message = MessageFormatter.createSwitchCardsPlayerMessage(username, givenCard, receivedCard);
        model.sendMessageToServer(message);
    }

    public void sendSwitchCardsDeckMessage(String card) {
        String message = MessageFormatter.createSwitchCardsDeckMessage(card);
        model.sendMessageToServer(message);
    }

    public void requestUsernames() {
        String message = MessageFormatter.createUsernameRequestMessage();
        model.sendMessageToServer(message);
    }



    private void handleUserName(JSONObject jsonResponse){
        try {
            JSONArray usernamesArray = jsonResponse.getJSONArray("usernames");
            ArrayList<String> usernamesList = new ArrayList<>();
            for (int i = 0; i < usernamesArray.length(); i++) {
                usernamesList.add(usernamesArray.getString(i));
            }

            tradeCardsActivity.runOnUiThread(() -> {
                tradeCardsView.updateUsernamesSpinner(usernamesList);
            });
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleUserName/CardDeckController");
        }
    }





    private void updateCardsListWithNewCard(ActionCardDTO newCard) {
        playerHand.addCard(newCard);

        tradeCardsView.displayPlayerCards(playerHand.getCards());
        tradeCardsView.setupCardSelection();
    }

    public void tradeCardDeck(ActionCardDTO card) {

        int id = card.getId();
        String idAsString = Integer.toString(id);

        sendSwitchCardsDeckMessage(idAsString);
    }*/



}



