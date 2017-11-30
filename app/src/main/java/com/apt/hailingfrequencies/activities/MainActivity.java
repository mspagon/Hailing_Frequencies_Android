package com.apt.hailingfrequencies.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.apt.hailingfrequencies.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.Arrays;

public class MainActivity extends BaseActivity {

    private String mUsername;
    private String mIdToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set username to ANONYMOUS
        mUsername = ANONYMOUS;

        // Firebase Auth State Listener
        createFirebaseAuthListener();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "Sign in cancelled!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remove auth state listener.
        if (getAuthStateListener() != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(getAuthStateListener());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Attach auth state listener.
        FirebaseAuth.getInstance().addAuthStateListener(getAuthStateListener());
    }

}
