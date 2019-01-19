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

import AnswerClasses.Event;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    ArrayList<Event> events;
    Context context;

    public EventAdapter(Context context, ArrayList events) {
        this.context = context;
        this.events = events;
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
        // TODO : add location
        holder.location.setText("location");
        holder.date.setText(event.getDate().toString());
        holder.seats.setText(event.getSeats());

        // implemented onClickListener event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: call method to see event_activity activity
                //Intent intent = new Intent(context, MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                //context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return events.size();
    }

    static class EventHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView title;
        protected TextView location;
        protected TextView date;
        protected TextView seats;

        public EventHolder(View v) {
            super(v);
            image = v.findViewById(R.id.event_list_item_image);
            title = v.findViewById(R.id.event_list_item_title);
            location = v.findViewById(R.id.event_list_item_location);
            date = v.findViewById(R.id.event_list_item_date);
            seats = v.findViewById(R.id.event_list_item_seats);
        }
    }
}
