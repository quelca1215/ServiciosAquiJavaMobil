package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProfessionalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_LIST = 0;
    public static final int VIEW_TYPE_GRID = 1;

    public interface OnProfessionalClickListener {
        void onProfileClick(ProfessionalModel model);
    }

    private List<ProfessionalModel> list;
    private OnProfessionalClickListener listener;
    private boolean isGridView = false;

    public ProfessionalAdapter(List<ProfessionalModel> list, OnProfessionalClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public void setGridView(boolean gridView) {
        this.isGridView = gridView;
        notifyDataSetChanged();
    }

    public void updateList(List<ProfessionalModel> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return isGridView ? VIEW_TYPE_GRID : VIEW_TYPE_LIST;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_GRID) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalog_provider_compact, parent, false);
            return new CompactViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalog_provider, parent, false);
            return new ListViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProfessionalModel item = list.get(position);

        if (holder instanceof ListViewHolder) {
            ListViewHolder lHolder = (ListViewHolder) holder;
            lHolder.tvName.setText(item.getName());
            lHolder.tvTitle.setText(item.getTitle());
            lHolder.tvStatusBadge.setText(item.getStatusBadge());
            lHolder.tvRating.setText(String.valueOf(item.getRating()));
            lHolder.tvReviews.setText("(" + item.getReviewsCount() + ")");
            lHolder.tvDistance.setText(item.getDistanceKm() + " km");
            lHolder.tvPrice.setText(item.getPriceText());

            if (item.getSkills() != null && !item.getSkills().isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < item.getSkills().size(); i++) {
                    sb.append("• ").append(item.getSkills().get(i)).append(" ");
                }
                lHolder.tvSkillsSummary.setText(sb.toString().trim());
            } else {
                lHolder.tvSkillsSummary.setText("Servicio garantizado y personalizado");
            }

            lHolder.imgVerified.setVisibility(item.isVerified() ? View.VISIBLE : View.GONE);

            lHolder.itemView.setOnClickListener(v -> {
                if (listener != null) listener.onProfileClick(item);
            });
        } else if (holder instanceof CompactViewHolder) {
            CompactViewHolder cHolder = (CompactViewHolder) holder;
            cHolder.tvName.setText(item.getName());
            cHolder.tvTitle.setText(item.getTitle());
            cHolder.tvRating.setText(String.valueOf(item.getRating()));
            cHolder.tvDistance.setText("• " + item.getDistanceKm() + " km");
            cHolder.tvPrice.setText(item.getPriceText());
            cHolder.imgVerified.setVisibility(item.isVerified() ? View.VISIBLE : View.GONE);

            cHolder.itemView.setOnClickListener(v -> {
                if (listener != null) listener.onProfileClick(item);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvTitle, tvStatusBadge, tvRating, tvReviews, tvDistance, tvPrice, tvSkillsSummary;
        ImageView imgVerified;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvProviderName);
            tvTitle = itemView.findViewById(R.id.tvProviderTitle);
            tvStatusBadge = itemView.findViewById(R.id.tvStatusBadge);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvReviews = itemView.findViewById(R.id.tvReviews);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvSkillsSummary = itemView.findViewById(R.id.tvSkillsSummary);
            imgVerified = itemView.findViewById(R.id.imgVerifiedBadge);
        }
    }

    static class CompactViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvTitle, tvRating, tvDistance, tvPrice;
        ImageView imgVerified;

        public CompactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNameCompact);
            tvTitle = itemView.findViewById(R.id.tvTitleCompact);
            tvRating = itemView.findViewById(R.id.tvRatingCompact);
            tvDistance = itemView.findViewById(R.id.tvDistanceCompact);
            tvPrice = itemView.findViewById(R.id.tvPriceCompact);
            imgVerified = itemView.findViewById(R.id.imgVerifiedBadgeCompact);
        }
    }
}