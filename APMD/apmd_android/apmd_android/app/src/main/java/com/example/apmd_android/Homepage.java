package com.example.apmd_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Homepage extends AppCompatActivity {

    Button b1, b2, b3, btcomplaint,btprofile,b5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        b1 = (Button) findViewById(R.id.btlogout);
        b2 = (Button) findViewById(R.id.record);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewResults.class));
            }
        });


        btprofile = (Button) findViewById(R.id.btprofile);

        btprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });

        btcomplaint = (Button) findViewById(R.id.btcomplaint);

        btcomplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), SendComplaint.class));
            }
        });

        b5=findViewById(R.id.button);

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), View_test.class));
            }
        });

    }
}