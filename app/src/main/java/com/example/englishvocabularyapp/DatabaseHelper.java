package com.example.englishvocabularyapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "vocabulary.db";
    public static final int DB_VERSION = 2; // tăng version để onUpgrade chạy lại

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng chủ đề
        db.execSQL("CREATE TABLE topics (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");

        // Tạo bảng từ vựng
        db.execSQL("CREATE TABLE words (id INTEGER PRIMARY KEY AUTOINCREMENT, topic_id INTEGER, english TEXT, vietnamese TEXT)");

        // Tạo bảng câu hỏi quiz
        db.execSQL("CREATE TABLE quiz (id INTEGER PRIMARY KEY AUTOINCREMENT, topic_id INTEGER, question TEXT, option1 TEXT, option2 TEXT, option3 TEXT, answer TEXT)");

        // Dữ liệu chủ đề
        db.execSQL("INSERT INTO topics (name) VALUES ('Animals'), ('Fruits'), ('Jobs')");

        // Dữ liệu từ vựng
        db.execSQL("INSERT INTO words (topic_id, english, vietnamese) VALUES " +
                "(1,'Dog','Con chó'), (1,'Cat','Con mèo'), " +
                "(2,'Apple','Quả táo'), (2,'Banana','Quả chuối'), " +
                "(3,'Doctor','Bác sĩ'), (3,'Teacher','Giáo viên')");

        // Dữ liệu câu hỏi quiz
        db.execSQL("INSERT INTO quiz (topic_id, question, option1, option2, option3, answer) VALUES " +
                "(1,'Con mèo tiếng Anh là gì?','Dog','Cat','Bird','Cat'), " +
                "(1,'Con chó tiếng Anh là gì?','Dog','Cat','Fish','Dog'), " +
                "(2,'Quả táo tiếng Anh là gì?','Banana','Apple','Orange','Apple'), " +
                "(2,'Quả chuối tiếng Anh là gì?','Banana','Grape','Mango','Banana'), " +
                "(3,'Bác sĩ tiếng Anh là gì?','Teacher','Doctor','Engineer','Doctor'), " +
                "(3,'Giáo viên tiếng Anh là gì?','Nurse','Doctor','Teacher','Teacher')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS topics");
        db.execSQL("DROP TABLE IF EXISTS words");
        db.execSQL("DROP TABLE IF EXISTS quiz");
        onCreate(db);
    }
}
