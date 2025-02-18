package com.example.apmd_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Profile extends AppCompatActivity implements JsonResponse {
    SharedPreferences sh;
    EditText t1;
    Button b1;
    ListView l1;
    String complaints;

    String [] uid,comp,rply,date,val,em;
    public static String fname,lname,place,phone,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1 =(ListView) findViewById(R.id.view);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Profile.this;
        String q = "/User_profile?log_id=" + sh.getString("logid", "");
        q = q.replace(" ", "%20");
        JR.execute(q);
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                complaints = t1.getText().toString();
//
//                JsonReq JR = new JsonReq();
//                JR.json_response = (JsonReq.JsonResponse) Complaints.this;
//                String q = "/User_Complaint?log_id=" + sh.getString("log_id", "") + "&complaints=" + complaints ;
//                q = q.replace(" ", "%20");
//                JR.execute(q);
//            }
//        });
    }
    @Override
    public void response(JSONObject jo) {
        try {



            String method = jo.getString("method");
            Log.d("result", method);
//                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();

            if (method.equalsIgnoreCase("Complaint")){
                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {


                    Toast.makeText(getApplicationContext(), "Feedback Send Successfull", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getApplicationContext(), Complaints.class));
                    //startService(new Intent(getApplicationContext(),Notiservise.class));
                }
//

            }
            else if (method.equalsIgnoreCase("User_feedbak")) {
                String status = jo.getString("status");
                Log.d("result", status);
//                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                if (status.equalsIgnoreCase("success")) {

                    JSONArray ja = (JSONArray) jo.getJSONArray("data");


                    uid = new String[ja.length()];
                    comp = new String[ja.length()];
//                    rply = new String[ja.length()];
                    date = new String[ja.length()];
                    em = new String[ja.length()];
                    val = new String[ja.length()];


                    for (int i = 0; i < ja.length(); i++) {

                        uid[i] = ja.getJSONObject(i).getString("fname");
                        comp[i] = ja.getJSONObject(i).getString("lname");
//                        rply[i] = ja.getJSONObject(i).getString("place");
                        date[i] = ja.getJSONObject(i).getString("phone");
                        em[i] = ja.getJSONObject(i).getString("email");
//                        Toast.makeText(this, "name"+em[i], Toast.LENGTH_SHORT).show();


                        val[i] = "Name  : " + uid[i]+"\nLastName : " +comp[i]+ "\nPhone  : " + date[i]+ "\nEmail : " + em[i]+"\n";
                    }
                    l1.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, val));
                    l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // Handle item click here
                            fname=uid[position];
                            lname=comp[position];
//                            place=rply[position];
                            phone=date[position];
                            email=em[position];


                            final CharSequence[] items = {"Update Profile", "Cancel"};
                            AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                            // builder.setTitle("Add Photo!");
                            builder.setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int item) {


                                    if (items[item].equals("Update Profile")) {

                                        startActivity(new Intent(getApplicationContext(), Update_profile.class));

                                    } else if (items[item].equals("Cancel")) {
                                        dialog.dismiss();
//                                    startActivity(new Intent(getApplicationContext(), View_tutorial_video.class));

                                    }

                                }

                            });
                            builder.show();



                        }
                    });

                }
            }





        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Homepage.class));
    }
}