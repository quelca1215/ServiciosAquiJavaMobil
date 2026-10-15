package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FullProfileActivity extends AppCompatActivity {

    public static final String EXTRA_PROVIDER = "EXTRA_PROVIDER";

    private ProfessionalModel provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_full_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getIntent() != null && getIntent().hasExtra(EXTRA_PROVIDER)) {
            provider = (ProfessionalModel) getIntent().getSerializableExtra(EXTRA_PROVIDER);
        }

        initViews();
    }

    private void initViews() {
        ImageButton btnBack = findViewById(R.id.btnBackFullProfile);
        btnBack.setOnClickListener(v -> finish());

        Button btnQuote = findViewById(R.id.btnFullProfileQuote);
        Button btnCall = findViewById(R.id.btnFullProfileCall);

        if (provider != null) {
            TextView tvName = findViewById(R.id.tvFullProfileName);
            TextView tvTitle = findViewById(R.id.tvFullProfileTitle);
            TextView tvRating = findViewById(R.id.tvFullRating);
            TextView tvDistance = findViewById(R.id.tvFullDistance);
            TextView tvPrice = findViewById(R.id.tvFullPrice);
            TextView tvBio = findViewById(R.id.tvFullBio);
            TextView tvSkills = findViewById(R.id.tvFullSkills);

            if (tvName != null) tvName.setText(provider.getName());
            if (tvTitle != null) tvTitle.setText(provider.getTitle());
            if (tvRating != null) tvRating.setText(String.valueOf(provider.getRating()));
            if (tvDistance != null) tvDistance.setText(provider.getDistanceKm() + " km");
            if (tvPrice != null) tvPrice.setText(provider.getPriceText());
            if (tvBio != null) tvBio.setText(provider.getBio());

            if (tvSkills != null && provider.getSkills() != null) {
                StringBuilder sb = new StringBuilder();
                for (String skill : provider.getSkills()) {
                    sb.append("• ").append(skill).append("  ");
                }
                tvSkills.setText(sb.toString().trim());
            }

            if (btnQuote != null) {
                btnQuote.setOnClickListener(v -> {
                    QuoteDialog dialog = QuoteDialog.newInstance(provider);
                    dialog.show(getSupportFragmentManager(), "QuoteDialog");
                });
            }

            if (btnCall != null) {
                btnCall.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + provider.getPhone()));
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(this, "Llamando a " + provider.getName() + " (" + provider.getPhone() + ")", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}