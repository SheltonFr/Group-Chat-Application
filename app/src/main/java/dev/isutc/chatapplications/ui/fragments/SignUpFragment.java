package dev.isutc.chatapplications.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.UUID;

import dev.isutc.chatapplications.MainActivity;
import dev.isutc.chatapplications.databinding.FragmentSignUpBinding;
import dev.isutc.chatapplications.models.User;
import dev.isutc.chatapplications.ui.listeners.AuthFragmentsListener;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private AuthFragmentsListener activityCallback;
    private Uri profileUri;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.navigateSignin.setOnClickListener(e -> activityCallback.replaceFragment(new SignFragment()));
        binding.selectImage.setOnClickListener(e -> selectPhoto());
        binding.loginBtn.setOnClickListener(e -> createUser());
    }

    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    private void createUser() {
        String email = binding.emailInput.getText().toString();
        String name = binding.nameInput.getText().toString();
        String password = binding.passwordInput.getText().toString();

        if (password.isEmpty() || email.isEmpty() || name.isEmpty()) {
            Toast.makeText(this.getContext(), "Nome, Senha e Email devem ser fornecidos!", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        saveUserOnFirebase();
                    }
                }).addOnFailureListener(e -> Toast.makeText(SignUpFragment.this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void saveUserOnFirebase() {
        String fileName = UUID.randomUUID().toString();
        final StorageReference reference = FirebaseStorage.getInstance().getReference("/images/" + fileName);
        reference.putFile(profileUri)
                .addOnSuccessListener(taskSnapshot -> reference.getDownloadUrl()
                        .addOnSuccessListener(uri -> {
                            String uuid = FirebaseAuth.getInstance().getUid();
                            String username = binding.nameInput.getText().toString();
                            String profileUrl = uri.toString();

                            User user = new User(uuid, username, profileUrl);

                            FirebaseFirestore.getInstance().collection("users")
                                    .add(user)
                                    .addOnSuccessListener(documentReference -> {

                                        Log.i("TESTE", documentReference.getId());

                                        Intent intent = new Intent(SignUpFragment.this.getContext(), MainActivity.class);

                                        // Informa que a activity actual deve ser removida da stak de navegação
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                        startActivity(intent);

                                    })
                                    .addOnFailureListener(e -> Log.i("TESTE", e.getMessage()));
                        }))
                .addOnFailureListener(e -> Log.i("TESTE", e.getMessage()));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            profileUri = data.getData();

            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), profileUri);
                binding.selectedImage.setImageDrawable(new BitmapDrawable(bitmap));
                binding.selectImage.setAlpha(0);
            } catch (IOException e) {
                Log.i("TESTE", e.getMessage());
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}