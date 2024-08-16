package com.example.cmdrtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PlayerInputActivity extends AppCompatActivity {

    Spinner player1Spinner, player2Spinner, player3Spinner, player4Spinner;
    Spinner player1SpinnerDeck, player2SpinnerDeck, player3SpinnerDeck, player4SpinnerDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_input);

        // Initialize spinners
        player1Spinner = findViewById(R.id.player1Spinner);
        player2Spinner = findViewById(R.id.player2Spinner);
        player3Spinner = findViewById(R.id.player3Spinner);
        player4Spinner = findViewById(R.id.player4Spinner);

        player1SpinnerDeck = findViewById(R.id.player1SpinnerDeck);
        player2SpinnerDeck = findViewById(R.id.player2SpinnerDeck);
        player3SpinnerDeck = findViewById(R.id.player3SpinnerDeck);
        player4SpinnerDeck = findViewById(R.id.player4SpinnerDeck);

        // Read names from the text file
        ArrayList<String> namesList = readNamesFromFile();

        ArrayList<String> deckList = readNamesFromFile2();

        // Create an ArrayAdapter using the namesList and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, namesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterdeckList = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, deckList);
        adapterdeckList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinners
        player1Spinner.setAdapter(adapter);
        player2Spinner.setAdapter(adapter);
        player3Spinner.setAdapter(adapter);
        player4Spinner.setAdapter(adapter);

        player1SpinnerDeck.setAdapter(adapterdeckList);
        player2SpinnerDeck.setAdapter(adapterdeckList);
        player3SpinnerDeck.setAdapter(adapterdeckList);
        player4SpinnerDeck.setAdapter(adapterdeckList);

        // Handle submit button click
        Button submitButton = findViewById(R.id.submitBtn);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected player names
                String player1Name = player1Spinner.getSelectedItem().toString();
                String player2Name = player2Spinner.getSelectedItem().toString();
                String player3Name = player3Spinner.getSelectedItem().toString();
                String player4Name = player4Spinner.getSelectedItem().toString();

                String player1Deck = player1SpinnerDeck.getSelectedItem().toString();
                String player2Deck = player2SpinnerDeck.getSelectedItem().toString();
                String player3Deck = player3SpinnerDeck.getSelectedItem().toString();
                String player4Deck = player4SpinnerDeck.getSelectedItem().toString();

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

                setResult(RESULT_OK, resultIntent);
                finish();  // End the activity and send the result back
            }
        });
    }

    private ArrayList<String> readNamesFromFile() {
        ArrayList<String> namesList = new ArrayList<>();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.player_names); // names is the name of the file without extension
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                namesList.add(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return namesList;
    }

    private ArrayList<String> readNamesFromFile2() {
        ArrayList<String> namesList = new ArrayList<>();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.deck_names); // names is the name of the file without extension
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                namesList.add(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return namesList;
    }
}
