package dev.isutc.chatapplications.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import dev.isutc.chatapplications.databinding.ActivityChatBinding;
import dev.isutc.chatapplications.models.Message;
import dev.isutc.chatapplications.models.User;
import dev.isutc.chatapplications.ui.adapters.MessageAdapter;

public class ChatActivity extends AppCompatActivity {

    private MessageAdapter adapter;
    private List<Message> messages;
    private User user;
    private User me;
    private EditText editChat;
    private ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        messages = new ArrayList<>();
        adapter = new MessageAdapter();

        binding.recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerChat.setAdapter(adapter);

        binding.btnChat.setOnClickListener(view -> {
            if (!binding.editChat.getText().toString().isEmpty()) {
                sendMessage();
            }
        });

        //binding.topAppBar.setNavigationIcon(R.drawable.back);
        // todo: Add the back arrow icon
        user = getIntent().getExtras().getParcelable("user");
        binding.topAppBar.setTitle(user.getUsername());


        FirebaseFirestore.getInstance()
                .collection("/users")
                .document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    me = documentSnapshot.toObject(User.class);
                    fetchMessages();
//                    binding.recyclerChat.setAdapter(new MessageAdapter(messages, true));
                }).addOnFailureListener(e -> Log.i("ERROR GETTING ME", e.getMessage(), e));

    }

    private void fetchMessages() {
        if (me != null) {

            String fromId = me.getUid();
            String toId = user.getUid();

            FirebaseFirestore.getInstance().collection("/conversations")
                    .document(fromId) /*fromId*/
                    .collection(toId) /*toId*/
                    .orderBy("timestamp", Query.Direction.ASCENDING)
                    .addSnapshotListener((queryDocumentSnapshots, e) -> {
                        List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();

                        if (documentChanges != null) {
                            for (DocumentChange doc : documentChanges) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    Message message = doc.getDocument().toObject(Message.class);
                                    Log.d("MESSAGES", message.getText());
                                    messages.add(message);
                                    adapter.setChatMessages(messages);
//                                    adapter.addMessage(message);
                                }
                            }
                        }
                    });

        }
    }

    private void sendMessage() {

        final String fromId = FirebaseAuth.getInstance().getUid();
        final String toId = user.getUid();
        long timestamp = System.currentTimeMillis();

        final Message message = new Message();
        message.setFromId(fromId);
        message.setToId(toId);
        message.setTimestamp(timestamp);
        message.setText(binding.editChat.getText().toString());

        if (!message.getText().isEmpty()) {
            FirebaseFirestore.getInstance()
                    .collection("/conversations")
                    .document(fromId)
                    .collection(toId)
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Log.d("MESSAGE SENT", documentReference.getId());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("MESSAGE NOT SENT", e.getMessage());
                        }
                    });

            FirebaseFirestore.getInstance()
                    .collection("/conversations")
                    .document(toId)
                    .collection(fromId)
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Log.d("MESSAGE RECEIVED", documentReference.getId());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Teste", e.getMessage(), e);
                        }
                    });
        }

        binding.editChat.setText("");
    }

}