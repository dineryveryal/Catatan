package com.example.catatanharian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toolbar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_STORAGE = 100;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Build.VERSION.SDK_INT>=23){
            if (periksaIzinPenyimpanan()){
                ambilListFilePadaFolder();
            }
        }else {
                ambilListFilePadaFolder();
        }
    }

    public boolean periksaIzinPenyimpanan() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_STORAGE);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE_STORAGE:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    ambilListFilePadaFolder();
                }
                break;
        }
    }

    private void ambilListFilePadaFolder(){
        String path= Environment.getExternalStorageDirectory().toString()+"/proyek1";
        File directory=new File(path);
        if(directory.exists()){
            File[] files=directory.listFiles();
            String[] filenames=new String[files.length];
            String[] dateCreated= new String[files.length];
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd mm yyyy");
            ArrayList<Map<String,String>> itemDataList=new ArrayList<>();
            for(int i=0;i<files.length;i++){
                    filenames[i]=files[i].getName();
                Date lastModDate=new Date(files[i].lastModified());
                dateCreated[i]=simpleDateFormat.format(lastModDate);
                Map<String,String> itemMap=new HashMap<>();
                itemMap.put("name",filenames[i]);
                itemMap.put("date",dateCreated[i]);
                itemDataList.add(itemMap);
            }
            SimpleAdapter simpleAdapter=new SimpleAdapter(this, itemDataList,
                    android.R.layout.simple_list_item_2,
                    new String[]{"name","data"}, new int[]{android.R.id.text2,
                    android.R.id.text2});
            listView.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
            }
        }
    }
