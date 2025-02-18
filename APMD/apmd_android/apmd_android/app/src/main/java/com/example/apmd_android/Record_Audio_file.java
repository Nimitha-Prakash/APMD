package com.example.apmd_android;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.util.Log;
import java.io.IOException;

public class Record_Audio_file extends AppCompatActivity {

    private MediaRecorder mediaRecorder;
    private String outputFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_audio_file);
        Button startRecordingButton = findViewById(R.id.btnStartRecording);
        startRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
            }
        });
    }

    private void startRecording() {
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audio.3gp";
        mediaRecorder = new MediaRecorder();

        // Error handling for setAudioSource
        try {
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(outputFile);

            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(Record_Audio_file.this, "Recording started", Toast.LENGTH_SHORT).show();
        } catch (IOException | IllegalStateException e) {
            Log.e("Recording", "Failed to start recording: " + e.getMessage());
            Toast.makeText(Record_Audio_file.this, "Failed to start recording", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopRecording();
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            Toast.makeText(Record_Audio_file.this, "Recording stopped", Toast.LENGTH_SHORT).show();
        }
    }
}