package com.easylib.dima.easylib.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.Adapters.BookAdapter;
import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.Model.Book;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.Event;
import AnswerClasses.Event_partecipant;
import AnswerClasses.User;

public class EventActivity extends AppCompatActivity {

    private static final String EVENT_INFO = "Event Info";
    private static final String USER_INFO = "User Info";
    AnswerClasses.User userInfo;
    private Event eventInfo;
    private ArrayList<Event> joinedEvents;

    private TextView title;
    private TextView date;
    private TextView description;
    private TextView availableSeatsNum;
    private ImageView image;
    private TextView errorText;
    private Button reserveButton;

    //Comunication
    ConnectionService mBoundService;
    private boolean mIsBound;

    //For the communication Service
    private ServiceConnection mConnection = new ServiceConnection () {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = ((ConnectionService.LocalBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
        }
    };

    public void doBindService() {
        bindService(new Intent (EventActivity.this, ConnectionService.class), mConnection,
                Context.BIND_AUTO_CREATE);
        mIsBound = true;
        if(mBoundService!=null){
            mBoundService.IsBoundable();
        }
    }

    public void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
        unregisterReceiver(mMessageReceiver);
    }

    private String extractKey(Intent intent){
        Set<String> keySet = Objects.requireNonNull(intent.getExtras()).keySet();
        Iterator iterator = keySet.iterator();
        return (String)iterator.next();
    }

    //This is the handler that will manager to process the broadcast intent
    //This has to be created inside each activity that needs it ( almost anyone )
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver () {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = extractKey(intent);

            if (key.equals(Constants.GET_EVENTS_PER_USER)) {
                joinedEvents = (ArrayList<Event>) intent.getSerializableExtra (Constants.GET_EVENTS_PER_USER);
                for (Event event : joinedEvents) {
                    if (event.getId () == eventInfo.getId () & event.getIdLib () == eventInfo.getIdLib ()) {
                        setReservationButton (true);
                        return;
                    }
                }
                setReservationButton (false);
            }
            if (key.equals (Constants.INSERT_EVENT_PARTICIPANT)) {
                Boolean bool = (Boolean) intent.getSerializableExtra (Constants.INSERT_EVENT_PARTICIPANT);
                if (bool) {
                    Toast.makeText (context, "Seat Reserved", Toast.LENGTH_LONG).show ();
                    reserveButton.setText("Remove Reservation");
                    reserveButton.setTextColor(Color.RED);
                    reserveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removeReservation ();
                        }
                    });
                } else
                    Toast.makeText (context, "ERROR..", Toast.LENGTH_LONG).show ();
            }
            if (key.equals (Constants.REMOVE_EVENT_PARTECIPANT)) {
                Boolean bool = (Boolean) intent.getSerializableExtra (Constants.REMOVE_EVENT_PARTECIPANT);
                if (bool) {
                    Toast.makeText (context, "Reserved Seat Removed", Toast.LENGTH_LONG).show ();
                    reserveButton.setText("Reserve Seat");
                    reserveButton.setTextColor(Color.GREEN);
                    reserveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reserveSeat ();
                        }
                    });
                } else
                    Toast.makeText (context, "ERROR..", Toast.LENGTH_LONG).show ();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_activity);

        // Communication
        doBindService ();
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_EVENTS_PER_USER));
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.INSERT_EVENT_PARTICIPANT));
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.REMOVE_EVENT_PARTECIPANT));


        eventInfo = (Event) getIntent ().getSerializableExtra(EVENT_INFO);
        userInfo = (User) getIntent ().getSerializableExtra (USER_INFO);

        // get components
        title = (TextView) findViewById(R.id.event_activity_title);
        date = (TextView) findViewById(R.id.event_activity_date);
        description = (TextView) findViewById(R.id.event_activity_description);
        availableSeatsNum = (TextView) findViewById(R.id.event_activity_available_seats_num);
        image = (ImageView) findViewById(R.id.event_activity_image);
        errorText = (TextView) findViewById (R.id.event_activity_error_text);
        reserveButton = (Button) findViewById(R.id.event_activity_reservation_button);

        // set the components
        title.setText(eventInfo.getTitle());
        date.setText(eventInfo.getDate().toString().replace ("T", "  "));
        description.setText(eventInfo.getDescription());
        availableSeatsNum.setText(String.valueOf (eventInfo.getSeats()));
        Glide.with(this)
                .load(eventInfo.getImage_link())
                .into(image);

        new Handler ().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mBoundService != null) {
                    mBoundService.setCurrentContext(getApplicationContext());
                    mBoundService.sendMessage(Constants.GET_EVENTS_PER_USER, userInfo);
                }
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        doUnbindService();
    }

    public void setReservationButton(Boolean isAlreadyJoined) {
        if(isAlreadyJoined) {
            reserveButton.setText("Remove Reservation");
            reserveButton.setTextColor(Color.RED);
            reserveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeReservation ();
                }
            });
        } else if (eventInfo.getSeats () == 0) {
            reserveButton.setText("Reserve Seat");
            reserveButton.setTextColor(Color.GRAY);
            reserveButton.setEnabled (false);
            errorText.setVisibility (View.VISIBLE);
        } else {
                reserveButton.setText("Reserve Seat");
                reserveButton.setTextColor(Color.GREEN);
                reserveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reserveSeat ();
                    }
                });
        }
        reserveButton.setVisibility (View.VISIBLE);
    }

    public void reserveSeat() {
        AnswerClasses.Event_partecipant event_partecipant = new Event_partecipant ();
        event_partecipant.setEvent_id (eventInfo.getId ());
        event_partecipant.setIdLib (eventInfo.getIdLib ());
        event_partecipant.setPartecipant_id (userInfo.getUser_id ());
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.INSERT_EVENT_PARTICIPANT, event_partecipant);
        }
    }

    public void removeReservation() {
        AnswerClasses.Event_partecipant event_partecipant = new Event_partecipant ();
        event_partecipant.setEvent_id (eventInfo.getId ());
        event_partecipant.setIdLib (eventInfo.getIdLib ());
        event_partecipant.setPartecipant_id (userInfo.getUser_id ());
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.REMOVE_EVENT_PARTECIPANT, event_partecipant);
        }
    }
}
