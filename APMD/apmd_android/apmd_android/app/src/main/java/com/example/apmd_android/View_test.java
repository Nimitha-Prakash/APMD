package com.example.apmd_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class View_test extends AppCompatActivity implements JsonResponse{

    ListView l1;
    String [] test_id,test,value;
    public static String tid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);
        l1=findViewById(R.id.list);


        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) View_test.this;
        String q = "/user_view_test" ;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    public void response(JSONObject jo) {
        try{
            String status=jo.getString("status");
            if(status.equalsIgnoreCase("success")) {
                JSONArray ja1 = jo.getJSONArray("data");
                test_id = new String[ja1.length()];
                test = new String[ja1.length()];
                value = new String[ja1.length()];


                for (int i = 0; i < ja1.length(); i++) {
                    test_id[i] = ja1.getJSONObject(i).getString("Test_id");
                    test[i] = ja1.getJSONObject(i).getString("Test_name");


                    value[i] = "Test Name :" + test[i];
                }
                ArrayAdapter<String> ar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
//                g1.setAdapter(new Cust_images(this,image,result,date));

                l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tid=test_id[position];
                        startActivity(new Intent(getApplicationContext(), View_questions.class));
                    }
                });


            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}