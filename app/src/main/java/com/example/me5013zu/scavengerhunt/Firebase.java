package com.example.me5013zu.scavengerhunt;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Alex on 11/13/16.
 */

public class Firebase {

    public interface CompleteListener {
        void onFirebaseComplete(String actionTag, boolean success);
    }


    FirebaseDatabase mDatabase;

    Query mostRecentQuery;
    ValueEventListener mostRecentListener;

    private final String TAG = "FIREBASE INTERACTION";

    public Firebase() {
        mDatabase = FirebaseDatabase.getInstance();
    }
}
