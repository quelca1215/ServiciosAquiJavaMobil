package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_screen);
        // Control de Insets para pantalla completa y BottomNav abajo
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Inicialización del BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_inicio);

        // Evento de selección de pestañas
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_inicio) {
                startActivity(new Intent(Profile_screen.this, Home_screen.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_mensajes) {
                startActivity(new Intent(Profile_screen.this, Messages_screen.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_solicitudes) {
                finish();
                return true;
            } else if (itemId == R.id.nav_perfil) {
                finish();
                return true;
            }

            return false;
        });
    }
    public void siguiente4(View view) {
        Intent intent = new Intent(Profile_screen.this, MainActivity.class);
        startActivity(intent);
    }
}