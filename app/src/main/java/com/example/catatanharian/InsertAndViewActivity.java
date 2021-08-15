package com.example.catatanharian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class InsertAndViewActivity extends AppCompatActivity {
    private EditText editFileName, editContent;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_and_view);
        editFileName = findViewById(R.id.editFilename);
        editContent = findViewById(R.id.editContent);
        btnSimpan = findViewById(R.id.buttonSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buat();

            }
        });
    }

    private void Buat() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return;
        }
        String path = Environment.getExternalStorageDirectory().toString() + "/proyek1";
        File parent = new File(path);
        if (parent.exists()) {
            File file = new File(path, editFileName.getText().toString());
            FileOutputStream outputStream=null;

            try {
                file.createNewFile();
                outputStream=new FileOutputStream(file);
                OutputStreamWriter streamWriter=new OutputStreamWriter(outputStream);
                streamWriter.append(editContent.getText().toString());
                streamWriter.flush();
                streamWriter.close();
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            parent.mkdir();
            File file = new File(path, editFileName.getText().toString());
            FileOutputStream outputStream=null;
            try {
                file.createNewFile();
                outputStream=new FileOutputStream(file, false);
                outputStream.write(editContent.getText().toString().getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}