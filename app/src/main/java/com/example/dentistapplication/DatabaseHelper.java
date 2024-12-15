package com.example.dentistapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Veritabanı adı ve versiyonu
    private static final String DATABASE_NAME = "DentistApp.db";
    private static final int DATABASE_VERSION = 1;

    // Randevu tablosu ve sütun adları
    public static final String TABLE_APPOINTMENTS = "appointments";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PATIENT_ID = "patient_id";
    public static final String COLUMN_DOCTOR_NAME = "doctor_name";
    public static final String COLUMN_DATE = "appointment_date";
    public static final String COLUMN_TIME = "appointment_time";

    // Kullanıcı tablosu ve sütun adları
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_PASSWORD = "password";

    // Randevu tablosu oluşturma sorgusu
    private static final String TABLE_CREATE_APPOINTMENTS =
            "CREATE TABLE " + TABLE_APPOINTMENTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PATIENT_ID + " TEXT NOT NULL, " +
                    COLUMN_DOCTOR_NAME + " TEXT NOT NULL, " +
                    COLUMN_DATE + " TEXT NOT NULL, " +
                    COLUMN_TIME + " TEXT NOT NULL);";

    // Kullanıcı tablosu oluşturma sorgusu
    private static final String TABLE_CREATE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " TEXT PRIMARY KEY, " + // Birincil anahtar: Kimlik No
                    COLUMN_USER_PASSWORD + " TEXT NOT NULL);";

    // Constructor: Veritabanını başlatır
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabloyu oluştur
        db.execSQL(TABLE_CREATE_APPOINTMENTS); // Randevu tablosunu oluştur
        db.execSQL(TABLE_CREATE_USERS); // Kullanıcı tablosunu oluştur
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eski tabloları sil ve yeniden oluştur
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
