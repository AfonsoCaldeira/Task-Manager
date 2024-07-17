package com.example.taskmanageras2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
public class Dasboard extends AppCompatActivity {
    Button AddTask, ViewTask , Share , Logout ;
    TextView Name ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        Name = findViewById(R.id.namevalue) ;
        Name.setText(getIntent().getStringExtra("name"));
        AddTask = findViewById(R.id.addtask) ;
        AddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dasboard.this , MainActivity.class)
                        .putExtra("UUID" , getIntent().getStringExtra("UUID")));

            }
        });
        Logout = findViewById(R.id.logout) ;
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });
        ViewTask = findViewById(R.id.viewtask) ;
        ViewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dasboard.this , ViewTaskActivity.class)
                        .putExtra("UUID" , getIntent().getStringExtra("UUID")));

            }
        });



    }
    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout Confirmation")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
    showLogoutDialog();
    }
}
