package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class GhostScreenDialog extends BottomSheetDialogFragment {

    public static GhostScreenDialog newInstance() {
        return new GhostScreenDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_ghost_screen, container, false);

        Button btnClose = view.findViewById(R.id.btnGhostClose);
        if (btnClose != null) {
            btnClose.setOnClickListener(v -> dismiss());
        }

        return view;
    }
}