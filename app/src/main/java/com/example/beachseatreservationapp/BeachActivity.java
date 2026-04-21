package com.example.beachseatreservationapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
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

    String URL = "http://192.168.1.109/getAllbeach.php";  // ← your IP

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBeachesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("☀\uFE0F");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        beachAdapter = new BeachAdapter(beachList, this);
        recyclerView.setAdapter(beachAdapter);
        loadBeaches();
    }

    private void loadBeaches() {
        StringRequest request = new StringRequest(Request.Method.GET, URL,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            beachList.add(new Beach(
                                    obj.getInt("id"),
                                    obj.getString("name"),
                                    obj.getString("location"),
                                    obj.getString("description")
                            ));
                        }
                        beachAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
        );

        Volley.newRequestQueue(this).add(request);
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