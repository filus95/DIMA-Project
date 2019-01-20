package com.easylib.dima.easylib.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.Book;

public class BookAvailableLibAdapter extends RecyclerView.Adapter<BookAvailableLibAdapter.BookAvailableLibHolder> {

    // TODO : decide vhich parameters to pass... need also to know number of user on waiting list for each book

    ArrayList<Book> books;
    Boolean reservedByMe;
    String libIDReserved;
    Context context;

    public BookAvailableLibAdapter(Context context, Boolean reservedByMe, String libIDReserved, ArrayList<Book> books) {
        this.context = context;
        this.reservedByMe = reservedByMe;
        this.libIDReserved = libIDReserved;
        this.books = books;
    }

    @Override
    public BookAvailableLibHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item
        View v = LayoutInflater.from(context).inflate(R.layout.book_activity_lib_recycle_item, parent, false);
        return new BookAvailableLibHolder(v);
    }

    @Override
    public void onBindViewHolder(BookAvailableLibHolder holder, int position) {
        // set the data in items TODO
        /*Book book = books.get(position);
        Glide.with(context)
                .load(book.getImage())
                .into(holder.image);
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        holder.location.setText(book.getLocation());
    */

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookAvailableLibHolder extends RecyclerView.ViewHolder {
        protected Button button;
        protected TextView libName;

        public BookAvailableLibHolder(View v) {
            super(v);
            button = v.findViewById(R.id.book_activity_lib_recycle_item_button);
            libName = v.findViewById(R.id.book_activity_lib_recycle_item_library);
        }
    }
}
