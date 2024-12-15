package com.example.dentistapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDAO {

    private final DatabaseHelper dbHelper;
    private final SQLiteDatabase database;

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void addUser(String userId, String password) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_ID, userId);
        values.put(DatabaseHelper.COLUMN_USER_PASSWORD, password);

        long result = database.insert(DatabaseHelper.TABLE_USERS, null, values);
        if (result == -1) {
            System.err.println("Kullanıcı kaydedilemedi!");
        }
    }

    public boolean checkUser(String userId, String password) {
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_USERS,
                null,
                DatabaseHelper.COLUMN_USER_ID + " = ? AND " + DatabaseHelper.COLUMN_USER_PASSWORD + " = ?",
                new String[]{userId, password},
                null, null, null
        );

        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        return userExists;
    }

    public void close() {
        database.close();
    }
}
