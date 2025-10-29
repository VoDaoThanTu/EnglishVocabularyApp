package com.example.englishvocabularyapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.englishvocabularyapp.R;
import com.example.englishvocabularyapp.TopicActivity;
import com.example.englishvocabularyapp.models.Topic;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {
    private Context context;
    private List<Topic> topicList;

    public TopicAdapter(Context context, List<Topic> topicList) {
        this.context = context;
        this.topicList = topicList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Topic topic = topicList.get(position);
        holder.txtName.setText(topic.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TopicActivity.class);
            intent.putExtra("topicId", topic.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtTopicName);
        }
    }
}
