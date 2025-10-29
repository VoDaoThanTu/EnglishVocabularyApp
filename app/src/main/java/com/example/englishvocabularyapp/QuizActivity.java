package com.example.englishvocabularyapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {
    TextView txtQuestion, txtResult;
    Button btnA, btnB, btnC, btnNext;
    ArrayList<String[]> questions;
    int index = 0, correctCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtQuestion = findViewById(R.id.txtQuestion);
        txtResult = findViewById(R.id.txtResult);
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnNext = findViewById(R.id.btnNext);

        int topicId = getIntent().getIntExtra("topicId", -1);
        loadQuestions(topicId);
        showQuestion();

        btnA.setOnClickListener(v -> checkAnswer(btnA.getText().toString()));
        btnB.setOnClickListener(v -> checkAnswer(btnB.getText().toString()));
        btnC.setOnClickListener(v -> checkAnswer(btnC.getText().toString()));
        btnNext.setOnClickListener(v -> nextQuestion());
    }

    private void loadQuestions(int topicId) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        questions = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT english, vietnamese FROM words WHERE topic_id=?", new String[]{String.valueOf(topicId)});
        ArrayList<String[]> allWords = new ArrayList<>();
        while (c.moveToNext()) {
            allWords.add(new String[]{c.getString(0), c.getString(1)});
        }
        c.close();

        if (allWords.isEmpty()) return;

        for (String[] w : allWords) {
            ArrayList<String> choices = new ArrayList<>();
            choices.add(w[1]);
            Collections.shuffle(allWords);
            for (int i = 0; i < allWords.size() && choices.size() < 3; i++) {
                if (!allWords.get(i)[1].equals(w[1])) {
                    choices.add(allWords.get(i)[1]);
                }
            }

            // Đảm bảo có 3 đáp án
            while (choices.size() < 3) choices.add("Không biết");

            Collections.shuffle(choices);
            questions.add(new String[]{w[0], w[1], choices.get(0), choices.get(1), choices.get(2)});
        }

        Collections.shuffle(questions);
    }

    private void showQuestion() {
        if (questions.isEmpty()) {
            txtQuestion.setText("Không có câu hỏi nào trong chủ đề này.");
            btnA.setEnabled(false);
            btnB.setEnabled(false);
            btnC.setEnabled(false);
            btnNext.setEnabled(false);
            return;
        }

        if (index >= questions.size()) {
            txtQuestion.setText("Hoàn thành! Đúng " + correctCount + "/" + questions.size());
            btnA.setEnabled(false);
            btnB.setEnabled(false);
            btnC.setEnabled(false);
            btnNext.setEnabled(false);
            return;
        }

        String[] q = questions.get(index);
        txtQuestion.setText("Nghĩa của: " + q[0]);
        btnA.setText(q[2]);
        btnB.setText(q[3]);
        btnC.setText(q[4]);
        txtResult.setText("");
        btnA.setEnabled(true);
        btnB.setEnabled(true);
        btnC.setEnabled(true);
    }

    private void checkAnswer(String selected) {
        String correct = questions.get(index)[1];
        if (selected.equals(correct)) {
            txtResult.setText("Đúng!");
            correctCount++;
        } else {
            txtResult.setText("Sai! Đáp án đúng: " + correct);
        }

        // Khóa lại các nút
        btnA.setEnabled(false);
        btnB.setEnabled(false);
        btnC.setEnabled(false);
    }

    private void nextQuestion() {
        index++;
        showQuestion();
    }
}
