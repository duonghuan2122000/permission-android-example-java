package com.soradbh.permissionandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    public static final int RC_CAMERA = 1;
    public static final String[] permissions = new String[]{
            Manifest.permission.CAMERA
    };

    private Button button;
    private View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        layout = findViewById(R.id.main_layout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSomething();
            }
        });
    }

    //TODO: action when click button.
    private void doSomething(){
        checkPerms();
    }

    //TODO: check permission and allow permission
    private void checkPerms(){

        if(ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED){
            // Permission hasn't been granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CAMERA
            )){
                // User dont't want to show the dialog permission
                // Open settings.
                Snackbar.make(layout, "Need Permission Camera",
                        Snackbar.LENGTH_LONG)
                        .setAction("Open Setting", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestPerms();
                            }
                        })
                        .show();

            } else {
                requestPerms();
            }


        } else {
            // Permission has already been granted
        }
    }

    //TODO: request permission.
    private void requestPerms(){
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.CAMERA
                },
                RC_CAMERA
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == RC_CAMERA &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Quyền camera đã được cho phép", Toast.LENGTH_SHORT).show();
        } else {
            Snackbar.make(layout, "Need Permission Camera. Settings?",
                    Snackbar.LENGTH_LONG)
                    .setAction("Open Setting", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openSetting();
                        }
                    })
                    .show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //TODO: open setting
    private void openSetting(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, RC_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_CAMERA && resultCode == RESULT_OK){
            Toast.makeText(this, "Permission camera", Toast.LENGTH_SHORT).show();
        }
    }
}
