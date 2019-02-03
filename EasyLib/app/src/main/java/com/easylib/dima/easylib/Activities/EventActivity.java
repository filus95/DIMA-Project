package com.easylib.dima.easylib.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.R;

import AnswerClasses.Event;

public class EventActivity extends AppCompatActivity {

    private static final String EVENT_INFO = "Event Info";
    private Event event;

    private TextView title;
    private TextView date;
    private TextView description;
    private TextView availableSeatsNum;
    private ImageView image;
    private Button reserveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_activity);

        event = (Event) getIntent ().getSerializableExtra(EVENT_INFO);

        // get components
        title = (TextView) findViewById(R.id.event_activity_title);
        date = (TextView) findViewById(R.id.event_activity_date);
        description = (TextView) findViewById(R.id.event_activity_description);
        availableSeatsNum = (TextView) findViewById(R.id.event_activity_available_seats_num);
        image = (ImageView) findViewById(R.id.event_activity_image);
        reserveButton = (Button) findViewById(R.id.event_activity_reservation_button);

        // set the components
        title.setText(event.getTitle());
        date.setText(event.getDate().toString().replace ("T", "  "));
        description.setText(event.getDescription());
        availableSeatsNum.setText(String.valueOf (event.getSeats()));
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
