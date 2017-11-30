package com.apt.hailingfrequencies.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.apt.hailingfrequencies.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.Arrays;


// Base class inspired by
// https://stackoverflow.com/questions/3270206/same-option-menu-in-all-activities-in-android/3270254#3270254

public class BaseActivity extends AppCompatActivity {
    // Request Code
    public static final int RC_SIGN_IN = 1;
    public static final String ANONYMOUS = "anonymous";

    private String mUsername;
    private String mIdToken;

    // Firebase instance variables
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public FirebaseAuth.AuthStateListener getAuthStateListener() {
        return mAuthStateListener;
    }

    public void createFirebaseAuthListener() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // user is signed in
                    onSignedInInitialize(user);
                } else {
                    // user is signed out
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    //TODO remove smart lock in the future
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    private void onSignedInInitialize(FirebaseUser user) {
        // Get information about user
        mUsername = user.getDisplayName();

//        Task<GetTokenResult> tokenTask = user.getIdToken(true);
//        tokenTask.addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
//            public void onComplete(@NonNull Task<GetTokenResult> task) {
//                if (task.isSuccessful()) {
//                    mIdToken = task.getResult().getToken();
//                    // Send token to your backend via HTTPS
//                    // ...
//                } else {
//                    // Handle error -> task.getException();
//                }
//            }
//        });

//        try {
//            // Wait for token to be returned
//            tokenTask.wait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        if (mIdToken != null) {
//            Log.v("TOKEN", mIdToken);
//        }

        // Attach database read listener
        // (get all the conversations and stuff from database.)

    }

    private void onSignedOutCleanup() {

        // Clear conversation adapter.
        // and detach database read listener
        // https://classroom.udacity.com/courses/ud0352/lessons/daa58d76-0146-4c52-b5d8-45e32a3dfb08/concepts/bc33923b-5328-4edd-96f4-6acc47c8429f
    }


    // Populate the menu bar
    // This is called by the system when the activity begins
    // https://developer.android.com/guide/topics/ui/menus.html
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    // Event listener for menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
