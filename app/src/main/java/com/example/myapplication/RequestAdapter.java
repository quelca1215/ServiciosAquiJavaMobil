package com.example.myapplication;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    public interface OnRequestClickListener {
        void onRequestClick(ServiceRequestModel model);
    }

    private List<ServiceRequestModel> list;
    private OnRequestClickListener listener;
    private boolean isProviderMode;

    public RequestAdapter(List<ServiceRequestModel> list, boolean isProviderMode, OnRequestClickListener listener) {
        this.list = list;
        this.isProviderMode = isProviderMode;
        this.listener = listener;
    }

    public void updateList(List<ServiceRequestModel> newList, boolean isProviderMode) {
        this.list = newList;
        this.isProviderMode = isProviderMode;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceRequestModel item = list.get(position);

        holder.tvId.setText("#" + item.getId() + " • " + item.getDateText());
        holder.tvTitle.setText(item.getTitle());

        if (isProviderMode) {
            holder.tvTarget.setText("Cliente: " + item.getClientName());
        } else {
            holder.tvTarget.setText("Especialista: " + item.getProviderName());
        }

        // Configurar estado y badge
        String status = item.getStatus();
        holder.tvStatusBadge.setText(status);

        if (status.equalsIgnoreCase("ACEPTADA")) {
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_emerald);
            holder.tvStatusBadge.setTextColor(Color.parseColor("#059669"));
            holder.tvBudget.setText("Trabajo Aceptado • " + item.getOriginalBudgetBs());
        } else if (status.equalsIgnoreCase("CONTRAOFERTA")) {
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_amber);
            holder.tvStatusBadge.setTextColor(Color.parseColor("#D97706"));
            if (!TextUtils.isEmpty(item.getCounterOfferBs())) {
                holder.tvBudget.setText("Contraoferta: " + item.getCounterOfferBs());
            } else {
                holder.tvBudget.setText("Presupuesto: " + item.getOriginalBudgetBs());
            }
        } else if (status.equalsIgnoreCase("RECHAZADA")) {
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_purple);
            holder.tvStatusBadge.setTextColor(Color.parseColor("#EF4444"));
            holder.tvBudget.setText("Solicitud Rechazada");
        } else {
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_blue);
            holder.tvStatusBadge.setTextColor(Color.parseColor("#2563EB"));
            holder.tvBudget.setText("Presupuesto: " + item.getOriginalBudgetBs());
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onRequestClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvStatusBadge, tvTitle, tvTarget, tvBudget;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvRequestId);
            tvStatusBadge = itemView.findViewById(R.id.tvRequestStatusBadge);
            tvTitle = itemView.findViewById(R.id.tvRequestTitle);
            tvTarget = itemView.findViewById(R.id.tvRequestTarget);
            tvBudget = itemView.findViewById(R.id.tvRequestBudgetSummary);
        }
    }
}