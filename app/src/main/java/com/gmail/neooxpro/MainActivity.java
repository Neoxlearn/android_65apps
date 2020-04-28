package com.gmail.neooxpro;
/* Главная активность приложения */
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    static Toolbar toolB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        toolB = findViewById(R.id.toolbar);
       // toolB.setTitle("Список контактов");
    }
}
