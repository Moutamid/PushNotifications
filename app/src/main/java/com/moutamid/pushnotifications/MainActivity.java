package com.moutamid.pushnotifications;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {
    String Texttoken;
    EditText messeage;
    Button sentAll;
    Button sentUser;
    EditText title;
    EditText tokenET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("all");
        this.Texttoken = getSharedPreferences(FirebaseMessagingService.MY_PREFS_NAME, 0)
                .getString("name", "No name defined");

        TextView token = findViewById(R.id.tokenTxt);
        token.setText(Texttoken);

        this.title = (EditText) findViewById(R.id.editTextTextPersonName);
        this.messeage = (EditText) findViewById(R.id.editTextTextPersonName2);
        this.tokenET = (EditText) findViewById(R.id.editTextToken);
        this.sentAll = (Button) findViewById(R.id.sentToAll);
        this.sentUser = (Button) findViewById(R.id.sentToUser);
        this.sentAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity mainActivity = MainActivity.this;
                Toast.makeText(mainActivity, mainActivity.Texttoken, Toast.LENGTH_SHORT).show();
                if (MainActivity.this.title.getText().toString().isEmpty() || MainActivity.this.messeage.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Details", Toast.LENGTH_SHORT).show();
                } else {
                    new FcmNotificationsSender("/topics/all", MainActivity.this.title.getText().toString(), MainActivity.this.messeage.getText().toString(), MainActivity.this.getApplicationContext(), MainActivity.this).SendNotifications();
                }
            }
        });
        this.sentUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MainActivity.this.title.getText().toString().isEmpty() || MainActivity.this.messeage.getText().toString().isEmpty()|| MainActivity.this.tokenET.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Details", Toast.LENGTH_SHORT).show();
                } else {
                    new FcmNotificationsSender(tokenET.getText().toString(), MainActivity.this.title.getText().toString(), MainActivity.this.messeage.getText().toString(), MainActivity.this.getApplicationContext(), MainActivity.this).SendNotifications();
                }
            }
        });

    }
}