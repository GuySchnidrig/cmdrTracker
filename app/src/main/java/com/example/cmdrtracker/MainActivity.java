package com.example.cmdrtracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    // Life total TextViews and player name TextViews
    TextView player1Life, player2Life, player3Life, player4Life;
    TextView player1Name, player2Name, player3Name, player4Name;
    TextView player1Deck, player2Deck, player3Deck, player4Deck;
    TextView player1Time, player2Time, player3Time, player4Time;
    TextView overallTurnCountTextView; // Add TextView for overall turn count
    TextView StartingPlayerName, errorTextView ;

    private String winningPlayer;
    private String MVCard;

    private static final int PLAYER_INPUT_REQUEST = 1;
    private final int[] turnOrder = {0, 2, 3, 1}; // Define the custom order
    private int currentTurnIndex = 0; // Index to track current turn in turnOrder array
    private int currentPlayerIndex;
    private int startingPositionIndex;
    private int overallTurnCount = 1; // Variable to track overall turn count
    private long[] startTimes = new long[4]; // To track the start time of each player's turn
    private boolean[] isRunning = new boolean[4]; // To check if the timer is running for each player
    private long[] totalTimes = new long[4]; // To store total times for each player

    @SuppressLint("ClickableViewAccessibility")
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

        // Initialize Buttons for Player 1
        Button player1Plus = findViewById(R.id.player1Plus);
        Button player1Minus = findViewById(R.id.player1Minus);

        // Initialize Buttons for Player 2
        Button player2Plus = findViewById(R.id.player2Plus);
        Button player2Minus = findViewById(R.id.player2Minus);

        // Initialize Buttons for Player 3
        Button player3Plus = findViewById(R.id.player3Plus);
        Button player3Minus = findViewById(R.id.player3Minus);

        // Initialize Buttons for Player 4
        Button player4Plus = findViewById(R.id.player4Plus);
        Button player4Minus = findViewById(R.id.player4Minus);

        // Create Handlers to run code at intervals while the buttons are held down
        Handler handler1 = new Handler();
        Handler handler2 = new Handler();
        Handler handler3 = new Handler();
        Handler handler4 = new Handler();

        // Runnable for incrementing and decrementing life for each player
        Runnable incrementRunnable1 = new Runnable() {
            @Override
            public void run() {
                updateLife(player1Life, 1);
                handler1.postDelayed(this, 200); // Run every 200ms
            }
        };
        Runnable decrementRunnable1 = new Runnable() {
            @Override
            public void run() {
                updateLife(player1Life, -1);
                handler1.postDelayed(this, 200); // Run every 200ms
            }
        };

        Runnable incrementRunnable2 = new Runnable() {
            @Override
            public void run() {
                updateLife(player2Life, 1);
                handler2.postDelayed(this, 200); // Run every 200ms
            }
        };
        Runnable decrementRunnable2 = new Runnable() {
            @Override
            public void run() {
                updateLife(player2Life, -1);
                handler2.postDelayed(this, 200); // Run every 200ms
            }
        };

        Runnable incrementRunnable3 = new Runnable() {
            @Override
            public void run() {
                updateLife(player3Life, 1);
                handler3.postDelayed(this, 200); // Run every 200ms
            }
        };
        Runnable decrementRunnable3 = new Runnable() {
            @Override
            public void run() {
                updateLife(player3Life, -1);
                handler3.postDelayed(this, 200); // Run every 200ms
            }
        };

        Runnable incrementRunnable4 = new Runnable() {
            @Override
            public void run() {
                updateLife(player4Life, 1);
                handler4.postDelayed(this, 200); // Run every 200ms
            }
        };
        Runnable decrementRunnable4 = new Runnable() {
            @Override
            public void run() {
                updateLife(player4Life, -1);
                handler4.postDelayed(this, 200); // Run every 200ms
            }
        };

        // Set onTouchListener for Player 1 buttons
        player1Plus.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler1.post(incrementRunnable1);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    handler1.removeCallbacks(incrementRunnable1);
                    return true;
            }
            return false;
        });

        player1Minus.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler1.post(decrementRunnable1);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    handler1.removeCallbacks(decrementRunnable1);
                    return true;
            }
            return false;
        });

        // Set onTouchListener for Player 2 buttons
        player2Plus.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler2.post(incrementRunnable2);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    handler2.removeCallbacks(incrementRunnable2);
                    return true;
            }
            return false;
        });

        player2Minus.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler2.post(decrementRunnable2);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    handler2.removeCallbacks(decrementRunnable2);
                    return true;
            }
            return false;
        });

        // Set onTouchListener for Player 3 buttons
        player3Plus.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler3.post(incrementRunnable3);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    handler3.removeCallbacks(incrementRunnable3);
                    return true;
            }
            return false;
        });

        player3Minus.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler3.post(decrementRunnable3);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    handler3.removeCallbacks(decrementRunnable3);
                    return true;
            }
            return false;
        });

        // Set onTouchListener for Player 4 buttons
        player4Plus.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler4.post(incrementRunnable4);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    handler4.removeCallbacks(incrementRunnable4);
                    return true;
            }
            return false;
        });

        player4Minus.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler4.post(decrementRunnable4);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    handler4.removeCallbacks(decrementRunnable4);
                    return true;
            }
            return false;
        });


        // Initialize the "Pass Turn" button
        Button passTurnButton = findViewById(R.id.passTurnButton);
        passTurnButton.setOnClickListener(v -> passTurn());

        Button endGameButton = findViewById(R.id.endGameButton);
        endGameButton.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialogTheme)
                    .setTitle("Confirm End Game")
                    .setMessage("Are you sure you want to end the game?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // User confirmed, proceed with ending the game
                        showWinningPlayerDialog();
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        // User cancelled the dialog, just dismiss it
                        dialog.dismiss();
                    })
                    .create()
                    .show();
        });


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

            // Retrieve startingPositionIndex from the Intent data
            startingPositionIndex = data.getIntExtra("startingPositionIndex", 0);

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

            // Determine player names based on startingPositionIndex
            if (startingPositionIndex == 0) {
                StartingPlayerName = player1Name;
            } else if (startingPositionIndex == 1) {
                StartingPlayerName = player3Name;
            } else if (startingPositionIndex == 2) {
                StartingPlayerName = player4Name;
            } else if (startingPositionIndex == 3) {
                StartingPlayerName = player2Name;
            } else {
                errorTextView.setText("Invalid starting position index!");
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
                playerTimeView.setText(String.format("Time: %d:%02d", minutes, seconds));
            }
        }
    }

    private float getTimeInMinutesFromTextView(TextView playerTimeView) {
        String timeText = playerTimeView.getText().toString();

        // Remove the "Time: " prefix if it's there
        String timeOnly = timeText.replace("Time: ", "");

        // Split the string by the colon to get minutes and seconds
        String[] parts = timeOnly.split(":");

        if (parts.length == 2) {
            int minutes = Integer.parseInt(parts[0]);
            int seconds = Integer.parseInt(parts[1]);

            // Convert the time to total minutes as a float
            return minutes + (seconds / 60.00f);
        }

        // If parsing fails or the format is unexpected, return 0.0f or handle the error appropriately
        return 0.00f;
    }

    private void showWinningPlayerDialog() {
        // Retrieve player names from TextViews
        String[] players = {
                player1Name.getText().toString(),
                player2Name.getText().toString(),
                player3Name.getText().toString(),
                player4Name.getText().toString()
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialogTheme);
        builder.setTitle("Select Winning Player");

        builder.setItems(players, (dialog, which) -> {
            // Handle player selection
            winningPlayer = players[which]; // Directly assign the selected player to the winningPlayer variable
            showMVCardDialog(); // Call endGame() after setting the winning player
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss(); // Close the dialog if the user cancels
        });

        builder.create().show(); // Create and show the dialog
    }

    private void showMVCardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialogTheme);
        builder.setTitle("Select Most Valuable Card");

        // Create an EditText to enter the most valuable card
        EditText input = new EditText(MainActivity.this);
        input.setHint("Enter Most Valuable Card");

        // Set the EditText as the view for the dialog
        builder.setView(input);

        // Set up the positive button to handle the input
        builder.setPositiveButton("OK", (dialog, which) -> {
            MVCard = input.getText().toString();
            if (!MVCard.isEmpty()) {
                // Handle the entered most valuable card
                // For example, you can store the card in a variable or perform some logic with it
                // Call endGame() after handling the input
                endGame();
            } else {
                // Handle the case where the user didn't enter anything
                Toast.makeText(MainActivity.this, "Please enter a card name", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss(); // Close the dialog if the user cancels
        });

        builder.create().show(); // Create and show the dialog
    }

    private void endGame() {
        // Get the text from the TextView
        int overallTurnCountEND = overallTurnCount;
        String StartingPlayerNameEND = StartingPlayerName.getText().toString();
        String winningPlayerEND = winningPlayer;
        String MVCardEND = MVCard;

        String player1NameEND = player1Name.getText().toString();
        String player1DeckEND = player1Deck.getText().toString();
        float  player1TimeEND = getTimeInMinutesFromTextView(player1Time);
        int player1LifeEND = Integer.parseInt(player1Life.getText().toString());

        String player2NameEND = player2Name.getText().toString();
        String player2DeckEND = player2Deck.getText().toString();
        float  player2TimeEND = getTimeInMinutesFromTextView(player2Time);
        int player2LifeEND = Integer.parseInt(player2Life.getText().toString());

        String player3NameEND = player3Name.getText().toString();
        String player3DeckEND = player3Deck.getText().toString();
        float  player3TimeEND = getTimeInMinutesFromTextView(player3Time);
        int player3LifeEND = Integer.parseInt(player3Life.getText().toString());

        String player4NameEND = player4Name.getText().toString();
        String player4DeckEND = player4Deck.getText().toString();
        float  player4TimeEND = getTimeInMinutesFromTextView(player4Time);
        int player4LifeEND = Integer.parseInt(player4Life.getText().toString());

        // Create an Intent to start the new activity
        Intent intent = new Intent(MainActivity.this, EndGameScreen.class);

        // Add data to the Intent
        intent.putExtra("overallTurnCountEND", overallTurnCountEND);
        intent.putExtra("StartingPlayerNameEND", StartingPlayerNameEND);
        intent.putExtra("winningPlayerEND", winningPlayerEND);
        intent.putExtra("MVCardEND", MVCardEND);

        intent.putExtra("player1NameEND", player1NameEND);
        intent.putExtra("player1DeckEND", player1DeckEND);
        intent.putExtra("player1TimeEND", player1TimeEND);
        intent.putExtra("player1LifeEND", player1LifeEND);

        intent.putExtra("player2NameEND", player2NameEND);
        intent.putExtra("player2DeckEND", player2DeckEND);
        intent.putExtra("player2TimeEND", player2TimeEND);
        intent.putExtra("player2LifeEND", player2LifeEND);

        intent.putExtra("player3NameEND", player3NameEND);
        intent.putExtra("player3DeckEND", player3DeckEND);
        intent.putExtra("player3TimeEND", player3TimeEND);
        intent.putExtra("player3LifeEND", player3LifeEND);

        intent.putExtra("player4NameEND", player4NameEND);
        intent.putExtra("player4DeckEND", player4DeckEND);
        intent.putExtra("player4TimeEND", player4TimeEND);
        intent.putExtra("player4LifeEND", player4LifeEND);

        // Start the new activity
        startActivity(intent);
    }
}
