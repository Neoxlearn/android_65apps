package com.gmail.neooxpro;
/* Главная активность приложения */
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;


public class MainActivity extends AppCompatActivity {
    static Toolbar toolB;
    static ContactService contactService;
    private boolean bound = false;
    private ServiceConnection sConn;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, ContactService.class);
        sConn = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName className, IBinder binder) {
                contactService = ((ContactService.ContactBinder) binder).getService();
                createContactListFragment(savedInstanceState);
                bound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName arg0) {
                bound = false;
            }
        };
        bindService(intent, sConn, Context.BIND_AUTO_CREATE);

        toolB = findViewById(R.id.toolbar);

    }

    protected void createContactListFragment(Bundle savedInstanceState){
        if (savedInstanceState == null){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft
                    .add(R.id.container, new ContactListFragment())
                    .commit();;
        }

    }

    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(sConn);
            bound = false;
        }
    }

}
