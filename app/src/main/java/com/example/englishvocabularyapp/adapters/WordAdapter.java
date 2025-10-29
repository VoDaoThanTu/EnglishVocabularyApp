package com.example.englishvocabularyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.englishvocabularyapp.R;
import com.example.englishvocabularyapp.models.Word;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {
    private Context context;
    private List<Word> wordList;

    public WordAdapter(Context context, List<Word> wordList) {
        this.context = context;
        this.wordList = wordList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_word, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Word word = wordList.get(position);
        holder.txtEnglish.setText(word.getEnglish());
        holder.txtVietnamese.setText(word.getVietnamese());
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtEnglish, txtVietnamese;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEnglish = itemView.findViewById(R.id.txtEnglish);
            txtVietnamese = itemView.findViewById(R.id.txtVietnamese);
        }
    }
}