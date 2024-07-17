package com.example.taskmanageras2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    private long taskId;
    private EditText titleEditText, descriptionEditText, dueDateEditText;
    private Spinner prioritySpinner;
    EditText categorySpinner;
    private Button saveButton;
    private Task task;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        // Initialize views
        titleEditText = findViewById(R.id.editTextEmail);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dueDateEditText = findViewById(R.id.dueDateEditText);
        prioritySpinner = findViewById(R.id.editTextPriority);
        categorySpinner = findViewById(R.id.editTextCategory);
        saveButton = findViewById(R.id.saveButton);

        // Get task data from intent
        taskId = getIntent().getLongExtra("id", -1);
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String dueDate = getIntent().getStringExtra("dueDate");
        String priority = getIntent().getStringExtra("priority");
        String category = getIntent().getStringExtra("category");

        // Initialize the task object
        task = new Task(title, "", description, "", dueDate, priority, category, "", false);

        // Set initial values to EditTexts and Spinners
        titleEditText.setText(title);
        descriptionEditText.setText(description);
        dueDateEditText.setText(dueDate);

        // Set selection in Spinners
        setSpinnerSelection(prioritySpinner, priority);

        categorySpinner.setText(getIntent().getStringExtra("category"));

        // Save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTask();
            }
        });
    }

    private void updateTask() {
        task.setId(taskId);
        // Update task object with new values from EditTexts and Spinners

        task.setUuid(getIntent().getStringExtra("uuid "));
        task.setId(taskId);
        task.setTitle(titleEditText.getText().toString());
        task.setDescription(descriptionEditText.getText().toString());
        task.setDueDate(dueDateEditText.getText().toString());
        task.setPriority(prioritySpinner.getSelectedItem().toString());
        task.setCategory(categorySpinner.getText().toString());
        TasksDbHelper dbHelper = new TasksDbHelper(this);
        dbHelper.updateTask(UpdateActivity.this,task);
        finish();
    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }
}
