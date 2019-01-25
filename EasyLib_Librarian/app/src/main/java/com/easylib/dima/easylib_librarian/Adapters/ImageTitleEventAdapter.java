package com.easylib.dima.easylib_librarian.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib_librarian.R;

import java.util.ArrayList;

import AnswerClasses.Event;

public class ImageTitleEventAdapter extends RecyclerView.Adapter<ImageTitleEventAdapter.ImageTitleEventHolder> {

    ArrayList<Event> events;
    Context context;

    public ImageTitleEventAdapter(Context context, ArrayList events) {
        this.context = context;
        this.events = events;
    }

    @Override
    public ImageTitleEventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item
        View v = LayoutInflater.from(context).inflate(R.layout.image_and_title_item, parent, false);
        return new ImageTitleEventHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageTitleEventHolder holder, int position) {
        // set the data in items
        Event event = events.get(position);
        holder.title.setText(event.getTitle());
        Glide.with(context)
                .load(event.getImage_link())
                .into(holder.image);

        // implemented onClickListener event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : onClick open Library Activity
            }
        });
    }

    @Override
    public int getItemCount() { return events.size(); }

    static class ImageTitleEventHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView title;

        public ImageTitleEventHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image_and_title_item_image);
            title = v.findViewById(R.id.image_and_title_item_title);
        }
    }
}