package com.example.edu.internalstroage1112;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
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



    private final String INTERNAL_FILENAME = "Internal";
    private final String EXTERNAL_PRIVATE_FILENAME = "Private_External";
    private final String EXTERNAL_PUBLIC_FILENAME = "Public_External";
    private final int REQUEST_CODE = 10;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE:
//                //강사님 코드
//                if(grantResults.length>0||grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    Log.e("","Permission has been granted by user");

                   //반장쌤 코드
                    if(grantResults.length==2 &&
                            grantResults[0]==PackageManager.PERMISSION_GRANTED &&
                            grantResults[1]==PackageManager.PERMISSION_GRANTED);{
                    System.out.println("******* Permission has been granted by user");
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permission = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permission!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE);
        }


        Button buttonReadInner = findViewById(R.id.buttonReadInner);
        buttonReadInner.setOnClickListener(this);
        Button buttonWriteInner = findViewById(R.id.buttonWriteInner);
        buttonWriteInner.setOnClickListener(this);
        Button buttonReadPrivateOutter = findViewById(R.id.buttonReadPrivateOutter);
        buttonReadPrivateOutter.setOnClickListener(this);
        Button buttonWritePrivateOutter = findViewById(R.id.buttonWritePrivateOutter);
        buttonWritePrivateOutter.setOnClickListener(this);
        Button buttonReadPublicOutter = findViewById(R.id.buttonReadPublicOutter);
        buttonReadPublicOutter.setOnClickListener(this);
        Button buttonWritePublicOutter = findViewById(R.id.buttonWritePublicOutter);
        buttonWritePublicOutter.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        EditText editTextInput = findViewById(R.id.editTextInput);
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        File file, dir;
        byte[] buffer = null;

        try {
        switch (v.getId()){
            case R.id.buttonReadInner:
                fileInputStream = openFileInput(INTERNAL_FILENAME);
                buffer = new byte[fileInputStream.available()];
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

            case R.id.buttonReadPrivateOutter:
                file = new File(getExternalFilesDir(null),EXTERNAL_PRIVATE_FILENAME);
                fileInputStream = new FileInputStream(file);
                buffer = new byte[fileInputStream.available()];
                fileInputStream.read(buffer);
                editTextInput.setText(new String(buffer));
                fileInputStream.close();
                break;

            case R.id.buttonWritePrivateOutter:
                file = new File(getExternalFilesDir(null),EXTERNAL_PRIVATE_FILENAME);
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(editTextInput.getText().toString().getBytes());
                editTextInput.setText("");
                fileOutputStream.close();
                break;

            case R.id.buttonReadPublicOutter:
                dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                file = new File(dir,EXTERNAL_PUBLIC_FILENAME);

                fileInputStream = new FileInputStream(file);
                buffer = new byte[fileInputStream.available()];
                fileInputStream.read(buffer);
                editTextInput.setText(new String(buffer));
                fileInputStream.close();
                break;

            case R.id.buttonWritePublicOutter:

                String state = Environment.getExternalStorageState();
                System.out.println("*********************** state: "+ state);
                if(state.equals(Environment.MEDIA_MOUNTED)==false) return;

                dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                if(!dir.exists()) dir.mkdirs();
                System.out.println("********************** dir: "+dir);

                file = new File(dir,EXTERNAL_PUBLIC_FILENAME);
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
