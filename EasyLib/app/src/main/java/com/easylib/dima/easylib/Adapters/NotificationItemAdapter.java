package com.easylib.dima.easylib.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easylib.dima.easylib.R;
import com.easylib.dima.easylib.Utils.NotificationObj;

import java.util.ArrayList;

public class NotificationItemAdapter extends RecyclerView.Adapter<NotificationItemAdapter.NotificationItemHolder> {

    ArrayList<NotificationObj> notifications;
    Context context;

    public NotificationItemAdapter(Context context, ArrayList<NotificationObj> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public NotificationItemAdapter.NotificationItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item
        View v = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
        return new NotificationItemAdapter.NotificationItemHolder (v);
    }

    @Override
    public void onBindViewHolder(NotificationItemAdapter.NotificationItemHolder holder, int position) {
        // set the data in items
        NotificationObj notificationObj = notifications.get(position);

        holder.date.setText (notificationObj.getDate ());
        holder.text.setText (notificationObj.getText ());

        if (notificationObj.getIsNew ()) {
            holder.text.setTypeface (Typeface.DEFAULT_BOLD);
            holder.date.setTypeface (Typeface.DEFAULT_BOLD);
        }
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    static class NotificationItemHolder extends RecyclerView.ViewHolder {
        protected TextView date;
        protected TextView text;

        public NotificationItemHolder(View v) {
            super(v);
            date = v.findViewById(R.id.notification_item_date);
            text = v.findViewById(R.id.notification_item_text);
        }
    }
}
