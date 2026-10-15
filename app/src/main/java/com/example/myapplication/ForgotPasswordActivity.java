package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etForgotEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etForgotEmail = findViewById(R.id.etForgotEmail);
    }

    public void enviarEnlace(View view) {
        String email = etForgotEmail != null ? etForgotEmail.getText().toString().trim() : "";
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Por favor, ingresa tu correo electrónico.", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Enlace de recuperación enviado a: " + email, Toast.LENGTH_LONG).show();
        finish();
    }

    public void volverAlLogin(View view) {
        finish();
    }
}