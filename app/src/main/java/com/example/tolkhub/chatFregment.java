package com.example.tolkhub;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tolkhub.Adapter.message_Recycler_Adepter;
import com.example.tolkhub.Model.chatRoomModel;
import com.example.tolkhub.Model.chattingMessageModel;
import com.example.tolkhub.Util.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.Arrays;

public class chatFregment extends Fragment {

    chatRoomModel chatRoomModel;
    chattingMessageModel chattingMessageModel;
    TextView nameMessage;
    String otherUserId;
    String chatroomId;
    String phone;
    RecyclerView recyclerView;
    message_Recycler_Adepter myAdapter;
    EditText enterMessage;
    ImageView enterButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("chatFregment", "onCreate: Fragment created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_fregment, container, false);
        Log.d("chatFregment", "onCreateView: View created");

        nameMessage = view.findViewById(R.id.nameMessage);
        enterMessage = view.findViewById(R.id.enterMessage);
        enterButton = view.findViewById(R.id.send);
        recyclerView = view.findViewById(R.id.chatRecyclerView);

        if (getArguments() != null) {
            String name = getArguments().getString("name");
            phone = getArguments().getString("phone");
            otherUserId = getArguments().getString("otherUserId");
            nameMessage.setText(name);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        enterButton.setOnClickListener((v) -> {
            String message = enterMessage.getText().toString().trim();
            if (message.isEmpty()) {
                return;
            }
            enterMessage.setText("");
            sendMessageToUser(message);
        });

        if (otherUserId != null) {
            getOrCreateChatroom();
            setChatRecyclerAdapter();
        } else {
            Log.e("chatFregment", "otherUserId is null");
        }

        new Handler().postDelayed(() -> enterMessage.requestFocus(), 200);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Log.d("chatFregment", "handleOnBackPressed: Back button pressed");
                Intent intent = new Intent(requireActivity(), ChatList_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                requireActivity().finish(); // Close the current activity
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("chatFregment", "onViewCreated: View created");
    }

    void setChatRecyclerAdapter() {
        Query query = FirebaseUtil.chatMessageReference(chatroomId).orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<chattingMessageModel> options = new FirestoreRecyclerOptions.Builder<chattingMessageModel>()
                .setQuery(query, chattingMessageModel.class).build();
        myAdapter = new message_Recycler_Adepter(options, getContext());

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(myAdapter);
        myAdapter.startListening();
        myAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    void sendMessageToUser(String message) {
        chatRoomModel.setLastMessageTimestamp(Timestamp.now());
        chatRoomModel.setLastMessageSenderId(FirebaseUtil.currentUserId());
        FirebaseUtil.ChatroomReference(chatroomId).set(chatRoomModel);

        chattingMessageModel chattingMessageModel = new chattingMessageModel(message, FirebaseUtil.currentUserId(), Timestamp.now());

        FirebaseUtil.chatMessageReference(chatroomId).add(chattingMessageModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    enterMessage.setText("");
                }
            }
        });
    }

    void getOrCreateChatroom() {
        chatroomId = FirebaseUtil.getChatroomId(FirebaseUtil.currentUserId(), otherUserId);

        FirebaseUtil.ChatroomReference(chatroomId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        chatRoomModel = document.toObject(chatRoomModel.class);
                    } else {
                        chatRoomModel = new chatRoomModel(chatroomId,
                                Arrays.asList(FirebaseUtil.currentUserId(), otherUserId),
                                Timestamp.now(),
                                "");
                        FirebaseUtil.ChatroomReference(chatroomId).set(chatRoomModel)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Chat room successfully created
                                        } else {
                                            Log.e("chatFregment", "Error creating chat room", task.getException());
                                        }
                                    }
                                });
                    }
                } else {
                    Log.e("chatFregment", "Error getting chat room", task.getException());
                }
            }
        });
    }
}
