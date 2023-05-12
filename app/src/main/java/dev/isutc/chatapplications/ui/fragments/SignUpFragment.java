package dev.isutc.chatapplications.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;

import dev.isutc.chatapplications.databinding.FragmentSignUpBinding;
import dev.isutc.chatapplications.ui.listeners.AuthFragmentsListener;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private AuthFragmentsListener activityCallback;
    private Uri profileUri;

    public SignUpFragment() {
        // Required empty public constructor
    }


    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0) {
            profileUri = data.getData();

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), profileUri);
                binding.selectedImage.setImageDrawable(new BitmapDrawable(bitmap));
                binding.selectImage.setAlpha(0);
            } catch (IOException e){

            }

        }
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
        binding.selectImage.setOnClickListener(e -> selectPhoto());
    }
}