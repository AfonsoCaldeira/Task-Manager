package com.example.taskmanageras2;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewTaskActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> taskList;
    private List<Task> OwnViewList;
    private TasksDbHelper tasksDbHelper;
    TaskAdapter taskAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_asc:
                Collections.sort(OwnViewList, TaskComparators.dueDateAscending);
                taskAdapter.updateTaskList(OwnViewList);
                return true;
            case R.id.action_sort_desc:
                Collections.sort(OwnViewList, TaskComparators.dueDateDescending);
                taskAdapter.updateTaskList(OwnViewList);
                return true;
            case R.id.action_sort_priority_asc:
                Collections.sort(OwnViewList, TaskComparators.priorityComparator);
                taskAdapter.notifyDataSetChanged();
                return true;
            case R.id.action_sort_priority_desc:
                Collections.sort(OwnViewList, Collections.reverseOrder(TaskComparators.priorityComparator));
                taskAdapter.notifyDataSetChanged();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_task_activity);





    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksDbHelper = new TasksDbHelper(ViewTaskActivity.this);
        taskList = new ArrayList<>() ;
        OwnViewList = new ArrayList<>() ;
        taskList = tasksDbHelper.getAllTasks() ;

        for(Task task : taskList){
            if(getIntent().getStringExtra("UUID").equals(task.getUuid())){
                OwnViewList.add(task) ;
            }
        }

        Log.d("TheSize" , taskList.size()+"");
        taskAdapter = new TaskAdapter(ViewTaskActivity.this , OwnViewList);
        recyclerView.setAdapter(taskAdapter);
    }
}
