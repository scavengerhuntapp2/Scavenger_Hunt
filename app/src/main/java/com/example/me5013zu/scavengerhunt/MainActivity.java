package com.example.me5013zu.scavengerhunt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Firebase.CompleteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onFirebaseComplete(String action, boolean success) {
        //adds things to Firebase, so assume the action is add.
        //if there were other FB interactions and need to differentiate results, then check the value of action to display a message
        if (success) {
            Toast.makeText(this, "Report received - thank you!", Toast.LENGTH_LONG).show();
        } else {
            //fail
            Toast.makeText(this, "An error occurred sending your report. Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }
}
