package com.easylib.dima.easylib.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.Model.Book;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {

    ArrayList<Book> books;
    Context context;

    public BookAdapter(Context context, ArrayList books) {
        this.context = context;
        this.books = books;
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
        Book book = books.get(position);
        Glide.with(context)
                .load(book.getImage())
                .into(holder.image);
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        holder.location.setText(book.getLocation());

        // implemented onClickListener event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: call method to see book_activity activity
                //Intent intent = new Intent(context, MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                //context.startActivity(intent);
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
        protected TextView location;

        public BookHolder(View v) {
            super(v);
            image = v.findViewById(R.id.book_item_img);
            title = v.findViewById(R.id.book_item_title);
            author = v.findViewById(R.id.book_item_author);
            location = v.findViewById(R.id.book_item_location);
        }
    }
}
