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

public class ImageTitleBookAdapter extends RecyclerView.Adapter<ImageTitleBookAdapter.ImageTitleBookHolder> {

    private static final String BOOK_INFO = "Book Info";
    private static final String USER_INFO = "User Info";
    AnswerClasses.User userInfo;
    ArrayList<Book> books;
    Context context;

    public ImageTitleBookAdapter(Context context, ArrayList books, AnswerClasses.User userInfo) {
        this.context = context;
        this.books = books;
        this.userInfo = userInfo;
    }

    @Override
    public ImageTitleBookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item
        View v = LayoutInflater.from(context).inflate(R.layout.image_and_title_item, parent, false);
        return new ImageTitleBookHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageTitleBookHolder holder, int position) {
        // set the data in items
        Book book = books.get(position);
        holder.title.setText(book.getTitle());
        Glide.with(context)
                .load(book.getImageLink())
                .into(holder.image);

        // implemented onClickListener event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookIntent = new Intent (context, BookActivity.class);
                Bundle bundle = new Bundle ();
                bundle.putSerializable(BOOK_INFO, book);
                bundle.putSerializable (USER_INFO, userInfo);
                bookIntent.putExtras(bundle);
                if(context instanceof MainActivity)
                    ((MainActivity)context).doUnbindService ();
                if(context instanceof LibraryActivity)
                    ((LibraryActivity)context).doUnbindService ();
                context.startActivity(bookIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class ImageTitleBookHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView title;

        public ImageTitleBookHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image_and_title_item_image);
            title = v.findViewById(R.id.image_and_title_item_title);
        }
    }
}