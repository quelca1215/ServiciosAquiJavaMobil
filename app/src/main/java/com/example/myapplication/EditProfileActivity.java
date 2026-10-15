package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditProfileActivity extends AppCompatActivity {

    private boolean isProvider;

    private EditText etFullName, etEmail, etPhone, etAge, etAddress;
    private EditText etProviderTitle, etBasePrice, etExperience, etBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        isProvider = UserRoleManager.isProviderMode(this);

        initViews();
    }

    private void initViews() {
        ImageButton btnBack = findViewById(R.id.btnBackEditProfile);
        btnBack.setOnClickListener(v -> finish());

        Button btnSaveTop = findViewById(R.id.btnSaveProfileTop);
        Button btnSaveBottom = findViewById(R.id.btnSaveProfileBottom);

        TextView tvBadge = findViewById(R.id.tvEditRoleBadge);
        View cardProviderFields = findViewById(R.id.cardProviderEditFields);

        etFullName = findViewById(R.id.etEditFullName);
        etEmail = findViewById(R.id.etEditEmail);
        etPhone = findViewById(R.id.etEditPhone);
        etAge = findViewById(R.id.etEditAge);
        etAddress = findViewById(R.id.etEditAddress);

        etProviderTitle = findViewById(R.id.etEditProviderTitle);
        etBasePrice = findViewById(R.id.etEditBasePrice);
        etExperience = findViewById(R.id.etEditExperience);
        etBio = findViewById(R.id.etEditBio);

        // Pre-poblado inicial ficticio
        if (etFullName != null) etFullName.setText("Juan Pérez Rodríguez");
        if (etEmail != null) etEmail.setText("juan.perez@ejemplo.com");
        if (etPhone != null) etPhone.setText("+51 987 654 321");
        if (etAge != null) etAge.setText("28");
        if (etAddress != null) etAddress.setText("Av. América y Calle 4, Zona Central");

        if (isProvider) {
            if (tvBadge != null) {
                tvBadge.setText("Edición Perfil • Modo Prestador");
                tvBadge.setBackgroundResource(R.drawable.bg_badge_blue);
                tvBadge.setTextColor(Color.parseColor("#2563EB"));
            }
            if (cardProviderFields != null) {
                cardProviderFields.setVisibility(View.VISIBLE);
            }

            if (etProviderTitle != null) etProviderTitle.setText("Electricista Industrial & Residencial");
            if (etBasePrice != null) etBasePrice.setText("50.00");
            if (etExperience != null) etExperience.setText("10");
            if (etBio != null) etBio.setText("Especialista en cortocircuitos, tableros trifásicos, instalaciones LED y certificación de tableros.");
        } else {
            if (tvBadge != null) {
                tvBadge.setText("Edición Perfil • Modo Cliente");
                tvBadge.setBackgroundResource(R.drawable.bg_badge_emerald);
                tvBadge.setTextColor(Color.parseColor("#059669"));
            }
            if (cardProviderFields != null) {
                cardProviderFields.setVisibility(View.GONE);
            }
        }

        View.OnClickListener saveListener = v -> saveChanges();
        if (btnSaveTop != null) btnSaveTop.setOnClickListener(saveListener);
        if (btnSaveBottom != null) btnSaveBottom.setOnClickListener(saveListener);
    }

    private void saveChanges() {
        String fullName = etFullName != null ? etFullName.getText().toString().trim() : "";
        String phone = etPhone != null ? etPhone.getText().toString().trim() : "";
        String address = etAddress != null ? etAddress.getText().toString().trim() : "";

        if (TextUtils.isEmpty(fullName)) {
            if (etFullName != null) etFullName.setError("Ingresa tu nombre completo");
            Toast.makeText(this, "Por favor ingresa tu nombre completo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            if (etPhone != null) etPhone.setError("Ingresa un número de contacto");
            Toast.makeText(this, "Por favor ingresa un teléfono de contacto", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(address)) {
            if (etAddress != null) etAddress.setError("Ingresa tu dirección o ubicación");
            Toast.makeText(this, "Por favor ingresa tu dirección o ubicación", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "¡Perfil actualizado exitosamente!", Toast.LENGTH_LONG).show();
        finish();
    }
}