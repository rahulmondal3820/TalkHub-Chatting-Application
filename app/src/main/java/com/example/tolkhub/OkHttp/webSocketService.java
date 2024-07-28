package com.example.tolkhub.OkHttp;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class webSocketService {
    private static OkHttpClient client;
    private static WebSocket webSocket;
    private static Context context;
    private static String userId;
    private static boolean isInitialized = false; // Flag to check if the object has been initialized

    // Constructor to initialize userId and context
    public webSocketService(String userId, Context context) {
        this.context = context;
        this.userId = userId;
    }

    // Default constructor
    public webSocketService() {

    }

    // Method to check if object has been initialized
    public static boolean isInitialized() {
        return isInitialized;
    }

    // Method for WebSocket initialization
    public static WebSocket initialization() {
        if (!isInitialized) {
            client = new OkHttpClient();
            Request request = new Request.Builder().url("ws://192.168.1.7:201?userId=" + userId).build();
            webSocket = client.newWebSocket(request, new webSocketListener(userId, context));
            isInitialized = true; // Set the flag to true after initialization
        }
        return webSocket;
    }
}
