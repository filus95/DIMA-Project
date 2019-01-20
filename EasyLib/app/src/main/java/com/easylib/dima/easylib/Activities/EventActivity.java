package com.easylib.dima.easylib.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.R;

import java.time.LocalDateTime;
import java.time.Month;

import AnswerClasses.Event;

public class EventActivity extends AppCompatActivity {

    private Event event;

    private TextView title;
    private TextView location;
    private TextView date;
    private TextView description;
    private TextView availableSeatsNum;
    private ImageView image;
    private Button reserveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        // JUST FOR TEST
        event = new Event();
        event.setImage_link("https://upload.wikimedia.org/wikipedia/en/thumb/7/70/Brisingr_book_cover.png/220px-Brisingr_book_cover.png");
        event.setTitle("Title Event");
        event.setDescription("skjdnfkjdnf dsjfdsjk fdsf jkds fkjds fkds fksjd f");
        event.setSeats(200);
        event.setDate(LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30));

        // get components
        title = (TextView) findViewById(R.id.event_activity_title);
        location = (TextView) findViewById(R.id.event_activity_location);
        date = (TextView) findViewById(R.id.event_activity_date);
        description = (TextView) findViewById(R.id.book_activity_description);
        availableSeatsNum = (TextView) findViewById(R.id.event_activity_available_seats_num);
        image = (ImageView) findViewById(R.id.event_activity_image);
        reserveButton = (Button) findViewById(R.id.event_activity_reservation_button);

        // set the components
        title.setText(event.getTitle());
        location.setText("via non la so (MI)");
        date.setText(event.getDate().toString());
        description.setText(event.getDescription());
        availableSeatsNum.setText(event.getSeats());
        Glide.with(this)
                .load(event.getImage_link())
                .into(image);
        if(event.getSeats() == 0)
            reserveButton.setEnabled(false);
    }

    public void setReservation(View view) {
        // TODO
    }
}
