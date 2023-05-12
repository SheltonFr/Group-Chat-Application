package dev.isutc.chatapplications.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.isutc.chatapplications.R;
import dev.isutc.chatapplications.databinding.FragmentSignUpBinding;
import dev.isutc.chatapplications.ui.listeners.AuthFragmentsListener;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private AuthFragmentsListener activityCallback;

    public SignUpFragment() {
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

        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.navigateSignin.setOnClickListener(e -> activityCallback.replaceFragment(new SignFragment()));
    }
}