package com.easylib.dima.easylib.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.Activities.Fragments.MainActivity;
import com.easylib.dima.easylib.Activities.LibraryActivity;
import com.easylib.dima.easylib.Activities.NewsActivity;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.News;

public class ImageTitleNewsAdapter extends RecyclerView.Adapter<ImageTitleNewsAdapter.ImageTitleNewsHolder> {

    private static final String NEWS_INFO = "News Info";
    ArrayList<News> newsList;
    Context context;

    public ImageTitleNewsAdapter(Context context, ArrayList newsList) {
            this.context = context;
            this.newsList = newsList;
    }

    @Override
    public ImageTitleNewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // inflate the item
            View v = LayoutInflater.from(context).inflate(R.layout.image_and_title_item, parent, false);
            return new ImageTitleNewsHolder(v);
     }

    @Override
    public void onBindViewHolder(ImageTitleNewsHolder holder, int position) {
            // set the data in items
            News news = newsList.get(position);
            holder.title.setText(news.getTitle());
            Glide.with(context)
                .load(news.getImage_link())
                .into(holder.image);

            // implemented onClickListener event
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newsIntent = new Intent (context, NewsActivity.class);
                    Bundle bundle = new Bundle ();
                    bundle.putSerializable(NEWS_INFO, news);
                    newsIntent.putExtras(bundle);
                    if(context instanceof MainActivity)
                        ((MainActivity)context).doUnbindService ();
                    if(context instanceof LibraryActivity)
                        ((LibraryActivity)context).doUnbindService ();
                    context.startActivity(newsIntent);
                }
            });
    }

    @Override
    public int getItemCount() { return newsList.size(); }

    static class ImageTitleNewsHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView title;

        public ImageTitleNewsHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image_and_title_item_image);
            title = v.findViewById(R.id.image_and_title_item_title);
        }
    }
}