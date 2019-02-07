package com.easylib.dima.easylib.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.Activities.Fragments.MainActivity;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.QueueHolder> {

    private static final String USER_INFO = "User Info";
    private ArrayList<AnswerClasses.Book> waitingBooks;
    private ArrayList<Integer> waitingUsers;
    private ArrayList<String> waitingLocations;
    private AnswerClasses.User userInfo;
    Context context;

    public QueueAdapter(Context context, ArrayList<AnswerClasses.Book> waitingBooks, ArrayList<Integer> waitingUsers, ArrayList<String> waitingLocations, AnswerClasses.User userInfo) {
        this.context = context;
        this.waitingBooks = waitingBooks;
        this.userInfo = userInfo;
        this.waitingUsers = waitingUsers;
        this.waitingLocations = waitingLocations;
    }

    @Override
    public QueueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_queue_item, parent, false);
        return new QueueHolder(v);
    }

    @Override
    public void onBindViewHolder(QueueHolder holder, int position) {
        // set the data in items
        AnswerClasses.Book book = waitingBooks.get(position);
        Glide.with(context)
                .load(book.getImageLink ())
                .into(holder.image);
        holder.title.setText(book.getTitle ());
        holder.location.setText(waitingLocations.get (position));
        holder.num.setText(String.valueOf(waitingUsers.get (position)));

        // implemented onClickListener event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: call method to see book_activity activity
            }
        });
    }

    @Override
    public int getItemCount() {
        return waitingBooks.size();
    }

    public void removeItem(int position) {
        waitingBooks.remove(position);
        // notify the item removed by position
        // to perform recyclerview delete animations
        notifyItemRemoved(position);
        ((MainActivity)context).removeFromWaitingList (waitingBooks.get (position).getIdLibrary ());
    }

    static public class QueueHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView title;
        protected TextView location;
        protected TextView num;

        protected LinearLayout viewBackground, viewForeground;

        public QueueHolder(View v) {
            super(v);
            image = v.findViewById(R.id.queue_img);
            title = v.findViewById(R.id.queue_title);
            location = v.findViewById(R.id.queue_location);
            num = v.findViewById(R.id.queue_num);
            viewBackground = v.findViewById(R.id.queue_background);
            viewForeground = v.findViewById(R.id.queue_foreground);
        }
    }
}
