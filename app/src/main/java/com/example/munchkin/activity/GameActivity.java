package com.example.munchkin.activity;

import android.content.Intent;
import android.os.Bundle;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.R;
import com.example.munchkin.controller.GameController;
import com.example.munchkin.controller.SpawnMonsterController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.GameView;
import com.example.munchkin.view.DiceRollView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private GameController gameController;
    private SpawnMonsterController spawnMonsterController;
    private GameView gameView;

    private ArrayList<Integer> diceResults = new ArrayList<>();

    private ActivityResultLauncher<Intent> diceRollLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game); // Make sure to use the correct layout file

        setupControllers();
        setupDiceRollLauncher();
        requestRoll();
        diceResults = getIntent().getIntegerArrayListExtra("diceResults");

        if (diceResults != null) {
            for (int result : diceResults) {
                spawnMonsterController.sendMonsterSpawnMessage("Zone" + result);
            }
        }
        /*
        Button buttonPlayerAttack = findViewById(R.id.buttonPlayerAttack);
        Button buttonMonsterAttack = findViewById(R.id.buttonMonsterAttack);

        buttonPlayerAttack.setOnClickListener(v -> {
            // Assuming you have a way to determine the monsterId and cardTypePlayed
            String monsterId = "someMonsterId";
            String cardTypePlayed = "someCardType";
            gameController.sendPlayerAttackMessage(monsterId, cardTypePlayed);
        });

        buttonMonsterAttack.setOnClickListener(v -> {
            String monsterId = "someMonsterId";
            gameController.sendMonsterAttackMessage(monsterId);
        });
         */

    }

    private void setupControllers() {
        WebSocketClientModel model = new WebSocketClientModel();
        gameView = new GameView(this);
        spawnMonsterController = new SpawnMonsterController(model, gameView);
        gameController = new GameController(model, gameView,spawnMonsterController);
        gameController.requestUsernames();


        MessageRouter router = new MessageRouter();
        router.registerController("PLAYER_ATTACK", gameController);
        router.registerController("MONSTER_ATTACK", gameController);
        router.registerController("SPAWN_MONSTER", spawnMonsterController);
        router.registerController("REQUEST_USERNAMES", gameController);
        model.setMessageRouter(router);
    }

    private void processDiceResults() {
        for (int zone : diceResults) {
            spawnMonsterController.sendMonsterSpawnMessage("Zone" + zone);
        }
        diceResults.clear();
    }

    private void requestRoll() {
        Intent intent = new Intent(this, DiceRollView.class);
        diceRollLauncher.launch(intent);
    }

    private void setupDiceRollLauncher() {
        diceRollLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        ArrayList<Integer> newResults = result.getData().getIntegerArrayListExtra("diceResults");
                        if (newResults != null) {
                            diceResults.addAll(newResults);
                            gameController.onDiceRolled(newResults.stream().mapToInt(i->i).toArray());
                        }
                    }
                }
        );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            ArrayList<Integer> results = data.getIntegerArrayListExtra("diceResults");
            if (results != null) {
                diceResults.addAll(results);
                processDiceResults();
            }
        }
    }

}
