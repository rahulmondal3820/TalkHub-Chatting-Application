package com.example.tolkhub.Util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FirebaseUtil {

    public static String currentUserId(){

        return FirebaseAuth.getInstance().getUid();
    }

    public static DocumentReference currentUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }

    public static Query fireBaseQuarry(String phoneN){

       return FirebaseFirestore.getInstance().collection("users").whereEqualTo("phone",phoneN);



    }
    public static DocumentReference ChatroomReference (String chatroomId){
        return  FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);

    }

    public static CollectionReference chatMessageReference(String chatroomId) {
        return ChatroomReference(chatroomId).collection("chats");
    }


    public static String getChatroomId(String user1, String user2) {
        if (user1 == null || user2 == null) {
            throw new IllegalArgumentException("User1 and User2 cannot be null");
        }
        if (user1.hashCode() < user2.hashCode()) {
            return user1+"_"+user2;
        }
        else {

            return user2+"_"+user1;
        }
    }
}
