package dev.isutc.chatapplications.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dev.isutc.chatapplications.R;
import dev.isutc.chatapplications.databinding.FragmentContactsListBinding;
import dev.isutc.chatapplications.models.User;
import dev.isutc.chatapplications.ui.adapters.ContactsAdapter;


public class ContactsListFragment extends Fragment {


    private FragmentContactsListBinding binding;
    private List<User> users;
    private ContactsAdapter adapter;


    public ContactsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContactsListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchUsers();
    }

    private void fetchUsers() {
        users = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("/users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("TESTE", error.getMessage(), error);
                            return;
                        }

                        List<DocumentSnapshot> docs = value.getDocuments();
                        docs.forEach(doc -> {
                            User user = doc.toObject(User.class);
                            users.add(user);
                            Log.i("TESTE", user.getUsername());
                        });

                        users.forEach(user -> System.out.println("My Name: " + user.getUsername()));
                        adapter = new ContactsAdapter(users);
                        binding.contactsList.setAdapter(adapter);
                    }
                });

    }
}