package com.example.demo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView myName = findViewById(R.id.name);
        userImage = findViewById(R.id.imageView2);

        ImageButton whatsApp= findViewById(R.id.button4);
        ImageButton callUs = findViewById(R.id.button5);
        ImageButton privacyPolicy = findViewById(R.id.button6);
        ImageButton aboutUs = findViewById(R.id.button7);


        whatsApp.setOnClickListener(v -> {
            String contact = "+91 8484940289"; // use country code with your phone number
            String url = "https://api.whatsapp.com/send?phone=" + contact+"&text=Hello sir, I want betting tips from you.";
            try {
                PackageManager pm = getPackageManager();
                pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(MainActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

        callUs.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "8484940289"));
            startActivity(intent);
        });

        aboutUs.setOnClickListener(v -> {
            dialogFun();
        });

        privacyPolicy.setOnClickListener(v -> {
            dialogFun2();
        });



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            assert user != null;
            myName.setText(user.getDisplayName());
            String url = user.getPhotoUrl().toString();
            Glide.with(this).load(url).circleCrop().into(userImage);
        }

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    public void dialogFun(){
     final Dialog dialog = new Dialog(this);
     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
     dialog.setContentView(R.layout.dialog);
     dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width=WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;

        ((ImageButton) dialog.findViewById(R.id.close)).setOnClickListener(v -> {
            dialog.dismiss();
        });

        ((Button) dialog.findViewById(R.id.btn_call)).setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "8484940289"));
            startActivity(intent);
        });
        dialog.show();
        dialog.getWindow();

    }
    public void dialogFun2(){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.privacy);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width=WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;

        ((ImageButton) dialog.findViewById(R.id.close)).setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow();

    }



    public void SignOut(MenuItem item){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
}