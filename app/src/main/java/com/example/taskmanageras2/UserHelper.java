package com.example.taskmanageras2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class UserHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Table name
    public static final String TABLE_USERS = "users";

    // Column names
    public static final String KEY_ID = "id";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    // Create table statement
    private static final String SQL_CREATE_USERS_TABLE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_FIRST_NAME + " TEXT," +
                    KEY_LAST_NAME + " TEXT," +
                    KEY_EMAIL + " TEXT UNIQUE," +
                    KEY_PASSWORD + " TEXT)";

    public UserHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement if you want to handle database schema upgrades
        // Example implementation:
        // db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        // onCreate(db);
    }

    // Insert a user into the database
    public long insertUser(String firstName, String lastName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, firstName);
        values.put(KEY_LAST_NAME, lastName);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PASSWORD, password);
        return db.insert(TABLE_USERS, null, values);
    }

    // Retrieve all users from the database
    @SuppressLint("Range")
    public List<Users> getAllUsers() {
        List<Users> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        if (cursor.moveToFirst()) {
            do {
                Users user = new Users();
                user.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                user.setFirstName(cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME)));
                user.setLastName(cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return userList;
    }

    // Retrieve a user by email
    @SuppressLint("Range")
    public Users getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID, KEY_FIRST_NAME, KEY_LAST_NAME, KEY_EMAIL, KEY_PASSWORD},
                KEY_EMAIL + "=?", new String[]{email}, null, null, null);

        Users user = null;
        if (cursor.moveToFirst()) {
            user = new Users();
            user.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
            user.setFirstName(cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME)));
            user.setLastName(cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
        }

        cursor.close();
        return user;
    }
}
