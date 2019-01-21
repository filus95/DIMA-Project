package com.easylib.dima.easylib.ConnectionLayer.Notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.easylib.dima.easylib.Activities.Fragments.MainActivity;
import com.easylib.dima.easylib.Activities.Login.LoginActivity;
import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.Constants;

//import com.easylib.dima.easylib.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{

    ConnectionService mBoundService;
    private boolean mIsBound;
    //For the communication Service
    private ServiceConnection mConnection = new ServiceConnection() {
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
        bindService(new Intent(this, ConnectionService.class), mConnection,
                Context.BIND_AUTO_CREATE);
        mIsBound = true;
        if(mBoundService!=null){
            mBoundService.IsBoundable();
        }
    }

    private void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        System.out.print("HERE");
//        doBindService();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        RemoteMessage.Notification notification = remoteMessage.getNotification();
        super.onMessageReceived(remoteMessage);
        Map<String, String> data = remoteMessage.getData();
        System.out.print("");        //
//        String title = remoteMessage.getData().get(“title”);
//        String body = remoteMessage.getData().get(“body”);
//        String objectId = remoteMessage.getData().get("object_id");
//        String objectType = remoteMessage.getData().get(objectType”);
//
//
//        Notification notification = new NotificationCompat.Builder(this)
//                .setContentTitle(data.get("title"))
//                .setContentText(data.get("body"))
//                .build();
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, R.string.default_notification_channel_id)
//                .setSmallIcon(R.drawable.notification_icon)
//                .setContentTitle(textTitle)
//                .setContentText(textContent)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
//        manager.notify(/*notification id*/data.get("object_id"), notification);

//        ShowNotification(notification, data);

        // TODO(developer): Handle FCM messages here.
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//            // Handle message within 10 seconds
////            handleNow();
//        }
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            String message = remoteMessage.getNotification().getBody();
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        mBoundService.sendMessage(Constants.NEW_NOTIFICATION_TOKEN, token);

    }


//    private void ShowNotification(RemoteMessage.Notification notification, Map<String, String> data) {
//
//        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//        Uri sound = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/raw/notification");
//
//
//        android.support.v4.app.NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setContentTitle(data.get("title"))
//                .setContentText(data.get("text"))
//                .setAutoCancel(true)
//                .setSound(sound)
//                .setContentIntent(pendingIntent)
//                .setContentInfo("ANY")
//                .setLargeIcon(icon)
//                .setColor(Color.RED)
//                .setSmallIcon(R.mipmap.ic_launcher);
//
//
//        notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
//        notificationBuilder.setLights(Color.YELLOW, 1000, 300);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, notificationBuilder.build());
//    }
//
}

