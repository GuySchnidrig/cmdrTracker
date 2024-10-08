package com.example.cmdrtracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerInputActivity extends AppCompatActivity {

    Spinner player1Spinner, player2Spinner, player3Spinner, player4Spinner;
    Spinner player1SpinnerDeck, player2SpinnerDeck, player3SpinnerDeck, player4SpinnerDeck;
    Spinner startingPlayerSpinner;

    DatabaseHelper myDB;
    ArrayList<String> namesList, deckList, startList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_input);

        myDB = new DatabaseHelper(PlayerInputActivity.this);

        // Initialize spinners
        player1Spinner = findViewById(R.id.player1Spinner);
        player2Spinner = findViewById(R.id.player2Spinner);
        player3Spinner = findViewById(R.id.player3Spinner);
        player4Spinner = findViewById(R.id.player4Spinner);

        player1SpinnerDeck = findViewById(R.id.player1SpinnerDeck);
        player2SpinnerDeck = findViewById(R.id.player2SpinnerDeck);
        player3SpinnerDeck = findViewById(R.id.player3SpinnerDeck);
        player4SpinnerDeck = findViewById(R.id.player4SpinnerDeck);

        startingPlayerSpinner = findViewById(R.id.startingPlayerSpinner);

        // Initialize new UI elements
        Button buttonAddPlayer = findViewById(R.id.submitNewPlayer);
        Button buttonAddDeck = findViewById(R.id.submitNewDeck);

        // Read names from the text file
        namesList = new ArrayList<>();
        deckList = new ArrayList<>();
        startList = new ArrayList<>();

        storePlayerNamesInArrays();
        storeDeckNamesInArrays();

        startList.add("Top left starts");
        startList.add("Bottom left starts");
        startList.add("Bottom right starts");
        startList.add("Top right starts");

        // Placeholder item
        namesList.add(0, "Select a player");
        deckList.add(0, "Select a deck");

        // Create an ArrayAdapter using the namesList and a default spinner layout
        ArrayAdapter<String> namesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, namesList);
        namesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> deckAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, deckList);
        deckAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> startAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, startList);
        deckAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinners
        player1Spinner.setAdapter(namesAdapter);
        player2Spinner.setAdapter(namesAdapter);
        player3Spinner.setAdapter(namesAdapter);
        player4Spinner.setAdapter(namesAdapter);

        player1SpinnerDeck.setAdapter(deckAdapter);
        player2SpinnerDeck.setAdapter(deckAdapter);
        player3SpinnerDeck.setAdapter(deckAdapter);
        player4SpinnerDeck.setAdapter(deckAdapter);

        startingPlayerSpinner.setAdapter(startAdapter);

        // Handle add player button click
        buttonAddPlayer.setOnClickListener(v -> showAddPlayerDialog());

        // Handle add deck button click
        buttonAddDeck.setOnClickListener(v -> showAddDeckDialog());

        // Handle submit button click
        Button submitButton = findViewById(R.id.submitBtn);
        submitButton.setOnClickListener(v -> {
            // Get selected player names
            String player1Name = player1Spinner.getSelectedItem().toString();
            String player2Name = player2Spinner.getSelectedItem().toString();
            String player3Name = player3Spinner.getSelectedItem().toString();
            String player4Name = player4Spinner.getSelectedItem().toString();

            String player1Deck = player1SpinnerDeck.getSelectedItem().toString();
            String player2Deck = player2SpinnerDeck.getSelectedItem().toString();
            String player3Deck = player3SpinnerDeck.getSelectedItem().toString();
            String player4Deck = player4SpinnerDeck.getSelectedItem().toString();

            String startingPlayer = startingPlayerSpinner.getSelectedItem().toString();
            int startingPositionIndex = startList.indexOf(startingPlayer);

            // Create intent to send data back to MainActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("player1Name", player1Name);
            resultIntent.putExtra("player2Name", player2Name);
            resultIntent.putExtra("player3Name", player3Name);
            resultIntent.putExtra("player4Name", player4Name);

            resultIntent.putExtra("player1Deck", player1Deck);
            resultIntent.putExtra("player2Deck", player2Deck);
            resultIntent.putExtra("player3Deck", player3Deck);
            resultIntent.putExtra("player4Deck", player4Deck);

            resultIntent.putExtra("startingPositionIndex", startingPositionIndex);

            setResult(RESULT_OK, resultIntent);
            finish();  // End the activity and send the result back
        });
    }

    void storePlayerNamesInArrays() {
        Cursor cursor = myDB.readAlLPlayerNames();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                namesList.add(cursor.getString(0));
            }

        }
    }

    void storeDeckNamesInArrays() {
        Cursor cursor = myDB.readAlLDeckNames();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                deckList.add(cursor.getString(0));
            }
        }
    }

    private void showAddPlayerDialog() {
        // Create an EditText view for user input
        final EditText input = new EditText(this);
        input.setHint("Enter player name");

        new AlertDialog.Builder(this)
                .setTitle("Add New Player")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    String playerName = input.getText().toString().trim();
                    if (!playerName.isEmpty()) {
                        myDB.addPlayer(playerName);
                        namesList.add(playerName); // Update spinner list
                        updateSpinners();
                        Toast.makeText(this, "Player added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please enter a player name", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .show();
    }

    private void showAddDeckDialog() {
        // Create an EditText view for user input
        final EditText input = new EditText(this);
        input.setHint("Enter deck name");

        new AlertDialog.Builder(this)
                .setTitle("Add New Deck")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    String deckName = input.getText().toString().trim();
                    if (!deckName.isEmpty()) {
                        myDB.addDeck(deckName);
                        deckList.add(deckName); // Update spinner list
                        updateSpinners();
                        Toast.makeText(this, "Deck added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please enter a deck name", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .show();
    }

    private void updateSpinners() {
        // Sort the lists before notifying the adapters
        Collections.sort(namesList);
        Collections.sort(deckList);

        ((ArrayAdapter<String>) player1Spinner.getAdapter()).notifyDataSetChanged();
        ((ArrayAdapter<String>) player2Spinner.getAdapter()).notifyDataSetChanged();
        ((ArrayAdapter<String>) player3Spinner.getAdapter()).notifyDataSetChanged();
        ((ArrayAdapter<String>) player4Spinner.getAdapter()).notifyDataSetChanged();

        ((ArrayAdapter<String>) player1SpinnerDeck.getAdapter()).notifyDataSetChanged();
        ((ArrayAdapter<String>) player2SpinnerDeck.getAdapter()).notifyDataSetChanged();
        ((ArrayAdapter<String>) player3SpinnerDeck.getAdapter()).notifyDataSetChanged();
        ((ArrayAdapter<String>) player4SpinnerDeck.getAdapter()).notifyDataSetChanged();
    }

}
