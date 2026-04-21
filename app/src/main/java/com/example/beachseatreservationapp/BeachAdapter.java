package com.example.beachseatreservationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeachAdapter extends RecyclerView.Adapter<BeachAdapter.BeachViewHolder> {
    private List<Beach> beachList;
    private Context context;

    public BeachAdapter(List<Beach> beachList, Context context) {
        this.beachList = beachList;
        this.context = context;
    }

    @NonNull
    @Override
    public BeachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beach, parent, false);
        return new BeachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeachViewHolder holder, int position) {
        Beach beach = beachList.get(position);
        holder.tvName.setText(beach.getName());
        holder.tvLocation.setText(beach.getLocation());
        holder.tvDescription.setText(beach.getDescription());


        holder.btnReserve.setOnClickListener(v -> reserveBeach(beach));
    }

    @Override
    public int getItemCount() {
        return beachList.size();
    }

    private void reserveBeach(Beach beach) {
        String url = "http://192.168.1.109/add_reservation.php";  // ← replace with your IP

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(context, "Reserved " + beach.getName(), Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("beach_id", String.valueOf(beach.getId()));
                params.put("beach_name", beach.getName());
                params.put("user_name", "Oliver");
                params.put("reservation_date", "2026-05-15");
                return params;
            }
        };

        Volley.newRequestQueue(context).add(request);
    }

    public static class BeachViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvLocation;
        TextView tvDescription;
        Button btnReserve;

        public BeachViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            btnReserve = itemView.findViewById(R.id.btnReserve);
        }
    }
}