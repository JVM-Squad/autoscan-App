package com.example.munchkin;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.munchkin.networking.WebSocketClient;

public class MainActivity extends AppCompatActivity {

    TextView textViewServerResponse;

    WebSocketClient networkHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



    Button buttonSendMsg = findViewById(R.id.sendbutton);
        buttonSendMsg.setOnClickListener(v -> sendMessage());

    textViewServerResponse = findViewById(R.id.responseTextView);

    networkHandler = new WebSocketClient();
}




    private void connectToWebSocketServer() {
        // register a handler for received messages when setting up the connection
        networkHandler.connectToServer(this::messageReceivedFromServer);
    }



    private void sendMessage() {
        connectToWebSocketServer();
        networkHandler.sendMessageToServer("test message");
    }




    private void messageReceivedFromServer(String message) {
        // TODO handle received messages
        Log.d("Network", message);
        textViewServerResponse.setText(message);
    }
}

