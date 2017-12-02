package com.apt.hailingfrequencies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

                    // For each conversation JSONObject returned create an object and add to adapter
                    for(int i = 0; i < jArray.length(); i++) {
                        Log.v("STATUS", jArray.getString(i));

                        JSONObject conversationJSON = jArray.getJSONObject(i);

                        Conversation singleConversation = new Conversation();
                        singleConversation.setDestroyDate(conversationJSON.getString("destroyDate"));
                        singleConversation.setId(conversationJSON.getString("id"));
                        singleConversation.setName(conversationJSON.getString("name"));

                        //TODO ASK PROFESSOR
                        //Why can we add to adapter but not conversation list?
                        //conversationList.add(singleConversation);
                        mConversationsAdapter.add(singleConversation);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //TODO ADD A CONVERSATIONS DATABASE LISTENER FOR REALTIME UPDATES


        // Click listener for listView
        mConversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Conversation conversation = mConversationsAdapter.getItem(position);
                Log.v("TAP", conversation.getName() + " - " + conversation.getId());

                // Get conversation id from adapter
                String conversationId = conversation.getId();

                // Start conversation activity and pass conversation Id
                Intent intent = new Intent(getBaseContext(), ConversationActivity.class);
                intent.putExtra("id", conversationId);
                startActivity(intent);
            }
        });




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
