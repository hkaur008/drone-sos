package com.example.cora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity  {

    Button logoutBtn;
    Button sosBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        logoutBtn = findViewById(R.id.floating_logout_button);
        sosBtn = findViewById(R.id.sosBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        sosBtn.setEnabled(false);
        checkForSmsPermission();
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) ==
                PackageManager.PERMISSION_GRANTED)
            enableSOS();
        sosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager sms = SmsManager.getDefault();
                Toast.makeText(MainActivity.this, "button pressed", Toast.LENGTH_SHORT).show();
                String sent = "android.telephony.SmsManager.STATUS_ON_ICC_SENT";
                PendingIntent piSent = PendingIntent.getBroadcast(MainActivity.this, 0,new Intent(sent), 0);

                sms.sendTextMessage("+919729378395", null, "hello!", piSent, null);
            }
        });

//run kar

    }
    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    0);

        } else {
            // Permission already granted. Enable the SMS button.
            enableSOS();
        }
    }

    private void enableSOS(){
        sosBtn.setEnabled(true);
    }

}