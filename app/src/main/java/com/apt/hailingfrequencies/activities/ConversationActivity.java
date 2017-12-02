package com.apt.hailingfrequencies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.apt.hailingfrequencies.R;
import com.apt.hailingfrequencies.models.Conversation;
import com.apt.hailingfrequencies.models.Message;
import com.apt.hailingfrequencies.util.Communicator;
import com.apt.hailingfrequencies.util.ResponseHandler;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ConversationActivity extends BaseActivity {
    private String mConversationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase Auth State Listener
        createFirebaseAuthListener();

        // get conversation extra from intent
        mConversationId = getIntent().getStringExtra("id");

        Communicator myCommunicator = new Communicator();

        String URL = ENDPOINT_CONVERSATIONS + mConversationId + "/messages/";
        Log.v("URL", URL);

        myCommunicator.getTokenAndPerformHTTPRequest(URL, "get", new ResponseHandler() {
            @Override
            public void accept(String res) {
                Log.v("HTTP RESPONSE", res);

                try {
                    JSONObject jObject = new JSONObject(res);
                    JSONArray jArray = jObject.getJSONArray("messages");

                    // For each conversation JSONObject returned create an object and add to adapter
                    for(int i = 0; i < jArray.length(); i++) {
                        // Log.v("STATUS", jArray.getString(i));

                        JSONObject messageJSON = jArray.getJSONObject(i);

                        Message singleMessage = new Message();
                        singleMessage.setUserAlias(messageJSON.getString("userAlias"));
                        singleMessage.setMediaURL(messageJSON.getString("mediaURL"));
                        singleMessage.setPostDate(messageJSON.getString("postDate"));
                        singleMessage.setText(messageJSON.getString("text"));

                        Log.v("Message", singleMessage.toString());


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        //TODO ADD A CONVERSATIONS DATABASE LISTENER FOR REALTIME UPDATES

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(ConversationActivity.this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(ConversationActivity.this, "Sign in cancelled!", Toast.LENGTH_SHORT).show();
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
