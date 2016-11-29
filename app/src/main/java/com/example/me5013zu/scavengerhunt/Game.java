package com.example.me5013zu.scavengerhunt;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.PreferenceManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Timestamp;
import java.util.Date;

/**
 *  Game class holds information for a game
 */

public class Game extends FragmentActivity {

    private TextView mGameName;
    private int id;
    private TextView mDescription;
    private TextView startTime;
    private TextView endTime;
    private TextView mDateCreated;
    private CheckBox mCheckBox1;
    private CheckBox mCheckBox2;
    private CheckBox mCheckBox3;

    private static final String ALL_HUNTS_KEY = "All_hunts";
    private DatabaseReference mDatabaseReference;
    String username;

    LocalStorage localStorage;
    Firebase gameDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        localStorage = new LocalStorage(this);

        mGameName = (TextView) findViewById(R.id.game_name_textview);
        mDescription = (TextView) findViewById(R.id.game_description_textview);
        startTime = (TextView) findViewById(R.id.start_timestamp_textview);
        endTime = (TextView) findViewById(R.id.end_timestamp_textview);
        mDateCreated = (TextView) findViewById(R.id.date_created_textview);
        mCheckBox1 = (CheckBox) findViewById(R.id.item_one_checkbox);
        mCheckBox2 = (CheckBox) findViewById(R.id.item_two_checkbox);
        mCheckBox3 = (CheckBox) findViewById(R.id.item_three_checkbox);

        mCheckBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCheck();
            }
        });

        mCheckBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCheck();
            }
        });

        mCheckBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCheck();
            }
        });

        //configure database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference();
    }

    private void saveCheck() {

        String name = mGameName.getText().toString();
        String description = mDescription.getText().toString();
        boolean isChecked1 = mCheckBox1.isChecked();
        boolean isChecked2 = mCheckBox2.isChecked();
        boolean isChecked3 = mCheckBox3.isChecked();

        ScavengerHunt game = new ScavengerHunt(name, description, isChecked1, isChecked2, isChecked3);

        DatabaseReference newGame = mDatabaseReference.child(ALL_HUNTS_KEY).push();
        newGame.setValue(game);

        Toast.makeText(this, "Game saved", Toast.LENGTH_SHORT).show();
    }
}
