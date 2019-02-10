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
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {

    private static final String BOOK_INFO = "Book Info";
    private static final String USER_INFO = "User Info";
    AnswerClasses.User userInfo;
    ArrayList<AnswerClasses.Book> books;
    Context context;

    public BookAdapter(Context context, ArrayList books, AnswerClasses.User userInfo) {
        this.context = context;
        this.books = books;
        this.userInfo = userInfo;
    }

    @Override
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item
        View v = LayoutInflater.from(context).inflate(R.layout.book_list_item, parent, false);
        return new BookHolder(v);
    }

    @Override
    public void onBindViewHolder(BookHolder holder, int position) {
        // set the data in items
        AnswerClasses.Book book = books.get(position);
        Glide.with(context)
                .load(book.getImageLink ())
                .into(holder.image);
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthors ().get (0));

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

    static class BookHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView title;
        protected TextView author;

        public BookHolder(View v) {
            super(v);
            image = v.findViewById(R.id.book_item_img);
            title = v.findViewById(R.id.book_item_title);
            author = v.findViewById(R.id.book_item_author);
        }
    }
}
