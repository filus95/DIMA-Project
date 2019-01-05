package com.easylib.dima.easylib.Main.QueueFragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.easylib.dima.easylib.Model.Book;
import com.easylib.dima.easylib.R;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.QueueHolder> {

    ArrayList<Book> queue_books;
    Context context;

    public QueueAdapter(Context context, ArrayList queue_books) {
        this.context = context;
        this.queue_books = queue_books;
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
        Book book = queue_books.get(position);
        holder.image.setImageDrawable(this.loadImageFromUrl(book.getImage()));
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        holder.num.setText(String.valueOf(book.getQueue()));

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

    private Drawable loadImageFromUrl(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "cover");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return queue_books.size();
    }

    static class QueueHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView title;
        protected TextView author;
        protected TextView num;

        public QueueHolder(View v) {
            super(v);
            image = v.findViewById(R.id.queue_img);
            title = v.findViewById(R.id.queue_title);
            author = v.findViewById(R.id.queue_author);
            num = v.findViewById(R.id.queue_num);
        }
    }
}
