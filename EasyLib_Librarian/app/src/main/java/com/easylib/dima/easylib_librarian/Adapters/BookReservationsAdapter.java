package com.easylib.dima.easylib_librarian.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.easylib.dima.easylib_librarian.R;

import java.util.ArrayList;

import AnswerClasses.Reservation;
import AnswerClasses.User;

public class BookReservationsAdapter extends RecyclerView.Adapter<BookReservationsAdapter.BookAvailableLibHolder> {

    ArrayList<Reservation> reservations;
    Context context;

    public BookReservationsAdapter(Context context, ArrayList<Reservation> reservations) {
        this.context = context;
        this.reservations = reservations;
    }

    @Override
    public BookAvailableLibHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item
        View v = LayoutInflater.from(context).inflate(R.layout.book_activity_reservation_item, parent, false);
        return new BookAvailableLibHolder(v);
    }

    @Override
    public void onBindViewHolder(BookAvailableLibHolder holder, int position) {
        // set the data in items TODO
        Reservation reservation = reservations.get(position);
        holder.userEmail.setText(String.valueOf (reservation.getUser_id ()));

        // implemented onClickListener event
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : onClick confirm reservation
            }
        });

    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    static class BookAvailableLibHolder extends RecyclerView.ViewHolder {
        protected Button button;
        protected TextView userEmail;

        public BookAvailableLibHolder(View v) {
            super(v);
            button = v.findViewById(R.id.book_activity_reservation_item_reserve_button);
            userEmail = v.findViewById(R.id.book_activity_reservation_item_name);
        }
    }
}
