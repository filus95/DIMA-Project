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
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.Event;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    private static final String EVENT_INFO = "Event Info";
    private static final String USER_INFO = "User Info";
    AnswerClasses.User userInfo;
    ArrayList<Event> events;
    Context context;

    public EventAdapter(Context context, ArrayList events, AnswerClasses.User userInfo) {
        this.context = context;
        this.events = events;
        this.userInfo = userInfo;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item
        View v = LayoutInflater.from(context).inflate(R.layout.event_list_item, parent, false);
        return new EventHolder(v);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        // set the data in items
        Event event = events.get(position);
        Glide.with(context)
                .load(event.getImage_link())
                .into(holder.image);
        holder.title.setText(event.getTitle());
        holder.date.setText(event.getDate().toString().replace ("T", "  "));
        holder.seats.setText(String.valueOf (event.getSeats()));

        // implemented onClickListener event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newsIntent = new Intent (context, EventActivity.class);
                Bundle bundle = new Bundle ();
                bundle.putSerializable(EVENT_INFO, event);
                bundle.putSerializable (USER_INFO, userInfo);
                newsIntent.putExtras(bundle);
                context.startActivity(newsIntent);
            }
        });
    }

    @Override
    public int getItemCount() { return events.size();
    }

    static class EventHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView title;
        protected TextView date;
        protected TextView seats;

        public EventHolder(View v) {
            super(v);
            image = v.findViewById(R.id.event_list_item_image);
            title = v.findViewById(R.id.event_list_item_title);
            date = v.findViewById(R.id.event_list_item_date);
            seats = v.findViewById(R.id.event_list_item_seats);
        }
    }
}
