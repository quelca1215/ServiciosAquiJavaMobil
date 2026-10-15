package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ProviderDetailDialog extends BottomSheetDialogFragment {

    private static final String ARG_PROVIDER = "arg_provider";

    public static ProviderDetailDialog newInstance(ProfessionalModel provider) {
        ProviderDetailDialog dialog = new ProviderDetailDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROVIDER, provider);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_provider_detail, container, false);

        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_PROVIDER)) {
            ProfessionalModel provider = (ProfessionalModel) args.getSerializable(ARG_PROVIDER);
            if (provider != null) {
                bindData(view, provider);
            }
        }

        return view;
    }

    private void bindData(View view, ProfessionalModel provider) {
        TextView tvName = view.findViewById(R.id.tvDetailName);
        TextView tvTitle = view.findViewById(R.id.tvDetailTitle);
        TextView tvBadge = view.findViewById(R.id.tvDetailBadge);
        TextView tvRating = view.findViewById(R.id.tvDetailRating);
        TextView tvDistance = view.findViewById(R.id.tvDetailDistance);
        TextView tvPrice = view.findViewById(R.id.tvDetailPrice);
        TextView tvBio = view.findViewById(R.id.tvDetailBio);

        if (tvName != null) tvName.setText(provider.getName());
        if (tvTitle != null) tvTitle.setText(provider.getTitle());
        if (tvBadge != null) tvBadge.setText(provider.getStatusBadge());
        if (tvRating != null) tvRating.setText(provider.getRating() + " (" + provider.getReviewsCount() + ")");
        if (tvDistance != null) tvDistance.setText(provider.getDistanceKm() + " km");
        if (tvPrice != null) tvPrice.setText(provider.getPriceText());
        if (tvBio != null) tvBio.setText(provider.getBio());

        // ÚNICA OPCIÓN: VER PERFIL COMPLETO
        View btnViewFullProfile = view.findViewById(R.id.btnViewFullProfile);
        if (btnViewFullProfile != null) {
            btnViewFullProfile.setOnClickListener(v -> {
                if (getContext() != null) {
                    Intent intent = new Intent(getContext(), FullProfileActivity.class);
                    intent.putExtra(FullProfileActivity.EXTRA_PROVIDER, provider);
                    startActivity(intent);
                }
                dismiss();
            });
        }
    }
}