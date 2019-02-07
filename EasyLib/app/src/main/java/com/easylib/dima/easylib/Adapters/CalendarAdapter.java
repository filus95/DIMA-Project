package com.easylib.dima.easylib.Adapters;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easylib.dima.easylib.R;

import java.net.UnknownServiceException;
import java.util.ArrayList;

import AnswerClasses.Book;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarHolder> {

    AnswerClasses.User userInfo;
    ArrayList<AnswerClasses.Reservation> reservations;
    Context context;
    ArrayList<String> dates;

    // Set the RecycleView of the single date
    private BookAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public CalendarAdapter(Context context, ArrayList<AnswerClasses.Reservation> reservations, ArrayList<String> dates, AnswerClasses.User userInfo) {
        this.context = context;
        this.reservations = reservations;
        this.dates = dates;
        this.userInfo = userInfo;
    }

    @Override
    public CalendarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_calendar_item, parent, false);
        return new CalendarHolder(v);
    }

    @Override
    public void onBindViewHolder(CalendarHolder holder, int position) {
        // set the data in items
        String date = dates.get(position);
        ArrayList<AnswerClasses.Book> booksForDate = new ArrayList<Book> ();

        holder.date.setText (date);
        if (position == 0 & date.equals ("To Take")) {
            for (AnswerClasses.Reservation r : reservations) {
                if (!r.isTaken ()){
                    booksForDate.add (r.getBook ());
                }
            }
        } else {
            for (AnswerClasses.Reservation r : reservations) {
                if (r.isTaken ()) {
                    if (date.equals (r.getEnd_res_date ().toString ().replace ("T", "  "))) {
                        booksForDate.add (r.getBook ());
                    }
                }
            }
        }

        // improve performance
        holder.recycleBooks.setHasFixedSize(true);
        // used linear layout
        mLayoutManager = new LinearLayoutManager(context);
        holder.recycleBooks.setLayoutManager(mLayoutManager);
        holder.recycleBooks.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        mAdapter = new BookAdapter (context, booksForDate, userInfo);
        holder.recycleBooks.setAdapter(mAdapter);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    static class CalendarHolder extends RecyclerView.ViewHolder {
        protected TextView date;
        protected RecyclerView recycleBooks;

        public CalendarHolder(View v) {
            super(v);
            date = v.findViewById(R.id.fragment_calendar_item_date);
            recycleBooks = v.findViewById(R.id.fragment_calendar_item_recycle);
        }
    }
}
