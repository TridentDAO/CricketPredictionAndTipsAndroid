package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

public class LoginActivity extends AppCompatActivity {

    ImageView img;
    private static final String TAG = "LoginActivity";
    int AUTHUI_REQUEST_CODE = 10001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        img = (ImageView)findViewById(R.id.animationView);

//        img.setImageURI(R.id.);

    }
    public void handleLogin(View view) {

        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.GoogleBuilder()
                        .build()
        );
        Intent intent =  AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false, false)
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.ic_launcher_foreground)
                        .build();
        startActivityForResult(intent,AUTHUI_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == AUTHUI_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.d(TAG,"onActivityResult: " + user.getEmail());

                if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()){
                    Toast.makeText(this,"Welcome new User",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(this,"Welcome back again", Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(this,GetNumberActivity.class);
                startActivity(intent);
                this.finish();

            }else {
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if(response==null){
                    Log.d(TAG,"onActivityResult: the user has cancelled the sign in request");
                }else {
                    Log.e(TAG,"onActivityResult: ",response.getError());
                }
            }
        }
    }
}