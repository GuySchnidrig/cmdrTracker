package com.example.cmdrtracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Life total TextViews and player name TextViews
    TextView player1Life, player2Life, player3Life, player4Life;
    TextView player1Name, player2Name, player3Name, player4Name;
    TextView player1Deck, player2Deck, player3Deck, player4Deck;

    private static final int PLAYER_INPUT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Life TextViews
        player1Life = findViewById(R.id.player1Life);
        player2Life = findViewById(R.id.player2Life);
        player3Life = findViewById(R.id.player3Life);
        player4Life = findViewById(R.id.player4Life);

        // Initialize Name TextViews
        player1Name = findViewById(R.id.player1Name);
        player2Name = findViewById(R.id.player2Name);
        player3Name = findViewById(R.id.player3Name);
        player4Name = findViewById(R.id.player4Name);

        // Initialize Name TextViews
        player1Deck = findViewById(R.id.player1Deck);
        player2Deck = findViewById(R.id.player2Deck);
        player3Deck = findViewById(R.id.player3Deck);
        player4Deck = findViewById(R.id.player4Deck);

        // Initialize Buttons and set click listeners for Player 1
        Button player1Plus = findViewById(R.id.player1Plus);
        Button player1Minus = findViewById(R.id.player1Minus);

        player1Plus.setOnClickListener(v -> updateLife(player1Life, 1));
        player1Minus.setOnClickListener(v -> updateLife(player1Life, -1));

        // Initialize Buttons and set click listeners for Player 2
        Button player2Plus = findViewById(R.id.player2Plus);
        Button player2Minus = findViewById(R.id.player2Minus);

        player2Plus.setOnClickListener(v -> updateLife(player2Life, 1));
        player2Minus.setOnClickListener(v -> updateLife(player2Life, -1));

        // Initialize Buttons and set click listeners for Player 3
        Button player3Plus = findViewById(R.id.player3Plus);
        Button player3Minus = findViewById(R.id.player3Minus);

        player3Plus.setOnClickListener(v -> updateLife(player3Life, 1));
        player3Minus.setOnClickListener(v -> updateLife(player3Life, -1));

        // Initialize Buttons and set click listeners for Player 4
        Button player4Plus = findViewById(R.id.player4Plus);
        Button player4Minus = findViewById(R.id.player4Minus);

        player4Plus.setOnClickListener(v -> updateLife(player4Life, 1));
        player4Minus.setOnClickListener(v -> updateLife(player4Life, -1));

        // Start PlayerInputActivity to get player names
        Intent intent = new Intent(MainActivity.this, PlayerInputActivity.class);
        startActivityForResult(intent, PLAYER_INPUT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLAYER_INPUT_REQUEST && resultCode == RESULT_OK && data != null) {
            // Get player names from the intent and set them to the TextViews
            player1Name.setText(data.getStringExtra("player1Name"));
            player2Name.setText(data.getStringExtra("player2Name"));
            player3Name.setText(data.getStringExtra("player3Name"));
            player4Name.setText(data.getStringExtra("player4Name"));

            player1Deck.setText(data.getStringExtra("player1Deck"));
            player2Deck.setText(data.getStringExtra("player2Deck"));
            player3Deck.setText(data.getStringExtra("player3Deck"));
            player4Deck.setText(data.getStringExtra("player4Deck"));
        }
    }

    // Helper method to update the life total
    private void updateLife(TextView playerLife, int change) {
        int currentLife = Integer.parseInt(playerLife.getText().toString());
        currentLife += change;
        playerLife.setText(String.valueOf(currentLife));
    }
}
