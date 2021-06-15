package com.example.demo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

public class GetNumberActivity extends AppCompatActivity {


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_number);

        Button btn = findViewById(R.id.button3);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        SimpleDateFormat tf = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        String DateTime = df.format(c) + "  " + tf.format(c);

        EditText number = findViewById(R.id.editTextPhone);
        btn.setOnClickListener(v -> {
            if(isValidPhone(number.getText().toString())){
                Submit(DateTime, number);
            }else {
                number.setError("Phone number is not valid");
                number.requestFocus();
            }
        });
    }


    private boolean isValidPhone(CharSequence phone){
        boolean check;
        if(!Pattern.matches("[a-zA-Z]+", phone))
        {
            check = phone.length() >= 10 && phone.length() <= 10;
        }
        else
        {
            check=false;
        }
        return check;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void Submit(String DateTime, EditText number) {
        String num = number.getText().toString();
        ArrayList<String> userData = new ArrayList<String>();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            // User is Login
            String displayName = user.getDisplayName();
            String email = user.getEmail();
            // If the above were null, iterate the provider data
            // and set with the first non null data
            for (UserInfo userInfo : user.getProviderData()) {
                if (displayName == null && userInfo.getDisplayName() != null) {
                    displayName = userInfo.getDisplayName();
//                    Toast.makeText(this, "username is available", Toast.LENGTH_LONG).show();
                }
            }

//            String FullMsg = "Name: " + displayName + "\n" + "Email: " + email + "\n" + "Phone No.: " + num + "\n" + "Date: " + DateTime;



            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> user1 = new HashMap<>();
            user1.put("name", displayName);
            user1.put("email", email);
            user1.put("number", num);
            user1.put("date", DateTime);
            db.collection("users")
                    .add(user1)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(this,e,Toast.LENGTH_LONG).show();
                        }
                    });


            Intent intent = new Intent(GetNumberActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}