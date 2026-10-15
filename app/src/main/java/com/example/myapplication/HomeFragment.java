package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private TextView btnSegmentClient;
    private TextView btnSegmentProvider;
    private TextView tvModeBadge;
    private TextView tvClientSubtext;
    private View scrollSpecialties;
    private ImageView imgVerifiedBadge;
    private View layoutClient;
    private View layoutProvider;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        layoutClient = view.findViewById(R.id.layoutClientHomeMode);
        layoutProvider = view.findViewById(R.id.layoutProviderHomeMode);
        tvModeBadge = view.findViewById(R.id.tvHeaderModeBadge);
        tvClientSubtext = view.findViewById(R.id.tvClientSubtext);
        scrollSpecialties = view.findViewById(R.id.layoutProviderSpecialties);
        imgVerifiedBadge = view.findViewById(R.id.imgHeaderVerifiedBadge);

        btnSegmentClient = view.findViewById(R.id.btnSegmentClient);
        btnSegmentProvider = view.findViewById(R.id.btnSegmentProvider);

        if (btnSegmentClient != null && btnSegmentProvider != null) {
            btnSegmentClient.setOnClickListener(v -> applyRoleMode(false, true));
            btnSegmentProvider.setOnClickListener(v -> applyRoleMode(true, true));
        }

        setupCategoryListeners(view);

        EditText etSearchHome = view.findViewById(R.id.etSearchHome);
        if (etSearchHome != null) {
            etSearchHome.setOnClickListener(v -> openCatalog("Todas"));
        }

        View btnRespond = view.findViewById(R.id.btnRespondQuote1);
        if (btnRespond != null) {
            btnRespond.setOnClickListener(v -> {
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Abriendo cotización de Roberto M. para enviar presupuesto...", Toast.LENGTH_LONG).show();
                }
            });
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean isProvider = UserRoleManager.isProviderMode(getContext());
        applyRoleMode(isProvider, false);
    }

    private void applyRoleMode(boolean isProvider, boolean showToast) {
        if (getContext() != null) {
            UserRoleManager.setProviderMode(getContext(), isProvider);
        }

        if (isProvider) {
            if (btnSegmentProvider != null) {
                btnSegmentProvider.setBackgroundResource(R.drawable.bg_segment_active_provider);
                btnSegmentProvider.setTextColor(Color.WHITE);
            }
            if (btnSegmentClient != null) {
                btnSegmentClient.setBackground(null);
                btnSegmentClient.setTextColor(Color.parseColor("#475569"));
            }

            if (tvModeBadge != null) tvModeBadge.setText("Modo Prestador Activo");
            if (tvClientSubtext != null) tvClientSubtext.setVisibility(View.GONE);
            if (scrollSpecialties != null) scrollSpecialties.setVisibility(View.VISIBLE);
            if (imgVerifiedBadge != null) imgVerifiedBadge.setVisibility(View.VISIBLE);

            if (layoutClient != null) layoutClient.setVisibility(View.GONE);
            if (layoutProvider != null) layoutProvider.setVisibility(View.VISIBLE);

            if (showToast && getContext() != null) {
                Toast.makeText(getContext(), "Activado Modo Prestador de Servicios (Especialista)", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (btnSegmentClient != null) {
                btnSegmentClient.setBackgroundResource(R.drawable.bg_segment_active_client);
                btnSegmentClient.setTextColor(Color.WHITE);
            }
            if (btnSegmentProvider != null) {
                btnSegmentProvider.setBackground(null);
                btnSegmentProvider.setTextColor(Color.parseColor("#475569"));
            }

            if (tvModeBadge != null) tvModeBadge.setText("Modo Cliente Activo");
            if (tvClientSubtext != null) tvClientSubtext.setVisibility(View.VISIBLE);
            if (scrollSpecialties != null) scrollSpecialties.setVisibility(View.GONE);
            if (imgVerifiedBadge != null) imgVerifiedBadge.setVisibility(View.GONE);

            if (layoutClient != null) layoutClient.setVisibility(View.VISIBLE);
            if (layoutProvider != null) layoutProvider.setVisibility(View.GONE);

            if (showToast && getContext() != null) {
                Toast.makeText(getContext(), "Activado Modo Cliente (Contratación)", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupCategoryListeners(View view) {
        int[] catIds = {
                R.id.btnCatHogar,
                R.id.btnCatGasfiteria,
                R.id.btnCatElectricidad,
                R.id.btnCatPlomeria,
                R.id.btnCatTransporte,
                R.id.btnCatLimpieza,
                R.id.btnVerTodas
        };

        String[] catNames = {
                "Hogar",
                "Gasfitería",
                "Electricidad",
                "Plomería",
                "Transporte",
                "Limpieza",
                "Todas"
        };

        for (int idx = 0; idx < catIds.length; idx++) {
            View btn = view.findViewById(catIds[idx]);
            if (btn != null) {
                final String catName = catNames[idx];
                btn.setOnClickListener(v -> openCatalog(catName));
            }
        }
    }

    private void openCatalog(String categoryName) {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), CatalogActivity.class);
            intent.putExtra("EXTRA_CATEGORY_NAME", categoryName);
            startActivity(intent);
        }
    }
}