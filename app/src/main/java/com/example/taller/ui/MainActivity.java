package com.example.taller.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.taller.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView nav = findViewById(R.id.bottom_nav);

        loadFragment(new HomeFragment());

        nav.setOnItemSelectedListener(item -> {
            Fragment f;
            int id = item.getItemId();
            if (id == R.id.nav_home)           f = new HomeFragment();
            else if (id == R.id.nav_clientes)  f = new ClientesFragment();
            else if (id == R.id.nav_servicios) f = new ServiciosFragment();
            else if (id == R.id.nav_agenda)    f = new PlaceholderFragment("Agenda");
            else                               f = new PlaceholderFragment("Reportes");
            return loadFragment(f);
        });
    }

    private boolean loadFragment(Fragment f) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, f)
                .commit();
        return true;
    }
}