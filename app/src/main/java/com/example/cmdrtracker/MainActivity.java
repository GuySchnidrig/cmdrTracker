package com.example.cmdrtracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    // Life total TextViews and player name TextViews
    TextView player1Life, player2Life, player3Life, player4Life;
    TextView player1Name, player2Name, player3Name, player4Name;
    TextView player1Deck, player2Deck, player3Deck, player4Deck;
    TextView player1Time, player2Time, player3Time, player4Time;
    TextView overallTurnCountTextView; // Add TextView for overall turn count

    private static final int PLAYER_INPUT_REQUEST = 1;
    private final int[] turnOrder = {0, 2, 3, 1}; // Define the custom order
    private int currentTurnIndex = 0; // Index to track current turn in turnOrder array
    private int currentPlayerIndex;
    private int startingPositionIndex;
    private int overallTurnCount = 1; // Variable to track overall turn count
    private long[] startTimes = new long[4]; // To track the start time of each player's turn
    private boolean[] isRunning = new boolean[4]; // To check if the timer is running for each player
    private long[] totalTimes = new long[4]; // To store total times for each player

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

        // Initialize Deck TextViews
        player1Deck = findViewById(R.id.player1Deck);
        player2Deck = findViewById(R.id.player2Deck);
        player3Deck = findViewById(R.id.player3Deck);
        player4Deck = findViewById(R.id.player4Deck);

        // Initialize Time TextViews
        player1Time = findViewById(R.id.player1Time);
        player2Time = findViewById(R.id.player2Time);
        player3Time = findViewById(R.id.player3Time);
        player4Time = findViewById(R.id.player4Time);

        // Initialize Turn Count TextView
        overallTurnCountTextView = findViewById(R.id.overallTurnCountTextView);

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

        // Initialize the "Pass Turn" button
        Button passTurnButton = findViewById(R.id.passTurnButton);

        passTurnButton.setOnClickListener(v -> passTurn());

        // Start PlayerInputActivity to get player names
        Intent intent = new Intent(MainActivity.this, PlayerInputActivity.class);
        startActivityForResult(intent, PLAYER_INPUT_REQUEST);

        // Initial highlight setup
        // highlightActivePlayer();
        // updateOverallTurnCountDisplay();
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

            // Retrieve startingPositionIndex from the Intent data
            startingPositionIndex = data.getIntExtra("startingPositionIndex", 0);
            currentPlayerIndex = startingPositionIndex;

            // Initialize currentTurnIndex based on startingPositionIndex
            if (startingPositionIndex >= 0 && startingPositionIndex < turnOrder.length) {
                // Set currentTurnIndex to the startingPositionIndex directly
                currentTurnIndex = startingPositionIndex;
                // Set the currentPlayerIndex based on the turnOrder
                currentPlayerIndex = turnOrder[currentTurnIndex];
            } else {
                // Handle unexpected values if needed
                currentTurnIndex = -1; // Set to an invalid index or handle appropriately
                // Handle invalid index for currentPlayerIndex if needed
                currentPlayerIndex = -1;
            }

            // Initialize totalTimes array to zero
            for (int i = 0; i < totalTimes.length; i++) {
                totalTimes[i] = 0;
            }

            // Initialize times display
            updateDisplayTimes();

            highlightActivePlayer();
            updateOverallTurnCountDisplay();

        }
    }

    // Helper method to update the life total
    private void updateLife(TextView playerLife, int change) {
        int currentLife = Integer.parseInt(playerLife.getText().toString());
        currentLife += change;
        playerLife.setText(String.valueOf(currentLife));
    }

    // Update highlight of the active player
    private void highlightActivePlayer() {
        // Reset all TextViews to default (non-highlighted) state
        player1Name.setTextColor(ContextCompat.getColor(this, R.color.main_black));
        player1Deck.setTextColor(ContextCompat.getColor(this, R.color.main_black));
        player1Life.setTextColor(ContextCompat.getColor(this, R.color.main_black));
        player1Time.setTextColor(ContextCompat.getColor(this, R.color.main_black));

        player2Name.setTextColor(ContextCompat.getColor(this, R.color.main_black));
        player2Deck.setTextColor(ContextCompat.getColor(this, R.color.main_black));
        player2Life.setTextColor(ContextCompat.getColor(this, R.color.main_black));
        player2Time.setTextColor(ContextCompat.getColor(this, R.color.main_black));

        player3Name.setTextColor(ContextCompat.getColor(this, R.color.main_black));
        player3Deck.setTextColor(ContextCompat.getColor(this, R.color.main_black));
        player3Life.setTextColor(ContextCompat.getColor(this, R.color.main_black));
        player3Time.setTextColor(ContextCompat.getColor(this, R.color.main_black));

        player4Name.setTextColor(ContextCompat.getColor(this, R.color.main_black));
        player4Deck.setTextColor(ContextCompat.getColor(this, R.color.main_black));
        player4Life.setTextColor(ContextCompat.getColor(this, R.color.main_black));
        player4Time.setTextColor(ContextCompat.getColor(this, R.color.main_black));


        // Highlight the active player
        switch (currentPlayerIndex) {
            case 0:
                player1Name.setTextColor(ContextCompat.getColor(this, R.color.secondary_yellow));
                player1Deck.setTextColor(ContextCompat.getColor(this, R.color.secondary_yellow));
                player1Life.setTextColor(ContextCompat.getColor(this, R.color.secondary_yellow));
                player1Time.setTextColor(ContextCompat.getColor(this, R.color.secondary_yellow));
                break;
            case 1:
                player2Name.setTextColor(ContextCompat.getColor(this, R.color.secondary_yellow));
                player2Deck.setTextColor(ContextCompat.getColor(this, R.color.secondary_yellow));
                player2Life.setTextColor(ContextCompat.getColor(this, R.color.secondary_yellow));
                player2Time.setTextColor(ContextCompat.getColor(this, R.color.secondary_yellow));

                break;
            case 2:
                player3Name.setTextColor(ContextCompat.getColor(this, R.color.secondary_yellow));
                player3Deck.setTextColor(ContextCompat.getColor(this, R.color.secondary_yellow));
                player3Life.setTextColor(ContextCompat.getColor(this, R.color.secondary_yellow));
                player3Time.setTextColor(ContextCompat.getColor(this, R.color.secondary_yellow));
                break;
            case 3:
                player4Name.setTextColor(ContextCompat.getColor(this, R.color.secondary_yellow));
                player4Deck.setTextColor(ContextCompat.getColor(this, R.color.secondary_yellow));
                player4Life.setTextColor(ContextCompat.getColor(this, R.color.secondary_yellow));
                player4Time.setTextColor(ContextCompat.getColor(this, R.color.secondary_yellow));
                break;
        }
    }

    private void passTurn() {
        // Stop the current player's timer
        if (isRunning[currentPlayerIndex]) {
            long elapsedTime = SystemClock.elapsedRealtime() - startTimes[currentPlayerIndex];
            totalTimes[currentPlayerIndex] += elapsedTime;
            isRunning[currentPlayerIndex] = false;
        }

        // Move to the next player in the custom order
        currentTurnIndex = (currentTurnIndex + 1) % turnOrder.length;
        currentPlayerIndex = turnOrder[currentTurnIndex];

        // Start the new player's timer
        startTimes[currentPlayerIndex] = SystemClock.elapsedRealtime();
        isRunning[currentPlayerIndex] = true;

        // If we have completed a full cycle, increment the overall turn count
        if (currentTurnIndex == startingPositionIndex) {
            overallTurnCount++;
            updateOverallTurnCountDisplay(); // Update the display for overall turn count
        }

        highlightActivePlayer();
        updateDisplayTimes(); // Update the display with new times
    }

    private void updateOverallTurnCountDisplay() {
        // Update the TextView to display the overall turn count
        overallTurnCountTextView.setText("Turn: " + overallTurnCount);
    }

    private void updateDisplayTimes() {
        for (int i = 0; i < 4; i++) {
            long savedTime = totalTimes[i] + (isRunning[i] ? (SystemClock.elapsedRealtime() - startTimes[i]) : 0);
            int minutes = (int) (savedTime / 60000);
            int seconds = (int) (savedTime % 60000) / 1000;
            TextView playerTimeView = null;
            switch (i) {
                case 0: playerTimeView = player1Time; break;
                case 1: playerTimeView = player2Time; break;
                case 2: playerTimeView = player3Time; break;
                case 3: playerTimeView = player4Time; break;
            }
            if (playerTimeView != null) {
                playerTimeView.setText(String.format("Time %d: %d:%02d", i + 1, minutes, seconds));
            }
        }
    }
}
