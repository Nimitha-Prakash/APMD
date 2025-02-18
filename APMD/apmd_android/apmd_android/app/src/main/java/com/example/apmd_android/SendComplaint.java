package com.example.apmd_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class SendComplaint extends AppCompatActivity implements JsonResponse {
    Button b1;
    EditText e1,e2;
    ListView l1;
    String[] complaints,reply,date,complaint_ids, value;
    String[] desc;
    String complaint,des;
    public static String complaint_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_complaint);
        e1=(EditText)findViewById(R.id.etcomplaint);
        e2=(EditText)findViewById(R.id.desc);


        l1=(ListView)findViewById(R.id.lvcomplaintlist);
        b1=(Button)findViewById(R.id.btsendcomplaint);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complaint=e1.getText().toString();
                des=e2.getText().toString();


                if(complaint.equalsIgnoreCase(""))
                {
                    e1.setError("No value for Complaint");
                    e1.setFocusable(true);
                }
                else{
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) SendComplaint.this;
                    String q = "/usersendcomplaint?loginid="+ Login.logid+"&complaint="+complaint+"&des="+des;
                    q=q.replace(" ","%20");
                    JR.execute(q);
                }

            }
        });
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) SendComplaint.this;
        String q = "/userviewcomplaints?loginid="+ Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try{
            String method=jo.getString("method");
            if(method.equalsIgnoreCase("usersendcomplaint")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                //Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    Toast.makeText(getApplicationContext(), " SENT", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), SendComplaint.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Something went wrong!Try Again.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Homepage.class));
                }
            }
            if(method.equalsIgnoreCase("userviewcomplaint")){
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    //feedback_id=new String[ja1.length()];
                    complaints=new String[ja1.length()];
                    date=new String[ja1.length()];
                    reply=new String[ja1.length()];
                    desc=new String[ja1.length()];
                    value=new String[ja1.length()];


                    for(int i = 0;i<ja1.length();i++)
                    {
                        //feedback_id[i]=ja1.getJSONObject(i).getString("feedback_id");
                        complaints[i]=ja1.getJSONObject(i).getString("Title");
                        date[i]=ja1.getJSONObject(i).getString("Date");
                        reply[i]=ja1.getJSONObject(i).getString("Reply");
                        desc[i]=ja1.getJSONObject(i).getString("Description");

                        value[i]="Complaint:  "+complaints[i]+"\nDescription:  "+desc[i]+"\nDate:  "+date[i]+"\nReply:  "+reply[i];



                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,value);
                    l1.setAdapter(ar);
                    //startActivity(new Intent(getApplicationContext(),User_Post_Disease.class));
                }

                else

                {
                    Toast.makeText(getApplicationContext(), "No Complaints!!", Toast.LENGTH_LONG).show();

                }
            }

        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }


    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Homepage.class);
        startActivity(b);
    }
}