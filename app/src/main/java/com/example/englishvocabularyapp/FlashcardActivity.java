package com.example.englishvocabularyapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;

public class FlashcardActivity extends AppCompatActivity {
    TextView txtWord, txtMeaning;
    ArrayList<String[]> flashcards;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        txtWord = findViewById(R.id.txtWord);
        txtMeaning = findViewById(R.id.txtMeaning);

        int topicId = getIntent().getIntExtra("topicId", -1);
        loadFlashcards(topicId);

        showCard();

        findViewById(R.id.btnNext).setOnClickListener(v -> nextCard());
        findViewById(R.id.btnShow).setOnClickListener(v -> toggleMeaning());
    }

    private void loadFlashcards(int topicId) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        flashcards = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT english, vietnamese FROM words WHERE topic_id=?", new String[]{String.valueOf(topicId)});
        while (c.moveToNext()) {
            flashcards.add(new String[]{c.getString(0), c.getString(1)});
        }
        c.close();
        Collections.shuffle(flashcards);
    }

    private void showCard() {
        if (flashcards.size() > 0) {
            txtWord.setText(flashcards.get(index)[0]);
            txtMeaning.setText("");
        }
    }

    private void nextCard() {
        if (flashcards.size() > 0) {
            index = (index + 1) % flashcards.size();
            txtWord.setText(flashcards.get(index)[0]);
            txtMeaning.setText("");
        }
    }

    private void toggleMeaning() {
        if (txtMeaning.getText().toString().isEmpty()) {
            txtMeaning.setText(flashcards.get(index)[1]);
        } else {
            txtMeaning.setText("");
        }
    }
}
