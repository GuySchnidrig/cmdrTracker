package com.example.cmdrtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cmdr_tracker.db"; // Use your existing database name
    private static final int DATABASE_VERSION = 1;
    private static String DATABASE_PATH = "";
    private final Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context= context;

        // Set the database path for internal storage
        assert context != null;
        DATABASE_PATH = context.getDatabasePath(DATABASE_NAME).getPath();
        Log.d("DatabaseHelper", "Database Path: " + DATABASE_PATH);

        // Copy the database if it doesn't exist
        try {
            createDatabase();
        } catch (IOException e) {
            Log.e("DatabaseHelper", "Error copying database", e);
        }
    }

    // Check if the database already exists to avoid re-copying it
    private boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
            Log.d("DatabaseHelper", "Database does not exist yet.");
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null;
    }

    // Copy the database from the assets folder to the internal storage
    private void copyDatabase() throws IOException {
        InputStream input = context.getAssets().open(DATABASE_NAME);
        OutputStream output = new FileOutputStream(DATABASE_PATH);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        output.flush();
        output.close();
        input.close();

        Log.d("DatabaseHelper", "Database copied successfully");
    }

    // Method to create the database
    public void createDatabase() throws IOException {
        boolean dbExists = checkDatabase();
        if (!dbExists) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // No need to create tables, as you already have the database
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade if necessary
    }

    // Method to get all player names from the existing table
    Cursor readAlLPlayerNames() {
        String query = "SELECT player_name FROM player_names ORDER BY player_name ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    // Method to get all deck names from the existing table
    Cursor readAlLDeckNames() {
        String query = "SELECT deck_name FROM deck_names ORDER BY deck_name ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void addPlayer(String playerName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("player_name", playerName);
        db.insert("player_names", null, values);
    }

    public void addDeck(String deckName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("deck_name", deckName);
        db.insert("deck_names", null, values);
    }

    public int getMaxGameId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(game_id) FROM games_data", null);

        int maxGameId = 0;
        if (cursor.moveToFirst()) {
            maxGameId = cursor.getInt(0);
        }

        cursor.close();
        return maxGameId;
    }

    public void addGameData(
            int gameID,
            String gameType,
            String date,
            String player_name,
            String deck_name,
            String start,
            int win,
            int win_turn,
            String win_type,
            String mv_card,
            int life,
            float time_total,
            String deck_link,
            String uploader) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("game_id", gameID);
        values.put("game_type", gameType);
        values.put("date", date);
        values.put("player_name", player_name);
        values.put("deck_name", deck_name);
        values.put("start", start);
        values.put("win", win);
        values.put("win_turn", win_turn);
        values.put("win_type", win_type);
        values.put("mv_card", mv_card);
        values.put("life", life);
        values.put("time_total", time_total);
        values.put("deck_link", deck_link);
        values.put("uploader", uploader);

        db.insert("game_data", null, values);
    }
}