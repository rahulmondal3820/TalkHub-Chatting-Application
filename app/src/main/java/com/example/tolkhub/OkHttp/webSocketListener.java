package com.example.tolkhub.OkHttp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class webSocketListener extends WebSocketListener {
    String userId;
    Context context;
    public webSocketListener(String userId,Context context){
        this.userId=userId;
        this.context=context;
    }
    public webSocketListener(Context context){
this.context=context;
    }
    @Override
    public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
        super.onOpen(webSocket, response);
        showToast(context,"Connection opened for user: " + userId);
    }

    @Override
    public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
        super.onClosed(webSocket, code, reason);

    }



    @Override
    public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
        Log.e("connection fail", "connection fail", t.getCause());
        showToast(context,"connection fail");
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
        super.onMessage(webSocket, text);

        showToast(context,text+webSocket);
    }

    // connection established

    public void showToast(final Context context, final String comment) {
        if (context != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context.getApplicationContext(), comment, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }




}
