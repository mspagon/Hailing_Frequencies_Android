package com.apt.hailingfrequencies.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.apt.hailingfrequencies.R;
import com.apt.hailingfrequencies.models.Conversation;
import com.apt.hailingfrequencies.models.ConversationResponse;
import com.apt.hailingfrequencies.util.Communicator;
import com.apt.hailingfrequencies.util.ConversationsAdapter;
import com.apt.hailingfrequencies.util.ResponseHandler;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ListView mListView;
    private ArrayList<Conversation> mConversationList = new ArrayList<Conversation>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase Auth State Listener
        createFirebaseAuthListener();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mListView = (ListView) findViewById(R.id.conversation_list_view);

        Communicator myCommunicator = new Communicator();
        myCommunicator.getTokenAndPerformHTTPRequest(ENDPOINT_CONVERSATIONS, "get", new ResponseHandler() {
            @Override
            public void accept(String res) {
                Log.v("HTTP RESPONSE", res);
                populate(res);

//                try {
//                    JSONObject jObject = new JSONObject(res);
//                    JSONArray jArray = jObject.getJSONArray("conversations");
//
//                    ArrayList<Conversation> conversationList = new ArrayList<Conversation>();
//
//                    // For each conversation JSONObject returned
//                    for(int i = 0; i < jArray.length(); i++) {
//                        JSONObject conversationJSON = jArray.getJSONObject(i);
//
//                        Conversation singleConversation = new Conversation();
//                        singleConversation.setDestroyDate(conversationJSON.getString("destroyDate"));
//                        singleConversation.setId(conversationJSON.getInt("id"));
//                        singleConversation.setName(conversationJSON.getString("name"));
//
//                        conversationList.add(singleConversation);
//                        Log.v("STATUS", jArray.getString(i));
//                    }
//
//                    // Create the adapter to convert the array to views
//                    ConversationsAdapter adapter = new ConversationsAdapter(MainActivity.this, conversationList);
//
//                    // Attach the adapter to a ListView
//                    ListView listView = (ListView) findViewById(R.id.conversation_list_view);
//                    listView.setAdapter(adapter);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    public void populate(String res) {

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

                mConversationList.add(singleConversation);
                Log.v("STATUS", jArray.getString(i));
            }

            // Create the adapter to convert the array to views
            ConversationsAdapter adapter = new ConversationsAdapter(MainActivity.this, mConversationList);

            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.conversation_list_view);
            listView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
