package dev.isutc.chatapplications.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import dev.isutc.chatapplications.databinding.ActivityAuthBinding;
import dev.isutc.chatapplications.ui.fragments.SignFragment;
import dev.isutc.chatapplications.ui.fragments.SignUpFragment;
import dev.isutc.chatapplications.ui.listeners.AuthFragmentsListener;

public class AuthActivity extends AppCompatActivity implements AuthFragmentsListener {

    private ActivityAuthBinding binding;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(binding.fragmentContainerView.getId(), new SignFragment(), "SIGN")
                .commit();
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(binding.fragmentContainerView.getId(), fragment)
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .commit();
    }
}