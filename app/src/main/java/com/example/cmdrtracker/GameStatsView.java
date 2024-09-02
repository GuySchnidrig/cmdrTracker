package com.example.cmdrtracker;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameStatsView extends AppCompatActivity {

    private DatabaseHelper myDB;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamestats); // Make sure to set the content view
        LinearLayout mainLayout = findViewById(R.id.linearLayout2);

        myDB = new DatabaseHelper(this);
        // Example query to get data from your database
        cursor = myDB.getReadableDatabase().rawQuery("SELECT * FROM game_data ORDER BY id DESC", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Create a new horizontal LinearLayout for each row
                LinearLayout rowLayout = new LinearLayout(this);
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                // Iterate over all columns in the current row
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String value = cursor.getString(i);
                    TextView textView = new TextView(this);
                    textView.setText(value);
                    //textView.setText(cursor.getColumnName(i) + ": " + value);
                    textView.setTextSize(10f);
                    textView.setTextColor(getResources().getColor(android.R.color.white));
                    textView.setLayoutParams(new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1f  // Distribute space equally among columns
                    ));
                    rowLayout.addView(textView); // Add each column to the row
                }

                // Add the row to the main layout
                mainLayout.addView(rowLayout);

                // Add a separator between rows (optional)
                View separator = new View(this);
                separator.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1
                ));
                separator.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                mainLayout.addView(separator);

            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close(); // Close the cursor after use
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myDB != null) {
            myDB.close(); // Close the database connection
        }
    }
}
