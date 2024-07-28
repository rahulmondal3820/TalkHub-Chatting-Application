package com.example.tolkhub.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tolkhub.Model.chattingMessageModel;
import com.example.tolkhub.R;
import com.example.tolkhub.Util.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class message_Recycler_Adepter extends FirestoreRecyclerAdapter<chattingMessageModel, message_Recycler_Adepter.MessageViewHolder> {

    private Context context;

    public message_Recycler_Adepter(@NonNull FirestoreRecyclerOptions<chattingMessageModel> options, Context context) {
        super(options);
        this.context = context;
    }



    @Override
    protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull chattingMessageModel message) {
        if (message.getSenderId().equals(FirebaseUtil.currentUserId())) {
            holder.leftCardView.setVisibility(View.GONE);
            holder.rightCardView.setVisibility(View.VISIBLE);
            holder.rightTextMessage.setText(message.getMessage());


        } else {
            holder.rightCardView.setVisibility(View.GONE);
            holder.leftCardView.setVisibility(View.VISIBLE);
            holder.leftTextMessage.setText(message.getMessage());

        }
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_adptr_rt_recycler, parent, false);
        return new MessageViewHolder(view);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftCardView, rightCardView;
        TextView leftTextMessage, rightTextMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            leftCardView = itemView.findViewById(R.id.leftCardView);
            rightCardView = itemView.findViewById(R.id.rightCardView);
            leftTextMessage = itemView.findViewById(R.id.leftMessageText);
            rightTextMessage = itemView.findViewById(R.id.rightMessageText);
        }
    }
}
