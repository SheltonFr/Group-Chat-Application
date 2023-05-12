package dev.isutc.chatapplications.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dev.isutc.chatapplications.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {

    private ActivityAuthBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}