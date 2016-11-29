package com.example.me5013zu.scavengerhunt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements Firebase.CompleteListener, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Button signOutButton = (Button) findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();

            }
        });
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Intent signInIntent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(signInIntent);
            }
        });
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection to Google failed", Toast.LENGTH_LONG).show();
        Log.d(TAG, "CONNECTION FAILED" + connectionResult.getErrorMessage() + connectionResult.getErrorCode());
    }
}
