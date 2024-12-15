package com.example.dentistapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        // Layout bileşenlerini tanımla
        TextView tvMessage = findViewById(R.id.tvMessage);
        TextView tvDetails = findViewById(R.id.tvDetails);
        Button btnCancelAppointment = findViewById(R.id.btnCancelAppointment);

        // Intent ile gelen verileri al
        String patientID = getIntent().getStringExtra("PATIENT_ID");
        String doctorName = getIntent().getStringExtra("DOCTOR_NAME");
        String appointmentTime = getIntent().getStringExtra("SELECTED_TIME");
        String selectedDay = getIntent().getStringExtra("SELECTED_DAY"); // Seçilen günü al

        // Randevuyu kontrol ve eklemek için DAO başlat
        AppointmentDAO appointmentDAO = new AppointmentDAO(this);

        // Randevu kontrolü
        if (appointmentDAO.isAppointmentTaken(doctorName, selectedDay, appointmentTime)) {
            // Eğer randevu alınmışsa kullanıcıya mesaj göster ve giriş ekranına dön
            Toast.makeText(this, "Seçilen gün ve saat için bu doktorda randevu dolu!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ConfirmationActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            // Randevu müsaitse kaydet
            appointmentDAO.addAppointment(patientID, doctorName, selectedDay, appointmentTime);

            Toast.makeText(this, "Randevu başarıyla kaydedildi!", Toast.LENGTH_SHORT).show();

            // Mesaj ve detayları ayarla
            tvMessage.setText("Randevu kaydınız oluşturuldu !");
            String details = "Hasta Kimlik No: " + patientID + "\n"
                    + "Doktor: " + doctorName + "\n"
                    + "Randevu Saati: " + appointmentTime + "\n"
                    + "Seçilen Gün: " + selectedDay; // Seçilen günü ekle
            tvDetails.setText(details);
        }

        // "Randevuyu İptal Et" butonuna tıklama işlevi
        btnCancelAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Randevuyu iptal işlemi (örneğin veritabanından silebilirsin)
                Toast.makeText(ConfirmationActivity.this, "Randevu iptal edildi!", Toast.LENGTH_SHORT).show();

                // Giriş ekranına yönlendir
                Intent intent = new Intent(ConfirmationActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Önceki aktiviteleri temizle
                startActivity(intent);
                finish(); // Bu aktiviteyi kapat
            }
        });
    }
}
