package com.example.dentistapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class DaySelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_selection);

        // Hasta kimlik numarasını al
        String patientID = getIntent().getStringExtra("PATIENT_ID");

        RecyclerView recyclerView = findViewById(R.id.recyclerViewDays);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Günlerin listesi
        List<String> days = Arrays.asList("Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma");

        // Gün Adapter'ını ayarla
        DayAdapter adapter = new DayAdapter(days, patientID);
        recyclerView.setAdapter(adapter);
    }

    // Gün Adapter sınıfı
    private class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {
        private final List<String> dayList;
        private final String patientID; // Hasta kimlik numarası

        public DayAdapter(List<String> dayList, String patientID) {
            this.dayList = dayList;
            this.patientID = patientID;
        }

        @NonNull
        @Override
        public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
            return new DayViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
            String day = dayList.get(position);
            holder.tvDay.setText(day);

            // Gün seçildiğinde DoctorListActivity'e yönlendirme işlemi
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(DaySelectionActivity.this, DoctorListActivity.class);
                intent.putExtra("SELECTED_DAY", day); // Seçilen günü aktar
                intent.putExtra("PATIENT_ID", patientID); // Hasta kimlik numarasını aktar
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return dayList.size();
        }

        class DayViewHolder extends RecyclerView.ViewHolder {
            TextView tvDay;

            public DayViewHolder(@NonNull View itemView) {
                super(itemView);
                tvDay = itemView.findViewById(R.id.tvDay);
            }
        }
    }
}
