package com.example.dentistapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserDAO userDAO; // UserDAO referansı
    private AppointmentDAO appointmentDAO; // AppointmentDAO referansı

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DAO'ları başlat
        userDAO = new UserDAO(this);
        appointmentDAO = new AppointmentDAO(this);

        // Giriş ekranındaki alanlar ve buton
        EditText etKimlikNo = findViewById(R.id.etKimlikNo);
        EditText etSifre = findViewById(R.id.etSifre);
        Button btnGiris = findViewById(R.id.btnGiris);

        // Giriş butonuna tıklama olayı
        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kimlikNo = etKimlikNo.getText().toString().trim();
                String sifre = etSifre.getText().toString().trim();

                if (kimlikNo.isEmpty() || sifre.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Tüm alanları doldurun!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kullanıcı giriş kontrolü
                if (userDAO.checkUser(kimlikNo, sifre)) {
                    navigateToDaySelection(kimlikNo); // Geçiş yap
                } else {
                    userDAO.addUser(kimlikNo, sifre);
                    Toast.makeText(MainActivity.this, "Kullanıcı kaydedildi!", Toast.LENGTH_SHORT).show();
                    navigateToDaySelection(kimlikNo);
                }
            }
        });
    }

    /**
     * Gün Seçim Ekranı'na geçiş
     */
    private void navigateToDaySelection(String kimlikNo) {
        Intent intent = new Intent(MainActivity.this, DaySelectionActivity.class);
        intent.putExtra("PATIENT_ID", kimlikNo); // Kimlik numarasını aktar
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close(); // Veritabanı bağlantısını kapat
        appointmentDAO.close();
    }
}
