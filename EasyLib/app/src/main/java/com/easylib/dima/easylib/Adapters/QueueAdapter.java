package com.easylib.dima.easylib.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.Activities.BookActivity;
import com.easylib.dima.easylib.Activities.Fragments.MainActivity;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.QueueHolder> {

    private static final String BOOK_INFO = "Book Info";
    private static final String USER_INFO = "User Info";
    private ArrayList<AnswerClasses.Book> waitingBooks;
    private AnswerClasses.User userInfo;
    Context context;

    public QueueAdapter(Context context, ArrayList<AnswerClasses.Book> waitingBooks, AnswerClasses.User userInfo) {
        this.context = context;
        this.waitingBooks = waitingBooks;
        this.userInfo = userInfo;
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
        holder.location.setText(book.getLib_name ());
        holder.num.setText(String.valueOf(book.getWaiting_position ()));

        // implemented onClickListener event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookIntent = new Intent (context, BookActivity.class);
                Bundle bundle = new Bundle ();
                bundle.putSerializable(BOOK_INFO, book);
                bundle.putSerializable (USER_INFO, userInfo);
                bookIntent.putExtras(bundle);
                context.startActivity(bookIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return waitingBooks.size();
    }

    public void removeItem(int position) {
        ((MainActivity)context).removeFromWaitingList (waitingBooks.get (position).getIdLibrary (), waitingBooks.get (position).getIdentifier ());
        waitingBooks.remove(position);
        // notify the item removed by position
        // to perform recyclerview delete animations
        notifyItemRemoved(position);
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
