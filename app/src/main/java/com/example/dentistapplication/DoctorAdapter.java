package com.example.dentistapplication;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    private final List<Doctor> doctorList;
    private final String patientID; // Hasta kimlik numarası
    private final String selectedDay; // Seçilen gün bilgisi

    // Constructor: Hasta kimlik numarası ve seçilen gün bilgisi eklendi
    public DoctorAdapter(List<Doctor> doctorList, String patientID, String selectedDay) {
        this.doctorList = doctorList;
        this.patientID = patientID;
        this.selectedDay = selectedDay;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.tvDoctorName.setText(doctor.getName());

        // Doktor adına tıklama olayı
        holder.tvDoctorName.setOnClickListener(v ->
                Toast.makeText(holder.itemView.getContext(), doctor.getName() + " seçildi!", Toast.LENGTH_SHORT).show()
        );

        // Randevu saatleri için alt RecyclerView Adapter'ını ayarla
        AppointmentTimeAdapter timeAdapter = new AppointmentTimeAdapter(doctor.getTimes(), patientID, doctor.getName(), selectedDay);
        holder.recyclerViewTimes.setAdapter(timeAdapter);
        holder.recyclerViewTimes.setLayoutManager(new LinearLayoutManager(holder.recyclerViewTimes.getContext()));
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    // ViewHolder sınıfı
    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView tvDoctorName;
        RecyclerView recyclerViewTimes;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDoctorName = itemView.findViewById(R.id.tvDoctorName);
            recyclerViewTimes = itemView.findViewById(R.id.recyclerViewTimes);
        }
    }

    // AppointmentTimeAdapter: Saat kontrol ve seçim işlemleri
    public static class AppointmentTimeAdapter extends RecyclerView.Adapter<AppointmentTimeAdapter.TimeViewHolder> {
        private final List<String> timeList;
        private final String patientID;
        private final String doctorName;
        private final String selectedDay;

        public AppointmentTimeAdapter(List<String> timeList, String patientID, String doctorName, String selectedDay) {
            this.timeList = timeList;
            this.patientID = patientID;
            this.doctorName = doctorName;
            this.selectedDay = selectedDay;
        }

        @NonNull
        @Override
        public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, parent, false);
            return new TimeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
            String time = timeList.get(position);
            holder.tvTime.setText(time);

            // Doktorun bu saatte randevusu var mı kontrol et
            boolean isTaken = new AppointmentDAO(holder.itemView.getContext()).isAppointmentTaken(doctorName, selectedDay, time);

            if (isTaken) {
                // Eğer saat doluysa, kullanıcıya göster ama tıklanamaz yap
                holder.itemView.setEnabled(false);
                holder.tvTime.setText(time + " (Dolu)");
                holder.tvTime.setTextColor(Color.RED); // Dolu saatleri kırmızı renkle göster
            } else {
                holder.itemView.setEnabled(true);
                holder.tvTime.setText(time + " (Boş)");
                holder.tvTime.setTextColor(Color.GREEN); // Boş saatleri yeşil renkle göster
            }

            // Saat seçimi tıklama olayı
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), ConfirmationActivity.class);
                intent.putExtra("SELECTED_TIME", time);
                intent.putExtra("SELECTED_DAY", selectedDay);
                intent.putExtra("DOCTOR_NAME", doctorName);
                intent.putExtra("PATIENT_ID", patientID);
                holder.itemView.getContext().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return timeList.size();
        }

        // ViewHolder sınıfı
        public static class TimeViewHolder extends RecyclerView.ViewHolder {
            TextView tvTime;

            public TimeViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTime = itemView.findViewById(R.id.tvTime);
            }
        }
    }
}
