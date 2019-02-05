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

import AnswerClasses.Book;

public class ReadBooksAdapter extends RecyclerView.Adapter<ReadBooksAdapter.RatedBooksHolder> {

    ArrayList<Book> books;
    Context context;

    public ReadBooksAdapter(Context context, ArrayList books) {
        this.context = context;
        this.books = books;
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
        holder.rate.setText(Float.toString(7.4f));

        // implemented onClickListener event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : onClick set Preference
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
