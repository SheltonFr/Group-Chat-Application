package dev.isutc.chatapplications.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.isutc.chatapplications.R;
import dev.isutc.chatapplications.databinding.FragmentSiginBinding;


public class SignFragment extends Fragment {

    private FragmentSiginBinding binding;

    public SignFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSiginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}