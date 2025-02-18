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

public class View_questions extends AppCompatActivity implements JsonResponse{



    ListView l1;
    String [] qn_id,qn,value;
    public static String qid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_questions);
        l1=findViewById(R.id.list);


        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) View_questions.this;
        String q = "/user_view_question?id="+View_test.tid ;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    public void response(JSONObject jo) {
        try{
            String status=jo.getString("status");
            if(status.equalsIgnoreCase("success")) {
                JSONArray ja1 = jo.getJSONArray("data");
                qn_id = new String[ja1.length()];
                qn = new String[ja1.length()];
                value = new String[ja1.length()];


                for (int i = 0; i < ja1.length(); i++) {
                    qn_id[i] = ja1.getJSONObject(i).getString("Question_id");
                    qn[i] = ja1.getJSONObject(i).getString("Question");


                    value[i] = "Question :" + qn[i];
                }
                ArrayAdapter<String> ar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
//                g1.setAdapter(new Cust_images(this,image,result,date));

                l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        qid=qn_id[position];
                        startActivity(new Intent(getApplicationContext(), textspeech.class));
                    }
                });


            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}