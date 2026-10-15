package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FilterDialog extends BottomSheetDialogFragment {

    public interface OnFilterAppliedListener {
        void onFilterApplied(boolean near, boolean top, boolean available, boolean budget);
    }

    private boolean isNear, isTop, isAvailable, isBudget;
    private OnFilterAppliedListener listener;

    public static FilterDialog newInstance(boolean near, boolean top, boolean available, boolean budget) {
        FilterDialog dialog = new FilterDialog();
        Bundle args = new Bundle();
        args.putBoolean("near", near);
        args.putBoolean("top", top);
        args.putBoolean("available", available);
        args.putBoolean("budget", budget);
        dialog.setArguments(args);
        return dialog;
    }

    public void setListener(OnFilterAppliedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_filter, container, false);

        Bundle args = getArguments();
        if (args != null) {
            isNear = args.getBoolean("near", false);
            isTop = args.getBoolean("top", false);
            isAvailable = args.getBoolean("available", false);
            isBudget = args.getBoolean("budget", false);
        }

        CheckBox chkNear = view.findViewById(R.id.chkFilterNear);
        CheckBox chkTop = view.findViewById(R.id.chkFilterTop);
        CheckBox chkAvailable = view.findViewById(R.id.chkFilterAvailable);
        CheckBox chkBudget = view.findViewById(R.id.chkFilterBudget);

        chkNear.setChecked(isNear);
        chkTop.setChecked(isTop);
        chkAvailable.setChecked(isAvailable);
        chkBudget.setChecked(isBudget);

        TextView btnReset = view.findViewById(R.id.btnResetFilters);
        btnReset.setOnClickListener(v -> {
            chkNear.setChecked(false);
            chkTop.setChecked(false);
            chkAvailable.setChecked(false);
            chkBudget.setChecked(false);
        });

        View btnApply = view.findViewById(R.id.btnApplyFilters);
        btnApply.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFilterApplied(
                        chkNear.isChecked(),
                        chkTop.isChecked(),
                        chkAvailable.isChecked(),
                        chkBudget.isChecked()
                );
            }
            dismiss();
        });

        return view;
    }
}