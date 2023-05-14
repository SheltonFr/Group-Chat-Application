package dev.isutc.chatapplications.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import dev.isutc.chatapplications.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding chatBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatBinding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(chatBinding.getRoot());


    }
}