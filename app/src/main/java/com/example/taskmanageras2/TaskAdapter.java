package com.example.taskmanageras2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private Context context;

    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }
    public void updateTaskList(List<Task> newTaskList) {
        taskList = newTaskList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.titleTextView.setText(task.getTitle().toUpperCase() );
        // Bind other task as needed

        holder.PR.setText(task.getPriority().toUpperCase());
        holder.Desc.setText(task.getDescription());
        holder.DueDate.setText(task.getDueDate() + " " +task.getTime());
        holder.deleteImageView.setOnClickListener(v -> {
            // Handle delete action here
            // You might want to implement this
            // For example:


            TasksDbHelper dbHelper = new TasksDbHelper(context);
            dbHelper.deleteTask(task.getId());
             taskList.remove(position);
             notifyItemRemoved(position);

        });
        if(task.isCompleted() == true ){
            holder.STATUS.setText("Completed");

        }else{
            holder.STATUS.setText("Incomplete");

            holder.STATUS.setTextColor(Color.GREEN);

        }

        holder.CompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TasksDbHelper dbHelper = new TasksDbHelper(context);
                dbHelper.updateTaskStatus(task.getId(), true);
                holder.STATUS.setText("Completed");

            }
        });
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", task.getId());
            intent.putExtra("title", task.getTitle());
            intent.putExtra("uuid", task.getUuid());
            intent.putExtra("description", task.getDescription());
            intent.putExtra("creationDate", task.getCreationDate());
            intent.putExtra("dueDate", task.getDueDate());
            intent.putExtra("priority", task.getPriority());
            intent.putExtra("category", task.getCategory());
            intent.putExtra("time", task.getTime());
            intent.putExtra("completed", task.isCompleted());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView DueDate , Desc , PR , STATUS;
        ImageView deleteImageView;
        Button CompleteBtn ;

        public TaskViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            deleteImageView = itemView.findViewById(R.id.deleteImageView);
            DueDate = itemView.findViewById(R.id.dueDateTextView);
            Desc = itemView.findViewById(R.id.descriptionTextView);
            PR = itemView.findViewById(R.id.pr);
            STATUS = itemView.findViewById(R.id.status);
            CompleteBtn = itemView.findViewById(R.id.completebtn);
        }
    }
}

