package com.example.beachseatreservationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.beachseatreservationapp.databinding.ActivityBeachesBinding;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class BeachActivity extends AppCompatActivity {

    private ActivityBeachesBinding binding;
    RecyclerView recyclerView;
    BeachAdapter beachAdapter;
    List<Beach> beachList = new ArrayList<>();
    EditText etUserName, etDate, etSeats;
    Spinner spinnerSeatType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBeachesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("☀\uFE0F");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        etUserName = findViewById(R.id.etUserName);
        etDate = findViewById(R.id.etDate);
        etSeats = findViewById(R.id.etSeats);
        spinnerSeatType = findViewById(R.id.spinnerSeatType);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(BeachActivity.this,Reservation.class);
                startActivity(i);
            }
        });

        fetchBeaches();
    }

    private void fetchBeaches() {
        binding.progressBar.setVisibility(View.VISIBLE);
        String url = "http://192.168.1.121/getAllbeach.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            beachList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                Beach beach = new Beach(
                                        obj.getInt("id"),
                                        obj.getString("name"),
                                        obj.getString("location"),
                                        obj.getString("description"),
                                        obj.getInt("seats")
                                );
                                beachList.add(beach);
                            }
                            beachAdapter = new BeachAdapter(BeachActivity.this, beachList, etUserName, etDate, etSeats, spinnerSeatType,
                                    new BeachAdapter.OnReservationListener() {
                                        @Override
                                        public void onReservationSuccess() {
                                            fetchBeaches();
                                        }
                                    });
                            recyclerView.setAdapter(beachAdapter);
                            binding.progressBar.setVisibility(View.INVISIBLE);
                        } catch (Exception e) {
                            Toast.makeText(BeachActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            binding.progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String message = error.getMessage() != null ? error.getMessage() : "Connection failed, check your server";
                        Toast.makeText(BeachActivity.this, message, Toast.LENGTH_SHORT).show();
                        binding.progressBar.setVisibility(View.INVISIBLE);
                    }
                });

        Volley.newRequestQueue(BeachActivity.this).add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            android.content.Intent i = new android.content.Intent(this, MainActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.beachlist) {
            Toast.makeText(this, "You are already on this page", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.reservation) {
            android.content.Intent i = new android.content.Intent(this, Reservation.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}