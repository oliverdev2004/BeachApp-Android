package com.example.beachseatreservationapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.beachseatreservationapp.databinding.ActivityMainBinding;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    RecyclerView recyclerView;
    com.example.week8app3sp25section12rv.MyRecyclerViewAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("☀\uFE0F");

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
       // appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //floating bar intent
                Intent i = new Intent(MainActivity.this, Reservation.class);
                startActivity(i);
            }
        });

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] titles={"Maldives", "Bali, Indonesia", "Santorini, Greece", "Phuket, Thailand ","Cancun, Mexico","Mykonos, Greece ","Boracay, Philippines","Côte d'Azur, France","Maui, Hawaii ","Dubai, UAE "};
        int[] images={R.drawable.maldive, R.drawable.bali,R.drawable.santorini,R.drawable.thailand,R.drawable.mexico,R.drawable.mykonos,R.drawable.philipine,R.drawable.cote,R.drawable.huwai,R.drawable.dubai };
        String[] descriptions = {
                " Overwater bungalows, crystal clear water, Indian Ocean",
                "Kuta Beach, Seminyak, tropical paradise",
                "Perissa Black Sand Beach, stunning views",
                "Patong Beach, turquoise water, vibrant nightlife",
                "Hotel Zone, Caribbean white sand beaches",
                "Paradise Beach, crystal blue Aegean Sea",
                "White Beach, powder white sand, clear water",
                "French Riviera, glamorous Mediterranean beaches",
                "Kaanapali Beach, volcanic black sand, surfing",
                "Jumeirah Beach, luxury resorts, skyline views"
        };
        //create a list of items from the array
        List<item_java> itemList=new ArrayList<>();
        for(int i=0; i<titles.length; i++)
        {
            itemList.add(new item_java(titles[i], images[i], descriptions[i]));
        }

        myadapter=new com.example.week8app3sp25section12rv.MyRecyclerViewAdapter(itemList);
        recyclerView.setAdapter(myadapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.home) {
            Toast.makeText(this, "You are already on this page", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.beachlist) {
            Intent i = new Intent(this, BeachActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.reservation) {
            Intent i = new Intent(this, Reservation.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

  //  @Override
   // public boolean onSupportNavigateUp() {
     //   NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
      //  return NavigationUI.navigateUp(navController, appBarConfiguration)
         //       || super.onSupportNavigateUp();
   // }
}