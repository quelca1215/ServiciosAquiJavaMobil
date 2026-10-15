package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SolicitudesFragment extends Fragment {

    private RecyclerView rvSolicitudes;
    private View layoutEmpty;
    private TextView tvRoleBadge;
    private RequestAdapter adapter;

    public SolicitudesFragment() {
        // Constructor público requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_solicitudes, container, false);

        rvSolicitudes = view.findViewById(R.id.rvSolicitudes);
        layoutEmpty = view.findViewById(R.id.layoutEmptySolicitudes);
        tvRoleBadge = view.findViewById(R.id.tvRequestsRoleBadge);

        if (rvSolicitudes != null) {
            rvSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    private void updateData() {
        if (getContext() == null) return;

        boolean isProvider = UserRoleManager.isProviderMode(getContext());

        if (tvRoleBadge != null) {
            if (isProvider) {
                tvRoleBadge.setText("Modo Prestador • Cotizaciones Recibidas");
                tvRoleBadge.setBackgroundResource(R.drawable.bg_badge_blue);
                tvRoleBadge.setTextColor(Color.parseColor("#2563EB"));
            } else {
                tvRoleBadge.setText("Modo Cliente • Solicitudes Enviadas");
                tvRoleBadge.setBackgroundResource(R.drawable.bg_badge_emerald);
                tvRoleBadge.setTextColor(Color.parseColor("#059669"));
            }
        }

        List<ServiceRequestModel> requests = isProvider ?
                RequestMockDataProvider.getProviderRequests() :
                RequestMockDataProvider.getClientRequests();

        if (adapter == null) {
            adapter = new RequestAdapter(requests, isProvider, model -> {
                RequestDetailDialog dialog = RequestDetailDialog.newInstance(model);
                dialog.setListener(this::updateData);
                if (getFragmentManager() != null) {
                    dialog.show(getFragmentManager(), "RequestDetailDialog");
                }
            });
            if (rvSolicitudes != null) {
                rvSolicitudes.setAdapter(adapter);
            }
        } else {
            adapter.updateList(requests, isProvider);
        }

        if (requests.isEmpty()) {
            if (rvSolicitudes != null) rvSolicitudes.setVisibility(View.GONE);
            if (layoutEmpty != null) layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            if (rvSolicitudes != null) rvSolicitudes.setVisibility(View.VISIBLE);
            if (layoutEmpty != null) layoutEmpty.setVisibility(View.GONE);
        }
    }
}