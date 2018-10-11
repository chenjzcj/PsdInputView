package com.felix.psdinputview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.felix.psdinputview2.MyPsdInputView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MyPsdInputView mpiv = findViewById(R.id.mpiv);
        mpiv.setTask(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, mpiv.getPsd(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
