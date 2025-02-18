package com.example.apmd_android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class textspeech extends AppCompatActivity implements  JsonResponse,SurfaceHolder.Callback {

    String [] answer,qid,value;

    ListView l1;

    private static final int REQUEST_PERMISSION_CODE = 100;
    private static final String TAG = "AudioRecorderModule";
    private static final String OUTPUT_FILE = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/recorded_audio.mp3";
    private static final String UPLOAD_URL = "http://" + Ipsettings.ip + "/audioo";

    private Button recordButton;
    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;
    private Handler handler = new Handler();
    SharedPreferences sh;
    public static String resultData;

    TextView t1,t2;

    private TextView tvResult;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textspeech);

        recordButton = findViewById(R.id.record_button);
        t2=(TextView) findViewById(R.id.textView7);
        tvResult = findViewById(R.id.tvResult);

        l1=findViewById(R.id.ans);


        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) textspeech.this;
        String q = "/user_view_anser?id="+View_questions.qid;
        q = q.replace(" ", "%20");
        JR.execute(q);

        sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecording) {
                    startRecording();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            stopRecording();
                            uploadAudio(OUTPUT_FILE);
                        }
                    }, 5000); // Stop recording after 5 seconds
                } else {
                    stopRecording();
                    handler.removeCallbacksAndMessages(null); // Cancel any scheduled stop
                }
            }
        });
    }

    private void startRecording() {
        if (!checkPermission()) {
            requestPermission();
            return;
        }

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); // Or use AMR_NB for a different format
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC); // Or use AMR_NB for a different format
        mediaRecorder.setOutputFile(OUTPUT_FILE);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            recordButton.setText("Stop");
        } catch (IOException e) {
            Log.e(TAG, "Error preparing MediaRecorder", e);
            releaseMediaRecorder();
        } catch (IllegalStateException e) {
            Log.e(TAG, "Error starting MediaRecorder", e);
            releaseMediaRecorder();
        }
    }

    private void stopRecording() {
        if (isRecording) {
            mediaRecorder.stop();
            releaseMediaRecorder();
            isRecording = false;
            recordButton.setText("Start");
        }
    }

    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private boolean checkPermission() {
        int audioPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        return audioPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
    }

    private void uploadAudio(String filePath) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("audio/mp3"); // Change to appropriate audio type if necessary
        String loginId = Login.logid;
        String questionId = View_questions.qid;
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("audio", "recorded_audio.mp3", RequestBody.create(mediaType, new File(filePath)))
                .addFormDataPart("question_id", questionId)
                .addFormDataPart("login_id", loginId)

                .build();
        Request request = new Request.Builder()
                .url(UPLOAD_URL)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Handle failure
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseData);

                        // Log the response data for debugging
                        Log.d(TAG, "Response data: " + responseData);

                        // Check if the JSON response contains the expected keys
                        if (jsonObject.has("status") && jsonObject.has("out") && jsonObject.has("text")) {
                            String status = jsonObject.getString("status");
                            String output = jsonObject.getString("out");

                            // Log the status and output for debugging
                            Log.d(TAG, "Status: " + status);
                            Log.d(TAG, "Output: " + output);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Update UI elements on the UI thread
//

                                    Toast.makeText(getApplicationContext(), "Predicted Out " + output, Toast.LENGTH_LONG).show();

                                    // Handle the response based on the status and output
                                    if (output.equalsIgnoreCase("Correct Answer")) {
                                        if (status.equalsIgnoreCase("success")) {
                                            try {
                                                String text = jsonObject.getString("text");

                                                tvResult.setText(text);
                                                t2.setText(output);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Toast.makeText(textspeech.this, "Error parsing 'text' from JSON response", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                    else if (output.equalsIgnoreCase("Incorrect Answer")) {
                                        if (status.equalsIgnoreCase("success")) {
                                            try {
                                                String text = jsonObject.getString("text");

                                                tvResult.setText(text);
                                                t2.setText(output);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Toast.makeText(textspeech.this, "Error parsing 'text' from JSON response", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            });
                        } else {
                            // Handle missing keys in the JSON response
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(textspeech.this, "Response data missing required keys", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        // Handle JSON parsing errors
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(textspeech.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    // Handle unsuccessful response
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(textspeech.this, "Upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }


        });

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        // Surface created, nothing to do here for now
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        // Surface changed, nothing to do here for now
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        // Surface destroyed, release MediaRecorder
        releaseMediaRecorder();
    }

    @Override
    public void response(JSONObject jo) {






        try{
            String status=jo.getString("status");
            String output=jo.getString("output");

//            Toast.makeText(this, "///////////", Toast.LENGTH_SHORT).show();

            if (output.equalsIgnoreCase("view")) {


                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = jo.getJSONArray("data");
                    answer = new String[ja1.length()];
                    qid = new String[ja1.length()];
                    value = new String[ja1.length()];


                    for (int i = 0; i < ja1.length(); i++) {
                        answer[i] = ja1.getJSONObject(i).getString("Answer");
                        qid[i] = ja1.getJSONObject(i).getString("Question_id");


                        value[i] = "Answer :" + answer[i];


                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    l1.setAdapter(ar);


//


                }
            }

            if (output.equalsIgnoreCase("Correct Answer")) {
                if(status.equalsIgnoreCase("success")) {
                    try {
                        String text = jo.getString("text");

                        tvResult.setText(text);
                        t2.setText(output);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(textspeech.this, "Error parsing 'text' from JSON response", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            else if (output.equalsIgnoreCase("Incorrect Answer")) {
                if (status.equalsIgnoreCase("success")) {
                    try {
                        String text = jo.getString("text");

                        tvResult.setText(text);
                        t2.setText(output);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(textspeech.this, "Error parsing 'text' from JSON response", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

}
}
