package dev.isutc.chatapplications.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import dev.isutc.chatapplications.MainActivity;
import dev.isutc.chatapplications.R;
import dev.isutc.chatapplications.databinding.FragmentSiginBinding;
import dev.isutc.chatapplications.ui.listeners.AuthFragmentsListener;


public class SignFragment extends Fragment {

    private FragmentSiginBinding binding;
    private AuthFragmentsListener activityCallback;

    public SignFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            activityCallback = (AuthFragmentsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + "Must Implement AuthFragmentsListener!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSiginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.loginBtn.setOnClickListener(e -> {
            String email = binding.emailInput.getText().toString();
            String password = binding.passwordInput.getText().toString();

            if (password.isEmpty() || email.isEmpty()) {
                Toast.makeText(SignFragment.this.getContext(), "Senha e Email devem ser fornecidos!", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.i("TESTE", task.getResult().getUser().getUid());
                            Intent intent = new Intent(SignFragment.this.getContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(ex -> Log.i("TESTE", ex.getMessage()));
        });

        binding.navigateSignup.setOnClickListener(e -> {
            activityCallback.replaceFragment(new SignUpFragment());
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}