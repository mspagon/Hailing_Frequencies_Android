package com.apt.hailingfrequencies.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apt.hailingfrequencies.R;
import com.apt.hailingfrequencies.models.Conversation;
import com.apt.hailingfrequencies.models.Message;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    public MessageAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Message message = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, parent, false);
        }
        // Lookup view for data population
        TextView messageText = (TextView) convertView.findViewById(R.id.message_text);
        TextView userAlias = (TextView) convertView.findViewById(R.id.message_alias);
        // Populate the data into the template view using the data object
        userAlias.setText(message.getUserAlias());
        messageText.setText(message.getText());
        // Return the completed view to render on screen
        return convertView;
    }


}
