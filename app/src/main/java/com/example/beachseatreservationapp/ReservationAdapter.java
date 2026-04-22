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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {
    private List<ReservationModel> reservationList;
    private Context context;

    public ReservationAdapter(Context context, List<ReservationModel> reservationList) {
        this.context = context;
        this.reservationList = reservationList;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        ReservationModel reservation = reservationList.get(position);
        holder.tvBeachName.setText(reservation.getBeachName());
        holder.tvUserName.setText("Reserved by: " + reservation.getUserName());
        holder.tvDate.setText("Date: " + reservation.getReservationDate());
        holder.tvSeats.setText("Seats: " + reservation.getSeats());
        holder.seat_type.setText("Seat Type: " + reservation.getSeat_type());
        final int currentPosition = position; // ADD THIS

        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelReservation(reservation.getId(), currentPosition);
            }
        });
    }

    private void cancelReservation(int id, int position) {
        String url = "http://192.168.1.121/delete_reservation.php?id=" + id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("1")) {
                            Toast.makeText(context, "Reservation cancelled!", Toast.LENGTH_SHORT).show();
                            reservationList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, reservationList.size());
                        } else {
                            Toast.makeText(context, "Cancellation failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String message = error.getMessage() != null ? error.getMessage() : "Connection failed";
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(context).add(stringRequest);
    }

    @Override
    public int getItemCount() { return reservationList.size(); }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {
        TextView tvBeachName, tvUserName, tvDate, tvSeats,seat_type;
        Button btnCancel;

        public ReservationViewHolder(View itemView) {
            super(itemView);
            tvBeachName = itemView.findViewById(R.id.tvBeachName);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvSeats = itemView.findViewById(R.id.tvSeats);
            seat_type=itemView.findViewById(R.id.seat_type);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}