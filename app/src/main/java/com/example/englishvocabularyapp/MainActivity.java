package com.example.englishvocabularyapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishvocabularyapp.adapters.TopicAdapter;
import com.example.englishvocabularyapp.models.Topic;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Topic> topicList;
    private TopicAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ view
        recyclerView = findViewById(R.id.recyclerView);

        // Khởi tạo database và danh sách
        dbHelper = new DatabaseHelper(this);
        topicList = new ArrayList<>();

        // Tải dữ liệu từ SQLite
        loadTopics();

        // Cấu hình RecyclerView
        adapter = new TopicAdapter(MainActivity.this, topicList);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
    }

    private void loadTopics() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM topics", null);

        topicList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            topicList.add(new Topic(id, name));
        }

        cursor.close();
        db.close();
    }
}
