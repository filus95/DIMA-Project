package com.easylib.dima.easylib.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.Activities.BookActivity;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.Book;

public class BookAvailableLibAdapter extends RecyclerView.Adapter<BookAvailableLibAdapter.BookAvailableLibHolder> {

    ArrayList<AnswerClasses.LibraryDescriptor> libraries;
    Context context;

    public BookAvailableLibAdapter(Context context, ArrayList<AnswerClasses.LibraryDescriptor> libraries) {
        this.context = context;
        this.libraries = libraries;
    }

    @Override
    public BookAvailableLibHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item
        View v = LayoutInflater.from(context).inflate(R.layout.book_activity_lib_recycle_item, parent, false);
        return new BookAvailableLibHolder(v);
    }

    @Override
    public void onBindViewHolder(BookAvailableLibHolder holder, int position) {
        AnswerClasses.LibraryDescriptor library = libraries.get(position);
        holder.libName.setText (library.getLib_name ());

        // setButton
        if (library.getLibraryContent ().getBooks ().get (0).getQuantity_reserved () > 0) {
            // Reservation case
            holder.button.setTextColor (Color.GREEN);
            holder.button.setText ("Reserve");
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((BookActivity)context).addUserToBookReservationList (library);
                }
            });
        } else {
            // WaitingList case
            holder.button.setTextColor (Color.CYAN);
            holder.button.setText ("Add on Queue (" + library.getLibraryContent ().getBooks ().get (0).getWaitingQueueLength () + ")");
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((BookActivity)context).addUserToBookWaitingList (library);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return libraries.size();
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
