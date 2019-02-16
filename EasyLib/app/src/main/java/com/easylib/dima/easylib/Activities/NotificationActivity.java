package com.easylib.dima.easylib.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.easylib.dima.easylib.Adapters.NewsAdapter;
import com.easylib.dima.easylib.Adapters.NotificationItemAdapter;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.R;
import com.easylib.dima.easylib.Utils.NotificationObj;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.News;

public class NotificationActivity extends AppCompatActivity {

    private static final String NOTIFICATIONS = "Notifications";
    private ArrayList<NotificationObj> newNotifiations;
    private ArrayList<NotificationObj> oldNotifiations;
    private ArrayList<NotificationObj> allNotifications = new ArrayList<NotificationObj> ();

    // recycle view
    private RecyclerView mRecyclerView;
    private NotificationItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //This is the handler that will manager to process the broadcast intent
    //This has to be created inside each activity that needs it ( almost anyone )
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = extractKey(intent);

            if (key.equals(Constants.NETWORK_STATE_DOWN)){
                Intent internetIntent = new Intent (context, NoInternetActivity.class);
                startActivity (internetIntent);
            }
        }
    };

    private String extractKey(Intent intent){
        Set<String> keySet = Objects.requireNonNull(intent.getExtras()).keySet();
        Iterator iterator = keySet.iterator();
        return (String)iterator.next();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        // Get Notifications
        SharedPreferences sp = getSharedPreferences(NOTIFICATIONS, MODE_PRIVATE);
        //Retrieve new notifications
        Gson gson = new Gson();
        String jsonText = sp.getString("New Notifications", null);
        Type type = new TypeToken<List<NotificationObj>> (){}.getType();
        newNotifiations = gson.fromJson(jsonText, type);
        if (newNotifiations != null) {
            Collections.reverse (newNotifiations);
            allNotifications.addAll (newNotifiations);
        }
        // Retrieve old notifications
        Gson gson2 = new Gson();
        String jsonText2 = sp.getString("Old Notifications", null);
        oldNotifiations = gson2.fromJson(jsonText2, type);
        if (oldNotifiations != null) {
            allNotifications.addAll (oldNotifiations);
        }

        if (allNotifications.size () != 0) {
            // Recycle View Settings
            mRecyclerView = (RecyclerView) findViewById (R.id.list_recycle);
            mRecyclerView.setHasFixedSize (true);
            if ((getResources ().getConfiguration ().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
                // used linear layout
                mLayoutManager = new LinearLayoutManager (this);
            } else if ((getResources ().getConfiguration ().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                // used grid layout
                mLayoutManager = new GridLayoutManager (this, 2);
            }
            mRecyclerView.setLayoutManager (mLayoutManager);
            mRecyclerView.setItemAnimator (new DefaultItemAnimator ());
            mRecyclerView.addItemDecoration(new DividerItemDecoration (mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
            // specify an adapter
            mAdapter = new NotificationItemAdapter (this, allNotifications);
            mRecyclerView.setAdapter (mAdapter);
        } else {
            mRecyclerView = (RecyclerView) findViewById (R.id.list_recycle);
            mRecyclerView.setVisibility (View.GONE);
            TextView textView = (TextView) findViewById (R.id.activity_list_text);
            textView.setVisibility (View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume ();
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.NETWORK_STATE_DOWN));
    }

    @Override
    protected void onPause() {
        super.onPause ();
        unregisterReceiver(mMessageReceiver);
        // put all news in the OldNews ArrayList
        if (newNotifiations != null) {
            // Set notifications no more new
            for (NotificationObj obj : newNotifiations) {
                obj.setIsNew (false);
            }
            SharedPreferences sp = getSharedPreferences(NOTIFICATIONS, MODE_PRIVATE);
            SharedPreferences.Editor spEditor = sp.edit ();
            sp.edit ().clear ().apply ();
            Gson gson = new Gson();
            Type type = new TypeToken<List<NotificationObj>> (){}.getType();
            String json = gson.toJson(allNotifications, type);
            spEditor.putString ("Old Notifications", json);
            spEditor.commit ();
        }
    }
}
