package com.example.me5013zu.scavengerhunt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class SignInActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final int REQUEST_CODE_SIGN_IN = 12345;
    private static final String FIREBASE_USER_ID_PREF_KEY = "Firebase user id";
    private static final String USERS_PREFS = "User_preferences";
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final String Admin = "scavengerhuntapp2@gmail.com";

    private static final String TAG = "SIGN IN ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //two steps. User signs in with google and then exchange google token for firebase token
        mFirebaseAuth = FirebaseAuth.getInstance();

        //use google sign in to request the user data required by this app. Lets request basic data, the default
        //plus the user's email, although we are not going to use it here
        //if the other info was needed, you's chain on methods like requestProfile() before building
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d(TAG, "onAuthStateChanged for user: " + firebaseAuth.getCurrentUser());
                FirebaseUser user = firebaseAuth.getCurrentUser();
                SignInActivity.this.authStateChanged(user);
            }
        };

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.sign_in_button) {
            signIn();
        }
    }

    //method is called if user signs in or signs out. it is also called when the app is launched
    //so if the user has already authenticated, and their session has not timed out, they will not be
    //prompted to authenticate again. Instead, this will launch MainActivity or AdminMainActivity depending on authentication of admin or user
    private void authStateChanged(FirebaseUser user) {
        if (user == null) {
            Log.d(TAG, "user is signed out");
        } else {
            Log.d(TAG, "user is signed in");

            //save the user id in shared prefs
            Log.d(TAG, "The user id is = " + user.getUid() + " " + user.toString());

            SharedPreferences.Editor prefEditor = getSharedPreferences(USERS_PREFS, MODE_PRIVATE).edit();
            prefEditor.putString(FIREBASE_USER_ID_PREF_KEY, user.getUid());
            prefEditor.apply();

            //then boot up the app by starting the MainActivity/AdminMainActivity
            if(user.getEmail() == Admin) {
                Intent startAdminMainActivity = new Intent(this, MainActivity.class);
                startActivity(startAdminMainActivity);
            } else {
                Intent startGame = new Intent(this, Game.class);
                startActivity(startGame);
            }

            }

        }

    //this launches an activity where the user can sign into their google account, or even create a new account
    public void signIn() {
        Intent SignInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(SignInIntent, REQUEST_CODE_SIGN_IN);
    }

    //deals with the result from above method
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignIn(result);
        }
    }

    //this deals with the user signing into their google account. this checks for the success or failure
    //if success, credentials will be returned, that can be used to sign into Firebase on the user's behalf
    private void handleSignIn(GoogleSignInResult result) {
        Log.d(TAG, "handleSignIn for result " + result.getSignInAccount());
        if(result.isSuccess()) {
            //yay. now need to use these credentials to authenticate for FireBase.
            Log.d(TAG, "Google sign in success");
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogleCreds(account);
        } else {
            Log.e(TAG, "Google sign in failed");
            //this will fail if user has no internet connection or you haven't enabled google auth in firebase console.
            //and probably other reasons too. Check the log for the error message.
            Toast.makeText(this, "Google sign in failed", Toast.LENGTH_LONG).show();
        }
    }

    //this uses the credentials returned from a successful sign in to a google account to authenticate to Firebase
    //notice that this method doesn't do anything else with the results of auth - that's handled by an AuthStateListener
    private void firebaseAuthWithGoogleCreds(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        Log.d(TAG, "firebase auth attempt with creds " + credential);

        //attempt to sign in to firebase with the google credentials. the onCompleteListener is used for logging success
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "firebase auth success");
                        } else {
                            Log.d(TAG, "firebase auth fail");
                        }
                    }
                });
    }

    //Add and remove listeners for auth state as this activity stops and starts
    //The mAuthStateChangedListener is what will permit the user to continue with the app once authenticated.

    @Override
    public void onStart(){
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection to Google failed", Toast.LENGTH_LONG).show();
        Log.d(TAG, "CONNECTION FAILED" + connectionResult.getErrorMessage() + connectionResult.getErrorCode());
    }
}
