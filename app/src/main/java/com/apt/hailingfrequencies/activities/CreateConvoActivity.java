package com.apt.hailingfrequencies.activities;

import com.apt.hailingfrequencies.R;
import com.apt.hailingfrequencies.models.Conversation;
import com.apt.hailingfrequencies.models.Message;
import com.apt.hailingfrequencies.models.User;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateConvoActivity extends BaseActivity {

    // Request Code
    public static final int RC_SIGN_IN = 1;
    public static final String ANONYMOUS = "anonymous";

    private String mUsername;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private DatabaseReference mConversationDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_convo);

        mUsername = ANONYMOUS;

        // Initialize Firebase components
        mConversationDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Conversation");
        mFirebaseAuth = FirebaseAuth.getInstance();

        // Firebase Auth State Listener
        createFirebaseAuthListener();

        final Button buttonCreateConvo = (Button) findViewById(R.id.button_create_convo);
        buttonCreateConvo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postConversation();
            }

        });

    }

    public void postConversation() {
        EditText convoNameEditText;
        convoNameEditText = (EditText)findViewById(R.id.edittext_convo_name);

        String convoName = convoNameEditText.getText().toString();
        List<Message> myMessages= new ArrayList<Message>();

        List<User> myUsers = new ArrayList<User>();

        // create a new user.
        User user1 = new User(mUsername);
        User user2 = new User("John Doe");
        myUsers.add(user1);
        myUsers.add(user2);

        Message m1 = new Message("John Doe", "Hello");
        Message m2 = new Message(mUsername, "Hi back!");

        myMessages.add(m1);
        myMessages.add(m2);



        Conversation myConversation = new Conversation(convoName, myMessages, myUsers);

        mConversationDatabaseReference.push().setValue(myConversation);

    }


    private void createFirebaseAuthListener() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // user is signed in
                    onSignedInInitialize(user.getDisplayName());
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
                Toast.makeText(CreateConvoActivity.this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(CreateConvoActivity.this, "Sign in cancelled!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void onSignedInInitialize(String username) {
        mUsername = username;

        // attach database read listener
        // (get all the conversations and stuff from database.)

    }

    private void onSignedOutCleanup() {

        // Clear conversation adapter.
        // and detach database read listener
        // https://classroom.udacity.com/courses/ud0352/lessons/daa58d76-0146-4c52-b5d8-45e32a3dfb08/concepts/bc33923b-5328-4edd-96f4-6acc47c8429f
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remove auth state listener.
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Attach auth state listener.
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }







}


//
//    private String title;
//    private List<Message> messageList;
//    private List<User> userList;