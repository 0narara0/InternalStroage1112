package com.example.edu.internalstroage1112;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
//
//    if(permission != PackageManager.PERMISSION_GRANTED){
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},100);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode){
//            case 100:
//                if(grantResults.length>0)|| grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                Log.e("","Permission has been granted by user");
//            }
//        }
//    }

    private final String INTERNAL_FILENAME = "Interal";
    private final String EXTERNAL_FILENAME = "External";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonReadInner = findViewById(R.id.buttonReadInner);
        buttonReadInner.setOnClickListener(this);
        Button buttonWriteInner = findViewById(R.id.buttonWriteInner);
        buttonWriteInner.setOnClickListener(this);
        Button buttonReadOutter = findViewById(R.id.buttonReadOutter);
        buttonReadOutter.setOnClickListener(this);
        Button buttonWriteOutter = findViewById(R.id.buttonWriteOutter);
        buttonWriteOutter.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        EditText editTextInput = findViewById(R.id.editTextInput);
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
        switch (v.getId()){
            case R.id.buttonReadInner:
                fileInputStream = openFileInput(INTERNAL_FILENAME);
                byte[] buffer = new byte[fileInputStream.available()];
                fileInputStream.read(buffer);
                editTextInput.setText(new String(buffer));
                fileInputStream.close();
                break;

            case R.id.buttonWriteInner:
                fileOutputStream = openFileOutput(INTERNAL_FILENAME,Context.MODE_PRIVATE);
                fileOutputStream.write(editTextInput.getText().toString().getBytes());
                editTextInput.setText("");
                fileOutputStream.close();
                break;

            case R.id.buttonReadOutter:
                File file = new File(getExternalFilesDir(null),EXTERNAL_FILENAME);
                fileInputStream = new FileInputStream(file);
                byte[] buffer1 = new byte[fileInputStream.available()];
                fileInputStream.read(buffer1);
                editTextInput.setText(new String(buffer1));
                fileInputStream.close();
                break;

            case R.id.buttonWriteOutter:
                file = new File(getExternalFilesDir(null),EXTERNAL_FILENAME);
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(editTextInput.getText().toString().getBytes());
                editTextInput.setText("");
                fileOutputStream.close();
                break;
        }

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
