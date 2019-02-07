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

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarHolder> {

    ArrayList<AnswerClasses.Book> books;
    Context context;
    ArrayList<String> dates;
    // Set the RecycleView of the single date
    private BookAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public CalendarAdapter(Context context, ArrayList books) {
        this.context = context;
        this.books = books;

        // FOR TEST TODO : get all DIFFERENT DATES and fill the dates ArrayList
        dates = new ArrayList<String>();
        this.dates.add("00/00/0000");
        this.dates.add("11/11/1111");
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
        // TODO : get the books corresponding to that DATE

        // improve performance
        holder.recycleBooks.setHasFixedSize(true);
        // used linear layout
        mLayoutManager = new LinearLayoutManager(context);
        holder.recycleBooks.setLayoutManager(mLayoutManager);
        holder.recycleBooks.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        mAdapter = new BookAdapter (context, books);
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
