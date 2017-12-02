package com.apt.hailingfrequencies.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.apt.hailingfrequencies.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.Arrays;


// Base class inspired by
// https://stackoverflow.com/questions/3270206/same-option-menu-in-all-activities-in-android/3270254#3270254

public abstract class BaseActivity extends AppCompatActivity {
    // Request Code
    public static final int RC_SIGN_IN = 1;
    public static final String ANONYMOUS = "anonymous";

    // HTTP endpoints
    public static final String ENDPOINT = "http://hailing-frequencies-2017.appspot.com/";
    public static final String ENDPOINT_CONVERSATIONS = ENDPOINT + "api/conversations/";
    public static final String ENDPOINT_USERS = ENDPOINT + "api/users/";

    private String mUsername;

    // Firebase instance variables
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public FirebaseAuth.AuthStateListener getAuthStateListener() {
        return mAuthStateListener;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void createFirebaseAuthListener() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // user is signed in
                    onSignedInInitialize();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in cancelled!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    abstract void onSignedInInitialize();

    abstract void onSignedOutCleanup();


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
