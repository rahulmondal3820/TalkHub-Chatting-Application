package com.example.tolkhub;

import static android.content.ContentValues.TAG;

import static com.google.common.util.concurrent.Futures.addCallback;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tolkhub.contact_list_fagment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ChatList_activity extends AppCompatActivity {
    private Fragment currentFragment;
    ImageView AddContactBtn;

    String userId;

    private static final int CONTACTS_PERMISSION_REQUEST_COD = 100;
    Toolbar toolbar;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        toolbar = findViewById(R.id.chatListToolBar);
        appBarLayout = findViewById(R.id.chatAppbar);
        setSupportActionBar(toolbar);


        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor  editor = pref.edit();
        editor.putBoolean("flag",true);
        editor.apply();


        AddContactBtn = findViewById(R.id.addContact);

        String name = getIntent().getStringExtra("contactName");
        String contactNumber = getIntent().getStringExtra("contactPhone");
        Toast.makeText(this, name + contactNumber, Toast.LENGTH_SHORT).show();
        AddContactBtn.setOnClickListener(v -> {
            if (checkPermission()) {
                Toast.makeText(this, "Permission_Granted", Toast.LENGTH_SHORT).show();
                callFragment();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, CONTACTS_PERMISSION_REQUEST_COD);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == CONTACTS_PERMISSION_REQUEST_COD) {
            callFragment();
        }
    }

    public boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    public void callFragment() {
        AddContactBtn.setVisibility(View.GONE);
        contact_list_fagment myFragment = new contact_list_fagment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main, myFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        currentFragment = fragment;
        updateToolbarVisibility();
    }

    private void updateToolbarVisibility() {
        if (currentFragment instanceof contact_list_fagment) {
            getSupportActionBar().show();
        } else {
            getSupportActionBar().hide();
        }
    }
}
