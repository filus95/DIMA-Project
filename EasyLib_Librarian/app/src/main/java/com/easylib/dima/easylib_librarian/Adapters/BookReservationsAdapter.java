package com.easylib.dima.easylib_librarian.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.easylib.dima.easylib_librarian.Activities.BookActivity;
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

        if (reservation.isTaken ()) {
            holder.userId.setText(String.valueOf (reservation.getUser_id ()) + "  -  To Return : " + reservation.getEnd_res_date ());
            holder.returnedButton.setVisibility (View.VISIBLE);
            // implemented onClickListener event
            holder.returnedButton.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    ((BookActivity)context).setBookReturned ();
                }
            });
        } else {
            holder.userId.setText(String.valueOf (reservation.getUser_id ()) + "  -  Reserved : " + reservation.getReservation_time ());
            holder.reserveButton.setVisibility (View.VISIBLE);
            // implemented onClickListener event
            holder.reserveButton.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    ((BookActivity)context).confirmReservation ();
                }
            });
            holder.removeButton.setVisibility (View.VISIBLE);
            // implemented onClickListener event
            holder.removeButton.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    ((BookActivity)context).removeReservation ();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    static class BookAvailableLibHolder extends RecyclerView.ViewHolder {
        protected Button returnedButton;
        protected Button reserveButton;
        protected Button removeButton;
        protected TextView userId;

        public BookAvailableLibHolder(View v) {
            super(v);
            returnedButton = v.findViewById(R.id.book_activity_reservation_item_returned_button);
            reserveButton = v.findViewById (R.id.book_activity_reservation_item_reserve_button);
            removeButton = v.findViewById (R.id.book_activity_reservation_item_remove_button);
            userId = v.findViewById(R.id.book_activity_reservation_item_name);
        }
    }
}
