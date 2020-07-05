package com.gmail.neooxpro.lib.ui;
/* Главная активность приложения */

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

import com.gmail.neooxpro.lib.R;
import com.gmail.neooxpro.lib.ui.view.ContactDetailsFragment;
import com.gmail.neooxpro.lib.ui.view.ContactListFragment;


public class MainActivity extends AppCompatActivity implements FragmentListener {
    private Toolbar toolB;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String id = getIntent().getStringExtra("id");
        if (id != null) {
            createContactDetailsFragment(savedInstanceState, id);
        } else {
            createContactListFragment(savedInstanceState);
        }
        toolB = findViewById(R.id.toolbar);
    }

    protected void createContactListFragment(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft
                    .add(R.id.container, new ContactListFragment())
                    .commit();
        }

    }

    protected void createContactDetailsFragment(@Nullable Bundle savedInstanceState, @NonNull String id) {
        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ContactDetailsFragment cdf = new ContactDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("args", id);
            cdf.setArguments(bundle);
            ft
                    .replace(R.id.container, cdf)
                    .commit();
        }

    }

    @Override
    @Nullable
    public Toolbar getToolbar() {
        return toolB;
    }
}
