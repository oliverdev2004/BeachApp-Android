package com.example.beachseatreservationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeachAdapter extends RecyclerView.Adapter<BeachAdapter.BeachViewHolder> {
    private List<Beach> beachList;
    private Context context;
    private EditText etUserName, etDate, etSeats;
    private Spinner spinnerSeatType;
    private OnReservationListener listener;



    public interface OnReservationListener {
        void onReservationSuccess();
    }
    public BeachAdapter(Context context, List<Beach> beachList, EditText etUserName, EditText etDate, EditText etSeats, Spinner spinnerSeatType, OnReservationListener listener) {
        this.context = context;
        this.beachList = beachList;
        this.etUserName = etUserName;
        this.etDate = etDate;
        this.etSeats = etSeats;
        this.spinnerSeatType = spinnerSeatType;
        this.listener = listener;
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
        holder.tvSeats.setText("Available Seats: " + beach.getSeats());
        holder.btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUserName.getText().toString().trim();
                String date = etDate.getText().toString().trim();
                String seats = etSeats.getText().toString().trim();
                String seatType = spinnerSeatType.getSelectedItem().toString();

/*Beach beach = new Beach(
    obj.getInt("id"),
    obj.getString("name"),
    obj.getString("location"),
    obj.getString("description"),
    obj.getInt("seats") // ← this comes from database
);*/

                if (userName.isEmpty() || date.isEmpty() || seats.isEmpty()) {
                    Toast.makeText(context, "Please fill all fields first!", Toast.LENGTH_SHORT).show();
                } else if (beach.getSeats() == 0) {
                    Toast.makeText(context, "This beach is fully booked! 🚫", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(seats) > beach.getSeats()) {
                    Toast.makeText(context, "Only " + beach.getSeats() + " seats available!", Toast.LENGTH_SHORT).show();
                } else {
                    addReservation(beach, userName, date, seats, seatType);
                }
            }
        });
    }
    private void addReservation(Beach beach, String userName, String date, String seats, String seatType){
        String url = "http://192.168.1.121/add_reservation.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.trim().equals("-1")) {
                            Toast.makeText(context, "Reservation successful! 🎉", Toast.LENGTH_SHORT).show();
                            listener.onReservationSuccess(); // ADD THIS
                        } else {
                            Toast.makeText(context, "Reservation failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String message = error.getMessage() != null ? error.getMessage() : "Connection failed";
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("beach_id", String.valueOf(beach.getId()));
                params.put("beach_name", beach.getName());
                params.put("user_name", userName);
                params.put("reservation_date", date);
                params.put("seats", seats);
                params.put("seat_type", seatType);
                return params;
            }
        };

        Volley.newRequestQueue(context).add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return beachList.size();
    }



    public static class BeachViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvLocation;
        TextView tvDescription;
        TextView tvSeats;
        Button btnReserve;

        public BeachViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            btnReserve = itemView.findViewById(R.id.btnReserve);
            tvSeats = itemView.findViewById(R.id.tvSeats);
        }
    }
}