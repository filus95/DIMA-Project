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
import com.easylib.dima.easylib.Activities.BookActivity;
import com.easylib.dima.easylib.Activities.Fragments.MainActivity;
import com.easylib.dima.easylib.Activities.LibraryActivity;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.Book;
import AnswerClasses.User;

public class ReadBooksAdapter extends RecyclerView.Adapter<ReadBooksAdapter.RatedBooksHolder> {

    private static final String BOOK_INFO = "Book Info";
    private static final String USER_INFO = "User Info";
    ArrayList<Book> books;
    User userInfo;
    Context context;

    public ReadBooksAdapter(Context context, ArrayList books, User userInfo) {
        this.context = context;
        this.books = books;
        this.userInfo = userInfo;
    }

    @Override
    public RatedBooksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item
        View v = LayoutInflater.from(context).inflate(R.layout.rated_books_item, parent, false);
        return new RatedBooksHolder(v);
    }

    @Override
    public void onBindViewHolder(RatedBooksHolder holder, int position) {
        // set the data in items
        Book book = books.get(position);
        Glide.with(context)
                .load(book.getImageLink())
                .into(holder.image);
        holder.rate.setText(String.valueOf (book.getAverageRating ().intValue ()));

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
        return books.size();
    }

    static class RatedBooksHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView rate;

        public RatedBooksHolder(View v) {
            super(v);
            image = v.findViewById(R.id.rated_books_item_image);
            rate = v.findViewById(R.id.rated_books_item_rate);
        }
    }
}
