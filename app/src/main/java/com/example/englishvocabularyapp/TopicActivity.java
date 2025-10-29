package com.example.englishvocabularyapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.englishvocabularyapp.adapters.WordAdapter;
import com.example.englishvocabularyapp.models.Word;
import java.util.ArrayList;

public class TopicActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Word> wordList;
    WordAdapter adapter;
    DatabaseHelper dbHelper;
    int topicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        recyclerView = findViewById(R.id.recyclerViewWords);
        dbHelper = new DatabaseHelper(this);
        wordList = new ArrayList<>();

        topicId = getIntent().getIntExtra("topicId", -1);
        loadWords(topicId);

        adapter = new WordAdapter(this, wordList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btnFlashcard).setOnClickListener(v -> {
            Intent i = new Intent(this, FlashcardActivity.class);
            i.putExtra("topicId", topicId);
            startActivity(i);
        });

        findViewById(R.id.btnQuiz).setOnClickListener(v -> {
            Intent i = new Intent(this, QuizActivity.class);
            i.putExtra("topicId", topicId);
            startActivity(i);
        });
    }

    private void loadWords(int topicId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM words WHERE topic_id=?", new String[]{String.valueOf(topicId)});
        while (cursor.moveToNext()) {
            wordList.add(new Word(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3)));
        }
        cursor.close();
    }
}
