package com.example.dentistapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AppointmentTimeAdapter extends RecyclerView.Adapter<AppointmentTimeAdapter.TimeViewHolder> {

    private final List<String> timeList;
    private final String patientID;  // Hasta kimlik numarası
    private final String doctorName; // Doktor adı
    private final String selectedDay; // Seçilen gün bilgisi

    // Constructor: Hasta kimlik numarası, doktor adı ve gün bilgisi
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

        // Randevu saatine tıklama olayı
        holder.tvTime.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ConfirmationActivity.class);

            // Hasta kimlik numarası, doktor adı, randevu saati ve seçilen gün bilgisini gönder
            intent.putExtra("PATIENT_ID", patientID);
            intent.putExtra("DOCTOR_NAME", doctorName);
            intent.putExtra("SELECTED_TIME", time);
            intent.putExtra("SELECTED_DAY", selectedDay); // Seçilen gün eklendi

            // Aktiviteyi başlat
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }

    static class TimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;

        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
