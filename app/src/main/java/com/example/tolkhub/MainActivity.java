package com.example.tolkhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;


import android.content.Intent;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tolkhub.R;


public class MainActivity extends AppCompatActivity {

    Animation topAnimation,bottomAnimation;

    ImageView plashImg;
    TextView plashText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(com.example.tolkhub.R.layout.activity_main);
        plashImg = findViewById(R.id.imageView);
        plashText = findViewById(R.id.textView);


        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_up_aimation);
        bottomAnimation =AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        plashImg.setAnimation(topAnimation);
        plashText.setAnimation(bottomAnimation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent nextActivity ;
                SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
              boolean check=  pref.getBoolean("flag",false);
              if (check){
                  nextActivity = new Intent(MainActivity.this,ChatList_activity.class);


              }else {
                  nextActivity = new Intent(MainActivity.this, Authentication.class);

              }
                startActivity(nextActivity);
                finish();
            }
        },3000);


    }

}