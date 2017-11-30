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

    private String mUsername;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;

    private DatabaseReference mConversationDatabaseReference;
    private DatabaseReference mConvMessagesDatabaseReference;
    private DatabaseReference mConvUsersDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_convo);

        mUsername = ANONYMOUS;

        // Initialize Firebase components
        mConversationDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Conversation");
        mConvMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("ConvMessages");
        mConvUsersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("ConvUsers");

        // TODO testing user store and retreive here.

        mFirebaseAuth = FirebaseAuth.getInstance();

        // Firebase Auth State Listener
        createFirebaseAuthListener();

        attachButtonClickListener();
    }


    // Creates three new database objects associated with the creation of a conversation:
    // 1) The conversation itself (Conversation)
    // 2) The messages within the conversation (ConvMessages)
    // 3) The users that in the conversation. (ConvUsers)

    public void createConversation() {
        // get conversation name from editText
        EditText convoNameEditText;
        convoNameEditText = (EditText) findViewById(R.id.edittext_convo_name);
        String convoName = convoNameEditText.getText().toString();

        // Create a new Conversation object

        //Log.v("TAG", "blah);




//        Conversation myConversation = new Conversation(convoName, myMessages, myUsers);

//        mConversationDatabaseReference.push().setValue(myConversation);
    }


    private void attachButtonClickListener() {
        final Button buttonCreateConvo = (Button) findViewById(R.id.button_create_convo);
        buttonCreateConvo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createConversation();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remove auth state listener.
        if (getAuthStateListener() != null) {
            mFirebaseAuth.removeAuthStateListener(getAuthStateListener());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Attach auth state listener.
        mFirebaseAuth.addAuthStateListener(getAuthStateListener());
    }
}