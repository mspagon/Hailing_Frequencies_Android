package com.apt.hailingfrequencies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.apt.hailingfrequencies.R;
import com.apt.hailingfrequencies.models.Conversation;
import com.apt.hailingfrequencies.models.Message;
import com.apt.hailingfrequencies.util.Communicator;
import com.apt.hailingfrequencies.util.MessageAdapter;
import com.apt.hailingfrequencies.util.ResponseHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ConversationActivity extends BaseActivity {
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;

    private String mUserId;
    private String mAlias;

    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton;
    private String mConversationId;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        // Firebase Auth State Listener
        createFirebaseAuthListener();

        // get conversation extra from intent
        mConversationId = getIntent().getStringExtra("id");

        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("messages").child(mConversationId);


        //TODO set user alias?
        mAlias = "userAlias";

        // Initialize references to views
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageListView = (ListView) findViewById(R.id.messageListView);
        mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);
        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mSendButton = (Button) findViewById(R.id.sendButton);

        // Initialize message ListView and its adapter
        ArrayList<Message> messageList = new ArrayList<Message>();
        mMessageAdapter = new MessageAdapter(this, messageList);
        mMessageListView.setAdapter(mMessageAdapter);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
            }
        });

        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});


        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                String messageText = mMessageEditText.getText().toString();

                Log.v("SEND MESSAGE", messageText);

                // post messageText to API along with USER ALIAS
                //POST A MESSAGE
                String POST_MESSAGE_URL = "http://hailing-frequencies-2017.appspot.com/api/conversations/" + mConversationId + "/messages/";
                // String POST_MESSAGE_URL = "http://httpbin.org/post";
                Communicator postMessageCommunicator = new Communicator();
                RequestParams messageParams = new RequestParams();
                messageParams.add("text", messageText);
                messageParams.add("media_url", "");
                postMessageCommunicator.getTokenAndPerformHTTPRequest(POST_MESSAGE_URL, messageParams, "post", new ResponseHandler() {
                    @Override
                    public void accept(String res) {
                        Log.v("XYZ", res);
                    }
                });

                // Clear input box
                mMessageEditText.setText("");
            }
        });



        Communicator myCommunicator = new Communicator();

        String URL = ENDPOINT_CONVERSATIONS + mConversationId + "/messages/";
        Log.v("URL", URL);

        RequestParams emptyParams = new RequestParams();

        myCommunicator.getTokenAndPerformHTTPRequest(URL, emptyParams, "get", new ResponseHandler() {
            @Override
            public void accept(String res) {
                Log.v("HTTP RESPONSE", res);

                try {
                    JSONObject jObject = new JSONObject(res);
                    JSONArray jArray = jObject.getJSONArray("messages");

                    // For each conversation JSONObject returned create an object and add to adapter
                    for (int i = 0; i < jArray.length(); i++) {
                        // Log.v("STATUS", jArray.getString(i));

                        JSONObject messageJSON = jArray.getJSONObject(i);

                        Message singleMessage = new Message();
                        singleMessage.setUserAlias(messageJSON.getString("userAlias"));
                        singleMessage.setMediaURL(messageJSON.getString("mediaURL"));
                        singleMessage.setPostDate(messageJSON.getString("postDate"));
                        singleMessage.setText(messageJSON.getString("text"));

                        Log.v("Message", singleMessage.toString());

                        mMessageAdapter.add(singleMessage);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        //TODO ADD A CONVERSATIONS DATABASE LISTENER FOR REALTIME UPDATES



        //GET USER INFORMATION
        String USER_URL = "http://hailing-frequencies-2017.appspot.com/api/users/";
        myCommunicator.getTokenAndPerformHTTPRequest(USER_URL, emptyParams, "get", new ResponseHandler() {
            @Override
            public void accept(String res) {
                Log.v("USER RESPONSE", res);

                try {
                    JSONObject jObject = new JSONObject(res);
                    mUserId = jObject.getJSONObject("user").get("id").toString();

                    Log.v("USER ID", mUserId);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Message Message = dataSnapshot.getValue(Message.class);
                    mMessageAdapter.add(Message);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    @Override
    void onSignedInInitialize() {
        attachDatabaseReadListener();
    }

    @Override
    void onSignedOutCleanup() {
        mMessageAdapter.clear();
        detachDatabaseReadListener();
    }
}
