package com.easylib.dima.easylib.Search;

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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {

    ArrayList<Book> search_books;
    Context context;

    public SearchAdapter(Context context, ArrayList search_books) {
        this.context = context;
        this.search_books = search_books;
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item
        View v = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);
        return new SearchHolder(v);
    }

    @Override
    public void onBindViewHolder(SearchHolder holder, int position) {
        // set the data in items
        Book book = search_books.get(position);
        Glide.with(context)
                .load(book.getImage())
                .into(holder.image);
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());

        // implemented onClickListener event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: call method to see book activity
                //Intent intent = new Intent(context, MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                //context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return search_books.size();
    }

    static class SearchHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView title;
        protected TextView author;

        public SearchHolder(View v) {
            super(v);
            image = v.findViewById(R.id.search_img);
            title = v.findViewById(R.id.search_title);
            author = v.findViewById(R.id.search_author);
        }
    }
}
