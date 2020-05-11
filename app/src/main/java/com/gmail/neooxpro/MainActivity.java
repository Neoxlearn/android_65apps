package com.gmail.neooxpro;
/* Главная активность приложения */
//import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;


public class MainActivity extends AppCompatActivity implements GetContactService {
    static Toolbar toolB;
    static ContactService contactService;
    private boolean bound = false;
    private ServiceConnection sConn;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final int id = getIntent().getIntExtra("id", -1);
        Intent intent = new Intent(this, ContactService.class);

        sConn = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName className, IBinder binder) {
                contactService = ((ContactService.ContactBinder) binder).getService();
                if(id != -1){
                    createContactDetailsFragment(savedInstanceState, id);
                } else {
                    createContactListFragment(savedInstanceState);
                }
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
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft
                    .add(R.id.container, new ContactListFragment())
                    .commit();
        }

    }

    protected void createContactDetailsFragment(Bundle savedInstanceState, int id){
        if (savedInstanceState == null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ContactDetailsFragment cdf = new ContactDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("args", id);
            cdf.setArguments(bundle);
            ft
                    .replace(R.id.container, cdf)
                    .commit();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bound) {
            unbindService(sConn);
            bound = false;
        }
    }

    @Override
    public ContactService contactServiceForFragment() {
        return contactService;
    }
}
