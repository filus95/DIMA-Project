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
import com.easylib.dima.easylib.Activities.EventActivity;
import com.easylib.dima.easylib.Activities.Fragments.MainActivity;
import com.easylib.dima.easylib.Activities.LibraryActivity;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.Event;

public class ImageTitleEventAdapter extends RecyclerView.Adapter<ImageTitleEventAdapter.ImageTitleEventHolder> {

    private static final String EVENT_INFO = "Event Info";
    private static final String USER_INFO = "User Info";
    AnswerClasses.User userInfo;
    ArrayList<Event> events;
    Context context;

    public ImageTitleEventAdapter(Context context, ArrayList events, AnswerClasses.User userInfo) {
        this.context = context;
        this.events = events;
        this.userInfo = userInfo;
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
                Intent eventIntent = new Intent (context, EventActivity.class);
                Bundle bundle = new Bundle ();
                bundle.putSerializable(EVENT_INFO, event);
                bundle.putSerializable (USER_INFO, userInfo);
                eventIntent.putExtras(bundle);
                if(context instanceof MainActivity)
                    ((MainActivity)context).doUnbindService ();
                if(context instanceof LibraryActivity)
                    ((LibraryActivity)context).doUnbindService ();
                context.startActivity(eventIntent);
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