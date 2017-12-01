package com.apt.hailingfrequencies.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.apt.hailingfrequencies.R;
import com.apt.hailingfrequencies.models.Conversation;

import java.util.ArrayList;

public class ConversationsAdapter extends ArrayAdapter<Conversation> {
    public ConversationsAdapter(Context context, ArrayList<Conversation> conversations) {
        super(context, 0, conversations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Conversation conversation = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_conversation, parent, false);
        }
        // Lookup view for data population
        TextView conversationName = (TextView) convertView.findViewById(R.id.conversation_name);
        TextView destroyDate = (TextView) convertView.findViewById(R.id.destroy_date);
        // Populate the data into the template view using the data object
        conversationName.setText(conversation.getName());
        destroyDate.setText(conversation.getDestroyDate());
        // Return the completed view to render on screen
        return convertView;
    }
}
