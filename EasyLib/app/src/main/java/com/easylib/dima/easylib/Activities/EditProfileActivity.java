package com.easylib.dima.easylib.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easylib.dima.easylib.Activities.Fragments.MainActivity;
import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.R;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class EditProfileActivity extends AppCompatActivity {

    private static final String USER_INFO = "User Info";
    AnswerClasses.User userInfo;

    // Components
    EditText name;
    EditText surname;
    EditText pass;
    Button doneBt;

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
        bindService(new Intent (EditProfileActivity.this, ConnectionService.class), mConnection,
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

            if (key.equals(Constants.EDIT_PROFILE_INFO)) {
                Boolean bool = (Boolean) intent.getSerializableExtra (Constants.EDIT_PROFILE_INFO);
                if (bool) {
                    finish ();
                } else {
                    Toast.makeText(context, "Update Failed", Toast.LENGTH_LONG).show();
                }
            } if (key.equals(Constants.NOTIFICATION)){
                String message = (String) intent.getSerializableExtra(Constants.NOTIFICATION);
                Toast.makeText(context,message, Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.edit_profile_activity);

        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.EDIT_PROFILE_INFO));
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.NOTIFICATION));

        userInfo = (AnswerClasses.User) getIntent().getSerializableExtra(USER_INFO);

        // Get components reference
        name = (EditText) findViewById (R.id.editprofile_activity_name);
        surname = (EditText) findViewById (R.id.editprofile_activity_surname);
        pass = (EditText) findViewById (R.id.editprofile_activity_pass);
        doneBt = (Button) findViewById (R.id.editprofile_activity_done_button);

        name.setHint (userInfo.getName ());
        surname.setHint (userInfo.getSurname ());
        pass.setHint ("New Password");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        doUnbindService();
    }

    public void setNewUserInfo(View view) {
        AnswerClasses.User modifiedUser = new AnswerClasses.User ();
        modifiedUser.setUser_id (userInfo.getUser_id ());
        if (name.getText ().toString ().trim ().length () != 0)
            modifiedUser.setName (name.getText ().toString ());
        if (surname.getText ().toString ().trim ().length () != 0)
            modifiedUser.setSurname (surname.getText ().toString ());
        if (pass.getText ().toString ().trim ().length () != 0)
            modifiedUser.setPlainPassword (pass.getText ().toString ());
        if (name.getText ().toString ().trim ().length () == 0 & surname.getText ().toString ().trim ().length () == 0 &
                pass.getText ().toString ().trim ().length () == 0 ) {
            finish ();
        } else {
            if (mBoundService != null) {
                mBoundService.setCurrentContext (getApplicationContext ());
                mBoundService.sendMessage (Constants.EDIT_PROFILE_INFO, modifiedUser);
            }
        }
    }
}
