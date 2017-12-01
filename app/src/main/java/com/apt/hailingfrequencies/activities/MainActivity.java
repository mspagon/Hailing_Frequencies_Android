package com.apt.hailingfrequencies.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.apt.hailingfrequencies.R;
import com.apt.hailingfrequencies.models.Conversation;
import com.apt.hailingfrequencies.util.Communicator;
import com.apt.hailingfrequencies.util.ConversationsAdapter;
import com.apt.hailingfrequencies.util.ResponseHandler;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private ListView mConversationListView;
    private ConversationsAdapter mConversationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase Auth State Listener
        createFirebaseAuthListener();

        // Initialize references to views
        mConversationListView = (ListView) findViewById(R.id.conversation_list_view);

        // Initialize message ListView and its adapter
        ArrayList<Conversation> conversationList = new ArrayList<Conversation>();
        mConversationsAdapter = new ConversationsAdapter(MainActivity.this, conversationList);
        mConversationListView.setAdapter(mConversationsAdapter);

        Communicator myCommunicator = new Communicator();

        myCommunicator.getTokenAndPerformHTTPRequest(ENDPOINT_CONVERSATIONS, "get", new ResponseHandler() {
            @Override
            public void accept(String res) {
                Log.v("HTTP RESPONSE", res);

                try {
                    JSONObject jObject = new JSONObject(res);
                    JSONArray jArray = jObject.getJSONArray("conversations");

                    // For each conversation JSONObject returned
                    for(int i = 0; i < jArray.length(); i++) {
                        JSONObject conversationJSON = jArray.getJSONObject(i);

                        Conversation singleConversation = new Conversation();
                        singleConversation.setDestroyDate(conversationJSON.getString("destroyDate"));
                        singleConversation.setId(conversationJSON.getInt("id"));
                        singleConversation.setName(conversationJSON.getString("name"));

                        mConversationsAdapter.add(singleConversation);
                        Log.v("STATUS", jArray.getString(i));
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
