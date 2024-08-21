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
        int overallTurnCount = intent.getIntExtra("overallTurnCountEND", 0);
        String StartingPlayerName = intent.getStringExtra("StartingPlayerNameEND");

        String player1Name = intent.getStringExtra("player1NameEND");
        String player1Deck = intent.getStringExtra("player1DeckEND");
        String player1Life = intent.getStringExtra("player1LifeEND");
        String player1Time = intent.getStringExtra("player1TimeEND");


        String player2Name = intent.getStringExtra("player2NameEND");
        String player2Deck = intent.getStringExtra("player2DeckEND");
        String player2Life = intent.getStringExtra("player2LifeEND");
        String player2Time = intent.getStringExtra("player2TimeEND");


        String player3Name = intent.getStringExtra("player3NameEND");
        String player3Deck = intent.getStringExtra("player3DeckEND");
        String player3Life = intent.getStringExtra("player3LifeEND");
        String player3Time = intent.getStringExtra("player3TimeEND");

        String player4Name = intent.getStringExtra("player4NameEND");
        String player4Deck = intent.getStringExtra("player4DeckEND");
        String player4Life = intent.getStringExtra("player4LifeEND");
        String player4Time = intent.getStringExtra("player4TimeEND");

        // Use the player name as needed
        // For example, display it in a TextView
        ((TextView) findViewById(R.id.TurnCount)).setText(String.valueOf(overallTurnCount));
        ((TextView) findViewById(R.id.StartingPlayer)).setText(StartingPlayerName);

        ((TextView) findViewById(R.id.NamePlayer1)).setText(player1Name);
        ((TextView) findViewById(R.id.DeckPlayer1)).setText(player1Deck);
        ((TextView) findViewById(R.id.LifePlayer1)).setText(player1Life);
        ((TextView) findViewById(R.id.TimePlayer1)).setText(player1Time);

        ((TextView) findViewById(R.id.NamePlayer2)).setText(player2Name);
        ((TextView) findViewById(R.id.DeckPlayer2)).setText(player2Deck);
        ((TextView) findViewById(R.id.LifePlayer2)).setText(player2Life);
        ((TextView) findViewById(R.id.TimePlayer2)).setText(player2Time);

        ((TextView) findViewById(R.id.NamePlayer3)).setText(player3Name);
        ((TextView) findViewById(R.id.DeckPlayer3)).setText(player3Deck);
        ((TextView) findViewById(R.id.LifePlayer3)).setText(player3Life);
        ((TextView) findViewById(R.id.TimePlayer3)).setText(player3Time);

        ((TextView) findViewById(R.id.NamePlayer4)).setText(player4Name);
        ((TextView) findViewById(R.id.DeckPlayer4)).setText(player4Deck);
        ((TextView) findViewById(R.id.LifePlayer4)).setText(player4Life);
        ((TextView) findViewById(R.id.TimePlayer4)).setText(player4Time);


    }
}