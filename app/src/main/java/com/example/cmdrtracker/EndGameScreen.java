package com.example.cmdrtracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EndGameScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame); // Replace with your layout resource

        // Retrieve the player name from the Intent
        Intent intent = getIntent();

        String player1Name = intent.getStringExtra("player1NameEND");
        String player1Deck = intent.getStringExtra("player1DeckEND");


        // Use the player name as needed
        // For example, display it in a TextView
        TextView player1NameENDTextView = findViewById(R.id.player1NameEND);
        player1NameENDTextView.setText(player1Name);

        TextView player1DeckView = findViewById(R.id.player1DeckEND);
        player1DeckView.setText(player1Deck);
    }
}