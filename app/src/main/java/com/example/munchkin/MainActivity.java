package com.example.munchkin;

import android.os.Bundle;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;



import com.example.munchkin.controller.ConnectToServerController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.ConnectToServerView;

public class MainActivity extends AppCompatActivity {

    private ConnectToServerController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        ConnectToServerView view = new ConnectToServerView(this);
        WebSocketClientModel model = new WebSocketClientModel();
        controller = new ConnectToServerController(model, view);
    }


    public void sendMessage() {
        controller.sendMessage();
    }


}

