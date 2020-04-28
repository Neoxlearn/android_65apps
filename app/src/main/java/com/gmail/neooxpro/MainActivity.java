package com.gmail.neooxpro;
/* Главная активность приложения */
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.FragmentTransaction;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    static Toolbar toolB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft
                    .add(R.id.container, new ContactListFragment())
                    .commit();
        }
        toolB = findViewById(R.id.toolbar);
    }
}
