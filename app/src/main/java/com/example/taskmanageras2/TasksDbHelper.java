package com.example.taskmanageras2;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TasksDbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "tasks_db";
    public static final int DATABASE_VERSION = 1;






    // Table Names




    // Table name and column names
    public static final String TABLE_TASKS = "tasks";
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_UUID = "uuid";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_CREATION_DATE = "creation_date";
    public static final String KEY_DUE_DATE = "due_date";
    public static final String KEY_PRIORITY = "priority";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_TIME = "time";
    public static final String KEY_COMPLETED = "completed";

    public TasksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_TITLE + " TEXT," +
                KEY_UUID + " TEXT," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_CREATION_DATE + " TEXT," +
                KEY_DUE_DATE + " TEXT," +
                KEY_PRIORITY + " TEXT," +
                KEY_CATEGORY + " TEXT," +
                KEY_TIME + " TEXT," +
                KEY_COMPLETED + " INTEGER" +
                ")";

        db.execSQL(CREATE_TASKS_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists and create a new one


        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    // Method to insert a task into the database
    public long insertTask(String title, String uuid, String description, String creationDate, String dueDate, String priority, String category, String time, boolean completed) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_UUID, uuid);
        values.put(KEY_DESCRIPTION, description);
        values.put(KEY_CREATION_DATE, creationDate);
        values.put(KEY_DUE_DATE, dueDate);
        values.put(KEY_PRIORITY, priority);
        values.put(KEY_CATEGORY, category);
        values.put(KEY_TIME, time);
        values.put(KEY_COMPLETED, completed ? 1 : 0);

        long id = db.insert(TABLE_TASKS, null, values);

        db.close();
        return id;
    }


    // Method to retrieve all tasks from the database
    @SuppressLint("Range")
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                task.setUuid(cursor.getString(cursor.getColumnIndex(KEY_UUID)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                task.setCreationDate(cursor.getString(cursor.getColumnIndex(KEY_CREATION_DATE)));
                task.setDueDate(cursor.getString(cursor.getColumnIndex(KEY_DUE_DATE)));
                task.setPriority(cursor.getString(cursor.getColumnIndex(KEY_PRIORITY)));
                task.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));
                task.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
                task.setCompleted(cursor.getInt(cursor.getColumnIndex(KEY_COMPLETED)) == 1);

                taskList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return taskList;
    }

    // Method to update a task in the database
    public int updateTask(Context context , Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, task.getTitle());
        values.put(KEY_UUID, task.getUuid());
        values.put(KEY_DESCRIPTION, task.getDescription());
        values.put(KEY_CREATION_DATE, task.getCreationDate());
        values.put(KEY_DUE_DATE, task.getDueDate());
        values.put(KEY_PRIORITY, task.getPriority());
        values.put(KEY_CATEGORY, task.getCategory());
        values.put(KEY_TIME, task.getTime());
        values.put(KEY_COMPLETED, task.isCompleted() ? 1 : 0);

        Toast.makeText(context, task.getId()+"", Toast.LENGTH_SHORT).show();
        // Update row
        int rowsAffected = db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getId())});

        db.close();
        return rowsAffected;
    }

    // Method to delete a task from the database
    public void deleteTask(long taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?", new String[]{String.valueOf(taskId)});
        db.close();
    }
    public void updateTaskStatus(long taskId, boolean completed) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("completed", completed ? 1 : 0);

        db.update("tasks", values, "id=?", new String[] { String.valueOf(taskId) });
        db.close();
    }


}
