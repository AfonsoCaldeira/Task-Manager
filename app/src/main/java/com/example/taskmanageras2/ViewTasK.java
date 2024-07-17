package com.example.taskmanageras2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewTasK extends AppCompatActivity {
    RecyclerView recyclerView;

    List<Task> taskList ;
    TasksDbHelper tasksDbHelper ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_task_activity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksDbHelper = new TasksDbHelper(ViewTasK.this);
        taskList = new ArrayList<>() ;
        taskList = tasksDbHelper.getAllTasks() ;
        TaskAdapter taskAdapter = new TaskAdapter(ViewTasK.this , taskList);
        recyclerView.setAdapter(taskAdapter);



    }
}
