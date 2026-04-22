package com.example.beachseatreservationapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.beachseatreservationapp.databinding.ActivityReservationBinding;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Reservation extends AppCompatActivity {

    private ActivityReservationBinding binding;
    RecyclerView recyclerView;
    ReservationAdapter reservationAdapter;
    List<ReservationModel> reservationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#73A5CA"));
        binding = ActivityReservationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("☀\uFE0F");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        recyclerView = findViewById(R.id.reservationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchReservations();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Reservation.this, "Your Reservations \uD83D\uDCCB", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchReservations() {
        String url = "http://192.168.1.121/get_reservation.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            reservationList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                ReservationModel reservation = new ReservationModel(
                                        obj.getInt("id"),
                                        obj.getInt("beach_id"),
                                        obj.getString("beach_name"),
                                        obj.getString("user_name"),
                                        obj.getString("reservation_date"),
                                        obj.getInt("seats"),
                                        obj.getString("seat_type")

                                );
                                reservationList.add(reservation);
                            }
                            reservationAdapter = new ReservationAdapter(Reservation.this, reservationList);
                            recyclerView.setAdapter(reservationAdapter);
                        } catch (Exception e) {
                            Toast.makeText(Reservation.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String message = error.getMessage() != null ? error.getMessage() : "Connection failed";
                        Toast.makeText(Reservation.this, message, Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(Reservation.this).add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.beachlist) {
            Intent i = new Intent(this, BeachActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.reservation) {
            Toast.makeText(this, "You are already on this page", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}