package com.example.cmdrtracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EndGameScreen extends AppCompatActivity {

    DatabaseHelper myDB;
    public int player1win, player2win, player3win, player4win;
    public int player1start, player2start, player3start, player4start;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame); // Replace with your layout resource

        myDB = new DatabaseHelper(EndGameScreen.this);

        // Retrieve the player name from the Intent
        Intent intent = getIntent();
        int overallTurnCount = intent.getIntExtra("overallTurnCountEND", 0);
        String StartingPlayerName = intent.getStringExtra("StartingPlayerNameEND");
        String winningPlayer = intent.getStringExtra("winningPlayerEND");
        String MVCard = intent.getStringExtra("MVCardEND");

        String player1Name = intent.getStringExtra("player1NameEND");
        String player1Deck = intent.getStringExtra("player1DeckEND");
        int player1Life = intent.getIntExtra("player1LifeEND", 0);
        float player1Time = intent.getFloatExtra("player1TimeEND", 0.00f);


        String player2Name = intent.getStringExtra("player2NameEND");
        String player2Deck = intent.getStringExtra("player2DeckEND");
        int player2Life = intent.getIntExtra("player2LifeEND", 0);
        float player2Time = intent.getFloatExtra("player2TimeEND", 0.00f);


        String player3Name = intent.getStringExtra("player3NameEND");
        String player3Deck = intent.getStringExtra("player3DeckEND");
        int player3Life = intent.getIntExtra("player3LifeEND", 0);
        float player3Time = intent.getFloatExtra("player3TimeEND", 0.00f);

        String player4Name = intent.getStringExtra("player4NameEND");
        String player4Deck = intent.getStringExtra("player4DeckEND");
        int player4Life = intent.getIntExtra("player4LifeEND", 0);
        float player4Time = intent.getFloatExtra("player4TimeEND", 0.00f);

        // Check if each player is the winning player and update their score
        player1win = (player1Name != null && player1Name.equals(winningPlayer)) ? 1 : 0;
        player2win = (player2Name != null && player2Name.equals(winningPlayer)) ? 1 : 0;
        player3win = (player3Name != null && player3Name.equals(winningPlayer)) ? 1 : 0;
        player4win = (player4Name != null && player4Name.equals(winningPlayer)) ? 1 : 0;

        // Check if each player is the starting player and update their score
        player1start = (player1Name != null && player1Name.equals(StartingPlayerName)) ? 1 : 0;
        player2start = (player2Name != null && player2Name.equals(StartingPlayerName)) ? 1 : 0;
        player3start = (player3Name != null && player3Name.equals(StartingPlayerName)) ? 1 : 0;
        player4start = (player4Name != null && player4Name.equals(StartingPlayerName)) ? 1 : 0;

        // Use the player name as needed
        // For example, display it in a TextView
        ((TextView) findViewById(R.id.TurnCount)).setText(String.valueOf(overallTurnCount));
        ((TextView) findViewById(R.id.StartingPlayer)).setText(StartingPlayerName);
        ((TextView) findViewById(R.id.winningPlayer)).setText(winningPlayer);
        ((TextView) findViewById(R.id.MostValuableCard)).setText(MVCard);


        ((TextView) findViewById(R.id.NamePlayer1)).setText(player1Name);
        ((TextView) findViewById(R.id.DeckPlayer1)).setText(player1Deck);
        ((TextView) findViewById(R.id.LifePlayer1)).setText(String.valueOf(player1Life));
        ((TextView) findViewById(R.id.TimePlayer1)).setText(String.format("%.2f", player1Time));
        ((TextView) findViewById(R.id.Win1)).setText(String.valueOf(player1win));
        ((TextView) findViewById(R.id.Start1)).setText(String.valueOf(player1start));

        ((TextView) findViewById(R.id.NamePlayer2)).setText(player2Name);
        ((TextView) findViewById(R.id.DeckPlayer2)).setText(player2Deck);
        ((TextView) findViewById(R.id.LifePlayer2)).setText(String.valueOf(player2Life));
        ((TextView) findViewById(R.id.TimePlayer2)).setText(String.format("%.2f", player2Time));
        ((TextView) findViewById(R.id.Win2)).setText(String.valueOf(player2win));
        ((TextView) findViewById(R.id.Start2)).setText(String.valueOf(player2start));

        ((TextView) findViewById(R.id.NamePlayer3)).setText(player3Name);
        ((TextView) findViewById(R.id.DeckPlayer3)).setText(player3Deck);
        ((TextView) findViewById(R.id.LifePlayer3)).setText(String.valueOf(player3Life));
        ((TextView) findViewById(R.id.TimePlayer3)).setText(String.format("%.2f", player3Time));
        ((TextView) findViewById(R.id.Win3)).setText(String.valueOf(player3win));
        ((TextView) findViewById(R.id.Start3)).setText(String.valueOf(player3start));

        ((TextView) findViewById(R.id.NamePlayer4)).setText(player4Name);
        ((TextView) findViewById(R.id.DeckPlayer4)).setText(player4Deck);
        ((TextView) findViewById(R.id.LifePlayer4)).setText(String.valueOf(player4Life));
        ((TextView) findViewById(R.id.TimePlayer4)).setText(String.format("%.2f", player4Time));
        ((TextView) findViewById(R.id.Win4)).setText(String.valueOf(player4win));
        ((TextView) findViewById(R.id.Start4)).setText(String.valueOf(player4start));

    }
}