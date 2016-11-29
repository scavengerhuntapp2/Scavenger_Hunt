package com.example.me5013zu.scavengerhunt;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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


    FirebaseDatabase database;

    Query mostRecentQuery;
    ValueEventListener mostRecentListener;

    private final String TAG = "FIREBASE INTERACTION";

    public Firebase() {
        database = FirebaseDatabase.getInstance();
    }

    public void updateGame(ScavengerHunt hunt, final CompleteListener listener, final String tag) {

        DatabaseReference ref = database.getReference();
        DatabaseReference newChild = ref.push();
        newChild.setValue(hunt, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                notifyListener(databaseError, listener, tag);
            }
        });
    }

    private void notifyListener(DatabaseError databaseError, CompleteListener listener,  String tag) {

        //If there's a listener, notify it of success or error
        if (listener != null) {

            if (databaseError == null) {
                //Success
                Log.d(TAG, "completed successfully");
                listener.onFirebaseComplete(tag, true);
            }

            else {
                //error :(
                Log.e(TAG, "failed", databaseError.toException());
                listener.onFirebaseComplete(tag, false);

            }
        }
    }

}
