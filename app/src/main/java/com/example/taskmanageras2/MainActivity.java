package com.example.taskmanageras2;import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextUUID, editTextDescription,  editTextTime;
    private EditText editTextCreationDate, editTextDueDate;
    private CheckBox checkBoxCompleted;
    private Button buttonInsert;
    private Spinner editTextPriority;
    EditText editTextCategory;
    private TasksDbHelper dbHelper;

    private Calendar calendar;
    private SimpleDateFormat dateFormat; // For date
    private SimpleDateFormat timeFormat; // For time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextUUID = findViewById(R.id.editTextUUID);
        editTextUUID.setVisibility(View.GONE);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextCreationDate = findViewById(R.id.editTextCreationDate);
        editTextDueDate = findViewById(R.id.editTextDueDate);
        editTextPriority = findViewById(R.id.editTextPriority);
        editTextCategory = findViewById(R.id.editTextCategory);
        editTextTime = findViewById(R.id.editTextTime);
        checkBoxCompleted = findViewById(R.id.checkBoxCompleted);
        buttonInsert = findViewById(R.id.buttonInsert);

        // Initialize TasksDbHelper
        dbHelper = new TasksDbHelper(this);

        // Initialize calendar instance
        calendar = Calendar.getInstance();

        // Initialize date format
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Initialize time format
        timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        // Set creation date to current date
        editTextCreationDate.setText(getCurrentDate());

        // Set onClickListener for Insert button
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertTask();
            }
        });

        // Set onClickListener for editTextDueDate to show DatePickerDialog
        editTextDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Set onClickListener for editTextTime to show TimePickerDialog
        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
    }

    // Method to get current date in "yyyy-MM-dd" format
    private String getCurrentDate() {
        return dateFormat.format(calendar.getTime());
    }

    // Method to show DatePickerDialog for editTextDueDate
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update editTextDueDate with selected date
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        editTextDueDate.setText(dateFormat.format(calendar.getTime()));
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    // Method to show TimePickerDialog for editTextTime
    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Update editTextTime with selected time
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        editTextTime.setText(timeFormat.format(calendar.getTime()));
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true); // 24 hour time format
        timePickerDialog.show();
    }

    // Method to insert task into database
    private void insertTask() {
        String title = editTextTitle.getText().toString().trim();
        String uuid = getIntent().getStringExtra("UUID");
        String description = editTextDescription.getText().toString().trim();
        String creationDate = editTextCreationDate.getText().toString().trim();
        String dueDate = editTextDueDate.getText().toString().trim();
        String priority = editTextPriority.getSelectedItem().toString().trim();
        String category = editTextCategory.getText().toString().trim();
        String time = editTextTime.getText().toString().trim();
        boolean completed = checkBoxCompleted.isChecked();

        long id = dbHelper.insertTask(title, uuid, description, creationDate, dueDate, priority, category, time, completed);

        if (id == -1) {
            Toast.makeText(MainActivity.this, "Failed to insert task", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Task inserted successfully with ID: " + id, Toast.LENGTH_SHORT).show();
            editTextTitle.getText().clear();
            editTextUUID.getText().clear();
            editTextDescription.getText().clear();
            editTextTime.getText().clear();
            checkBoxCompleted.setChecked(false);
        }
    }
}
