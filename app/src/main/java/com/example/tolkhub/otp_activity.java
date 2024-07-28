package com.example.tolkhub;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tolkhub.Util.getToast;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class otp_activity extends AppCompatActivity {
    String phoneNum;
    Button otpBtn;
    EditText otpEditText;
    ProgressBar otpProgress;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    String verificationCode;
    TextView ResendOtp;
    Long timeOutSec =30L;
    CountDownTimer timer;
    PhoneAuthProvider.ForceResendingToken resendingToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        phoneNum = getIntent().getExtras().getString("number");
        Toast.makeText(this, phoneNum, Toast.LENGTH_SHORT).show();

        otpBtn = findViewById(R.id.otpBtn);
        otpEditText = findViewById(R.id.otpTextView);
        otpProgress = findViewById(R.id.otpProgress);
        ResendOtp = findViewById(R.id.ResenTextView);
        mAuth = FirebaseAuth.getInstance();

       sendOtp(phoneNum,false);


       otpBtn.setOnClickListener(v-> {
           String enterOtp = otpEditText.getText().toString();
       PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,enterOtp);
       inLogin(credential);
           inProgress(true);
       });
  ResendOtp.setOnClickListener(v->{
      sendOtp(phoneNum,true);
  });
    }

    public void sendOtp(String phoneNum, boolean resendOpt){

        ResendTimer();
        inProgress(true);
        PhoneAuthOptions.Builder build = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNum)
                .setTimeout(timeOutSec, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        inLogin(phoneAuthCredential);
                        inProgress(false);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        getToast.Toast(otp_activity.this, "Verification is fail");

                        inProgress(false);
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verificationCode =s;
                        resendingToken = forceResendingToken;

                        getToast.Toast(otp_activity.this,"otp send successful");

                        inProgress(false);
                    }
                });
        if(resendOpt) {
            PhoneAuthProvider.verifyPhoneNumber(build.setForceResendingToken(resendingToken).build() );
        }

        PhoneAuthProvider.verifyPhoneNumber(build.build());

    }

    public void inProgress(boolean inProgress){
        if (inProgress) {
            otpProgress.setVisibility(View.VISIBLE);
            otpBtn.setVisibility(View.GONE);
        }
        else {
            otpProgress.setVisibility(View.GONE);
            otpBtn.setVisibility(View.VISIBLE);
        }
    }

    public void inLogin(PhoneAuthCredential phoneAuthCredential){
        inProgress(true);
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()){
                  Intent intent = new Intent(getApplicationContext(),userName.class);
                  intent.putExtra("phoneNum",phoneNum);
                  startActivity(intent);
                   finish();
              }
              else {
                  inProgress(false);
                   getToast.Toast(getApplicationContext(),"Otp verification fail");
              }
            }


        });
    }

    void ResendTimer() {
        ResendOtp.setEnabled(false);
        timer = new CountDownTimer(timeOutSec * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                ResendOtp.setText("Resend OTP in " + millisUntilFinished / 1000 + " seconds");
            }

            public void onFinish() {
                ResendOtp.setEnabled(true);
                ResendOtp.setText("Resend OTP");
            }
        }.start();
    }

}