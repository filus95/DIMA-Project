package com.easylib.dima.easylib.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    ArrayList<News> newsList;
    Context context;

    public NewsAdapter(Context context, ArrayList newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item
        View v = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false);
        return new NewsHolder(v);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        // set the data in items
        News news = newsList.get(position);
        Glide.with(context)
                .load(news.getImage_link())
                .into(holder.image);
        holder.name.setText(news.getTitle());
        holder.date.setText(news.getPost_date().toString());

        // implemented onClickListener event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: call method to see news_activity activity
                //Intent intent = new Intent(context, MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                //context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    static class NewsHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView name;
        protected TextView date;

        public NewsHolder(View v) {
            super(v);
            image = v.findViewById(R.id.news_item_img);
            name = v.findViewById(R.id.news_item_name);
            date = v.findViewById(R.id.news_item_date);
        }
    }
}
