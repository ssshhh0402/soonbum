package com.example.hong.dhproject3;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIIDService";
    private static final String token_Url = "https://sungchang-sos-button.firebaseio.com";

    @Override
    public void onTokenRefresh(){
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
        }
    private void sendRegistrationToServer(String token){

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("Token", token).build();

        Request request = new Request.Builder().url(token_Url).post(body).build();
        try{
            client.newCall(request).execute();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
