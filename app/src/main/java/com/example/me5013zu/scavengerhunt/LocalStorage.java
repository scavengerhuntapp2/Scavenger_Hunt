package com.example.me5013zu.scavengerhunt;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Alex on 11/29/16.
 */

public class LocalStorage {

    private static final String USERNAME_KEY = "username";
    private static final String GAME_KEY = "highscore";
    private static final String FIREBASE_KEY = "firebase key";

    private static final String TAG = "LOCAL STORAGE";

    public static final int NO_SCORE_RECORDED = -1;

    private Context context;

    LocalStorage(Context context) {
        this.context = context;
    }

    protected void writeUsername(String username) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(USERNAME_KEY, username).apply();

    }


    protected String fetchUsername() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(USERNAME_KEY, null);

    }

    protected void writeHighScore(int newScore) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(GAME_KEY, newScore).apply();


    }

    protected int getHighScore() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(GAME_KEY, NO_SCORE_RECORDED);    // -1 is the default, if no score exists. So if user gets 0 as a score, it will still register as a new high score.

    }


    public String getFirebaseKey() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(FIREBASE_KEY, null);
    }


    public void writeFirebaseKey(String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(FIREBASE_KEY, key).apply();


    }

}
