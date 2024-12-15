package com.example.dentistapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    private final DatabaseHelper dbHelper;
    private final SQLiteDatabase database;

    private static final String TAG = "AppointmentDAO"; // Logcat etiketi

    public AppointmentDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Randevu ekleme fonksiyonu
     *
     * @param patientId   Hasta kimlik numarası
     * @param doctorName  Doktor adı
     * @param date        Randevu tarihi
     * @param time        Randevu saati
     */
    public void addAppointment(String patientId, String doctorName, String date, String time) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PATIENT_ID, patientId);
        values.put(DatabaseHelper.COLUMN_DOCTOR_NAME, doctorName);
        values.put(DatabaseHelper.COLUMN_DATE, date);
        values.put(DatabaseHelper.COLUMN_TIME, time);

        long result = database.insert(DatabaseHelper.TABLE_APPOINTMENTS, null, values);
        if (result != -1) {
            Log.d(TAG, "Randevu başarıyla kaydedildi: " + patientId + ", " + doctorName);
        } else {
            Log.e(TAG, "Randevu kaydedilemedi!");
        }
    }

    /**
     * Tüm randevuları getirme fonksiyonu
     *
     * @return Veritabanındaki tüm randevuların listesi
     */
    public List<String> getAllAppointments() {
        List<String> appointments = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_APPOINTMENTS, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String patientId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PATIENT_ID));
            String doctorName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DOCTOR_NAME));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIME));

            appointments.add("Hasta ID: " + patientId + ", Doktor: " + doctorName + ", Tarih: " + date + ", Saat: " + time);
        }
        cursor.close();
        return appointments;
    }

    /**
     * Belirli bir doktor, gün ve saat için mevcut randevuyu kontrol et.
     *
     * @param doctorName Doktorun adı
     * @param date       Randevu tarihi
     * @param time       Randevu saati
     * @return Eğer randevu mevcutsa true, değilse false
     */
    public boolean isAppointmentTaken(String doctorName, String date, String time) {
        String selection = DatabaseHelper.COLUMN_DOCTOR_NAME + " = ? AND " +
                DatabaseHelper.COLUMN_DATE + " = ? AND " +
                DatabaseHelper.COLUMN_TIME + " = ?";
        String[] selectionArgs = {doctorName, date, time};

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_APPOINTMENTS, // Tablo adı
                null, // Tüm sütunlar
                selection, // Şartlar
                selectionArgs, // Şartlara karşılık gelen değerler
                null, null, null
        );

        boolean isTaken = cursor.getCount() > 0; // Eğer kayıt varsa true
        cursor.close(); // Cursor'u kapat
        return isTaken;
    }

    /**
     * Veritabanı bağlantısını kapatma
     */
    public void close() {
        database.close();
    }
}
