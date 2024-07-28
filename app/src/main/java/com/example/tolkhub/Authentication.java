package com.example.tolkhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tolkhub.R;
import com.hbb20.CountryCodePicker;

public class Authentication extends AppCompatActivity {
EditText auEditText;

ProgressBar progressBar;
CountryCodePicker countryCodePicker;
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        btn = findViewById(R.id.AuBtn);
        auEditText=findViewById(R.id.AuEditTextView);
        countryCodePicker=findViewById(R.id.CodePicker);
        progressBar=findViewById(R.id.progress);
        countryCodePicker.registerCarrierNumberEditText(auEditText);
        progressBar.setVisibility(View.GONE);

        btn.setOnClickListener((v)-> {

            Intent intent = new Intent(Authentication.this, otp_activity.class);

            if (countryCodePicker.isValidFullNumber()) {
                intent.putExtra("number", countryCodePicker.getFullNumberWithPlus());
                startActivity(intent);

            }

            else{
                Toast.makeText(this, "Not Valid", Toast.LENGTH_SHORT).show();
        }
        });




    }
}