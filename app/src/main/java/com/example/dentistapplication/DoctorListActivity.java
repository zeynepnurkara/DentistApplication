package com.example.dentistapplication;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoctorListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        // Seçilen günü al (DaySelectionActivity'den gelen veri)
        String selectedDay = getIntent().getStringExtra("SELECTED_DAY");

        // Hasta kimlik numarasını al (DaySelectionActivity'den gelen veri)
        String patientID = getIntent().getStringExtra("PATIENT_ID"); // Intent ile gelen hasta kimlik numarasını al

        RecyclerView recyclerView = findViewById(R.id.recyclerViewDoctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Doktor verileri
        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(new Doctor("Dr. Mervenur Sertkaya", Arrays.asList("09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00")));
        doctorList.add(new Doctor("Dr. Eslem Nisa Batur", Arrays.asList("09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00")));

        // Hasta kimlik numarası ve seçilen gün ile DoctorAdapter'i oluştur
        DoctorAdapter adapter = new DoctorAdapter(doctorList, patientID, selectedDay);
        recyclerView.setAdapter(adapter);

        // Seçilen günü bir Toast ile göster (test amaçlı)
        if (selectedDay != null) {
            Toast.makeText(this, "Seçilen Gün: " + selectedDay, Toast.LENGTH_SHORT).show();
        }

        // Hasta kimlik numarasını kontrol et ve log veya toast ile göster
        if (patientID != null) {
            Toast.makeText(this, "Hasta Kimlik No: " + patientID, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Hasta Kimlik No alınamadı!", Toast.LENGTH_SHORT).show();
        }
    }
}
