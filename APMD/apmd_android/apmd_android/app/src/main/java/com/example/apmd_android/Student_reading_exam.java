package com.example.apmd_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Student_reading_exam extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView lv1;
    String[] reding_id, reding_qn;
    public static String reding_ids;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_reading_exam);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv1 = findViewById(R.id.ll);
        lv1.setOnItemClickListener(this);

        JsonReq JR = new JsonReq();
        JR.json_response = Student_reading_exam.this;
        String q = "/Student_reading_exam";
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String method = jo.getString("method");
            if (method.equalsIgnoreCase("Student_reading_exam")) {
                String status = jo.getString("status");
                Log.d("pearl", status);
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = jo.getJSONArray("data");
                    reding_id = new String[ja1.length()];
                    reding_qn = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        reding_id[i] = ja1.getJSONObject(i).getString("handwriting_id");
                        reding_qn[i] = ja1.getJSONObject(i).getString("hand_question");
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, reding_qn);
                    lv1.setAdapter(adapter);
                } else {
                    Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedQuestion = reding_qn[position];
        reding_ids=reding_id[position];
        SharedPreferences.Editor e=sh.edit();
        e.putString("reding_ids", reding_ids);
        e.commit();

        Intent intent = new Intent(getApplicationContext(), textspeech.class);
        intent.putExtra("question", selectedQuestion);
        startActivity(intent);
    }
}
