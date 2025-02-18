package com.example.apmd_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;


public class Update_profile extends AppCompatActivity implements JsonResponse {
    EditText e1,e2,e4,e5,e6;

    Button b1;
    SharedPreferences sh;

    public static String fname,lname,place,phone,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        e1=(EditText) findViewById(R.id.fname);
        e2=(EditText) findViewById(R.id.lname);
//        e4=(EditText) findViewById(R.id.place);
        e5=(EditText) findViewById(R.id.phone);
        e6=(EditText) findViewById(R.id.email);
        b1=(Button) findViewById(R.id.regbutton);
        e1.setText(Profile.fname);
        e2.setText(Profile.lname);
//        e4.setText(Profile.place);
        e5.setText(Profile.phone);
        e6.setText(Profile.email);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname=e1.getText().toString();
                lname=e2.getText().toString();
//                place=e4.getText().toString();
                phone=e5.getText().toString();
                email=e6.getText().toString();


                if (fname.equalsIgnoreCase("")) {
                    e1.setError("Enter Your Firstname");
                    e1.setFocusable(true);
                } else if (lname.equalsIgnoreCase("")) {
                    e2.setError("Enter Your lastname");
                    e2.setFocusable(true);

                }else if (phone.equalsIgnoreCase("") || phone.length()!=10) {
                    e5.setError("Enter Your Phone No");
                    e5.setFocusable(true);
                }else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")) {
                    e6.setError("Enter Your Email");
                    e6.setFocusable(true);
                }else {

                    com.example.apmd_android.JsonReq JR = new com.example.apmd_android.JsonReq();
                    JR.json_response = (JsonResponse) Update_profile.this;
                    String q = "/profile_update?fname=" + fname + "&lname=" + lname + "&phone=" + phone + "&email=" + email+ "&log_id=" + sh.getString("logid", "") ;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }



            }
        });


    }

    @Override
    public void response(JSONObject jo) {

        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {

                Toast.makeText(getApplicationContext(),"updation Succesfull",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), com.example.apmd_android.Homepage.class));



            }
        }
        catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Homepage.class));
    }
}
