package com.example.cmdrtracker;

import static java.lang.Float.parseFloat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EndGameScreen extends AppCompatActivity {

    DatabaseHelper myDB;

    public int player1win, player2win, player3win, player4win;
    public int player1start, player2start, player3start, player4start;

    public int player1Life, player2Life, player3Life, player4Life;

    public float player1Time, player2Time, player3Time, player4Time;
    public int overallTurnCount, overallTurnCountT;
    public int game_id;

    public String player1Name, player2Name, player3Name, player4Name;
    public String player1Deck, player2Deck, player3Deck, player4Deck;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"DefaultLocale"})
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


        // Initialize the "Submit Game" button
        Button submitGameToDBButton = findViewById(R.id.submitGameDB);

        submitGameToDBButton.setOnClickListener(v -> {
            new AlertDialog.Builder(EndGameScreen.this, R.style.CustomAlertDialogTheme)
                    .setTitle("Submit Game!")
                    .setMessage("Are you sure you want to submit the game data?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // User confirmed, proceed with ending the game
                        addGameDataEntries();
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        // User cancelled the dialog, just dismiss it
                        dialog.dismiss();
                    })
                    .create()
                    .show();
        });

        // Initialize the "Submit Game" button
        Button shareDBButton = findViewById(R.id.SendDB);


        // Use the player name as needed
        // For example, display it in a TextView

        ((TextView) findViewById(R.id.TurnCount)).setText(String.valueOf(overallTurnCount));
        ((TextView) findViewById(R.id.StartingPlayer)).setText(StartingPlayerName);
        ((TextView) findViewById(R.id.winningPlayer)).setText(winningPlayer);
        ((TextView) findViewById(R.id.MostValuableCard)).setText(MVCard);
        ((TextView) findViewById(R.id.GameIdentity)).setText(String.valueOf(game_id));

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



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addGameDataEntries() {
        // Extract data from TextViews
        String gameType = "Normal"; // Example: get this from a TextView or other source
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = today.format(dateFormatter);
        String winType = "DefaultWinType"; // Example value
        String deckLink = ""; // Example URL or other data source
        String uploader = "Guy";
        String mvCard = ((TextView) findViewById(R.id.MostValuableCard)).getText().toString();

        // Extract the overallTurnCount from TextView to ensure consistency
        String overallTurnCountString = ((TextView) findViewById(R.id.TurnCount)).getText().toString();
        int overallTurnCount = Integer.parseInt(overallTurnCountString);

        // Player 1
        String player1Name = ((TextView) findViewById(R.id.NamePlayer1)).getText().toString();
        String player1Deck = ((TextView) findViewById(R.id.DeckPlayer1)).getText().toString();

        String LifePlayer1String = ((TextView) findViewById(R.id.LifePlayer1)).getText().toString();
        int player1Life = Integer.parseInt(LifePlayer1String);

        String player1TimeString = ((TextView) findViewById(R.id.TimePlayer1)).getText().toString();
        float player1Time = parseFloat(player1TimeString);

        // Player 2
        String player2Name = ((TextView) findViewById(R.id.NamePlayer2)).getText().toString();
        String player2Deck = ((TextView) findViewById(R.id.DeckPlayer2)).getText().toString();

        String LifePlayer2String = ((TextView) findViewById(R.id.LifePlayer2)).getText().toString();
        int player2Life = Integer.parseInt(LifePlayer2String);

        String player2TimeString = ((TextView) findViewById(R.id.TimePlayer2)).getText().toString();
        float player2Time = parseFloat(player2TimeString);


        // Player 3
        String player3Name = ((TextView) findViewById(R.id.NamePlayer3)).getText().toString();
        String player3Deck = ((TextView) findViewById(R.id.DeckPlayer3)).getText().toString();

        String LifePlayer3String = ((TextView) findViewById(R.id.LifePlayer3)).getText().toString();
        int player3Life = Integer.parseInt(LifePlayer3String);

        String player3TimeString = ((TextView) findViewById(R.id.TimePlayer3)).getText().toString();
        float player3Time = parseFloat(player3TimeString);

        // Player 4
        String player4Name = ((TextView) findViewById(R.id.NamePlayer4)).getText().toString();
        String player4Deck = ((TextView) findViewById(R.id.DeckPlayer4)).getText().toString();

        String LifePlayer4String = ((TextView) findViewById(R.id.LifePlayer4)).getText().toString();
        int player4Life = Integer.parseInt(LifePlayer4String);

        String player4TimeString = ((TextView) findViewById(R.id.TimePlayer4)).getText().toString();
        float player4Time = parseFloat(player4TimeString);


        // Add Game Data
        myDB.addGameData(game_id,gameType, date,player1Name,player1Deck,player1start,player1win,overallTurnCount,winType,mvCard,player1Life,player1Time,deckLink,uploader);
        myDB.addGameData(game_id,gameType, date,player2Name,player2Deck,player2start,player2win,overallTurnCount,winType,mvCard,player2Life,player2Time,deckLink,uploader);
        myDB.addGameData(game_id,gameType, date,player3Name,player3Deck,player3start,player3win,overallTurnCount,winType,mvCard,player3Life,player3Time,deckLink,uploader);
        myDB.addGameData(game_id,gameType, date,player4Name,player4Deck,player4start,player4win,overallTurnCount,winType,mvCard,player4Life,player4Time,deckLink,uploader);

        // Optionally, you could show a message or take other actions after adding the game data
        Toast.makeText(this, "Game data submitted!", Toast.LENGTH_SHORT).show();

        // Start PlayerInputActivity
        Intent intent = new Intent(this, PlayerInputActivity.class);
        startActivity(intent);
    }


}