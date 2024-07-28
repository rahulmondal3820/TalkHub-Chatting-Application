package com.example.tolkhub;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.tolkhub.Model.AuthenticationModel;
import com.example.tolkhub.Model.userModel;
import com.example.tolkhub.Util.FirebaseUtil;
import com.example.tolkhub.retrofit.authApi;
import com.example.tolkhub.retrofit.retrofitService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class userName extends AppCompatActivity {
    Button NextBtn;
    ProgressBar nameProgress;
    EditText inputName;
    String phoneNumber;
userModel  userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name);


        NextBtn = findViewById(R.id.NameBtn);
        inputName = findViewById(R.id.NameEditTex);
        nameProgress = findViewById(R.id.progress);
        inProgress(false);
        phoneNumber = getIntent().getExtras().getString("phoneNum");
        getUserName();

        NextBtn.setOnClickListener(v->{
            inProgress(true);
setUser();

        });
    }


    void setUser(){

        String userName = inputName.getText().toString();
        if (userName.length()<3 || userName.isEmpty()){
            inputName.setError("UserName should be at least 3");
            inProgress(false);
            return;
        }
        if(userModel!=null){

            userModel.setUsername(userName);
        }
        else {

            userModel = new userModel(phoneNumber,userName, Timestamp.now(),FirebaseUtil.currentUserId());
        }
        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                inProgress(false);
                if (task.isSuccessful()){
                    Intent intent = new Intent(userName.this,ChatList_activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }

   void getUserName(){
        inProgress(false);
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    userModel = task.getResult().toObject(userModel.class);
                    if (userModel!=null){
                       inputName.setText(userModel.getUsername());
                    }
                    else {
                        inProgress(false);
                    }
                }
            }
        });
    }


        public void inProgress ( boolean inProgress){
            if (inProgress) {
                nameProgress.setVisibility(View.VISIBLE);
                NextBtn.setVisibility(View.GONE);
            } else {
                nameProgress.setVisibility(View.GONE);
                NextBtn.setVisibility(View.VISIBLE);
            }
        }



}
