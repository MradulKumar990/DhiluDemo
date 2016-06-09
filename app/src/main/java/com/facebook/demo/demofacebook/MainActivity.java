package com.facebook.demo.demofacebook;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.MyRecyclerViewAdapter;
import model.UserInfo;

public class MainActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<UserInfo> userInfos;
    public static MainActivity mainActivity;

    public final int MY_PERMISSIONS_REQUEST_STORAGE_READ = 1;
    public final int MY_PERMISSIONS_REQUEST_STORAGE_WRITE = 2;

    public static final int STATUS = 0;
    public static final int PHOTO = 1;
    public static final int VIDEO = 2;


    private int mDatasetTypes[] = {STATUS,PHOTO,PHOTO,PHOTO,VIDEO}; //view types

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity =this;
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        Resources resources = getResources();
        String[] title = resources.getStringArray(R.array.array_title);
        String[] desc = resources.getStringArray(R.array.array_decs);
        int img[] = {R.drawable.share, R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.mysurepalace};
        userInfos = new ArrayList<UserInfo>();
        for(int i=0; i<title.length; i++){
            UserInfo info  = new UserInfo(title[i],desc[i], img[i]);
            userInfos.add(info);
        }

        setRuntimePermission();
        adapter = new MyRecyclerViewAdapter(userInfos, mainActivity,mDatasetTypes);
        recyclerView.setAdapter(adapter);
    }

    public void setRuntimePermission(){
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

        }else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mainActivity,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(mainActivity, "Required Storage access permission",
                        Toast.LENGTH_SHORT).show();
            }
                ActivityCompat.requestPermissions(mainActivity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE_READ);

        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
        }else  {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mainActivity,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(mainActivity, "Required Storage access permission",
                        Toast.LENGTH_SHORT).show();

            }
                ActivityCompat.requestPermissions(mainActivity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE_WRITE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE_READ: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(mainActivity, getResources().getString(R.string.permissionGranted), Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(mainActivity, getResources().getString(R.string.permissionNotGranted), Toast.LENGTH_LONG)
                            .show();
                }
                return;
             }
            case MY_PERMISSIONS_REQUEST_STORAGE_WRITE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(mainActivity, getResources().getString(R.string.permissionGranted), Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(mainActivity, getResources().getString(R.string.permissionNotGranted), Toast.LENGTH_LONG)
                            .show();
                }
                return;
            }
            /*default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);*/
        }
    }
}
