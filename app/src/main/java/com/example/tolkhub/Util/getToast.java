package com.example.tolkhub.Util;

import android.content.Context;
import android.widget.Toast;

public class getToast {

    public static void  Toast(Context context,String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
