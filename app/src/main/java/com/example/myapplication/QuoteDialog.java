package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.Locale;

public class QuoteDialog extends BottomSheetDialogFragment {

    private static final String ARG_PROVIDER = "arg_provider";

    public static QuoteDialog newInstance(ProfessionalModel provider) {
        QuoteDialog dialog = new QuoteDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROVIDER, provider);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_quote_request, container, false);

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
        TextView tvTarget = view.findViewById(R.id.tvQuoteProviderTarget);
        if (tvTarget != null) {
            tvTarget.setText("Especialista: " + provider.getName());
        }

        EditText etDetail = view.findViewById(R.id.etQuoteDetail);
        EditText etDate = view.findViewById(R.id.etQuoteDate);
        EditText etTime = view.findViewById(R.id.etQuoteTime);
        EditText etDuration = view.findViewById(R.id.etQuoteDuration);
        EditText etBudget = view.findViewById(R.id.etQuoteBudget);
        EditText etLocation = view.findViewById(R.id.etQuoteLocation);

        // DATE PICKER NATIVO AL TOCAR ETQUOTEDATE
        if (etDate != null) {
            etDate.setOnClickListener(v -> {
                Calendar c = Calendar.getInstance();
                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);

                if (getContext() != null) {
                    DatePickerDialog datePicker = new DatePickerDialog(getContext(), (dateView, year, month, dayOfMonth) -> {
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, (month + 1), year);
                        etDate.setText(selectedDate);
                    }, y, m, d);
                    datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datePicker.show();
                }
            });
        }

        // TIME PICKER NATIVO AL TOCAR ETQUOTETIME
        if (etTime != null) {
            etTime.setOnClickListener(v -> {
                Calendar c = Calendar.getInstance();
                int h = c.get(Calendar.HOUR_OF_DAY);
                int min = c.get(Calendar.MINUTE);

                if (getContext() != null) {
                    TimePickerDialog timePicker = new TimePickerDialog(getContext(), (timeView, hourOfDay, minute) -> {
                        String amPm = hourOfDay >= 12 ? "PM" : "AM";
                        int formattedHour = hourOfDay % 12;
                        if (formattedHour == 0) formattedHour = 12;
                        String selectedTime = String.format(Locale.getDefault(), "%02d:%02d %s", formattedHour, minute, amPm);
                        etTime.setText(selectedTime);
                    }, h, min, false);
                    timePicker.show();
                }
            });
        }

        View btnSend = view.findViewById(R.id.btnSendQuoteRequest);
        if (btnSend != null) {
            btnSend.setOnClickListener(v -> {
                String detail = etDetail != null ? etDetail.getText().toString().trim() : "";
                String targetDate = etDate != null ? etDate.getText().toString().trim() : "";
                String prefTime = etTime != null ? etTime.getText().toString().trim() : "";
                String duration = etDuration != null ? etDuration.getText().toString().trim() : "";
                String budgetStr = etBudget != null ? etBudget.getText().toString().trim() : "";
                String location = etLocation != null ? etLocation.getText().toString().trim() : "";

                // VALIDACIONES DE FORMULARIO
                if (TextUtils.isEmpty(detail)) {
                    if (etDetail != null) etDetail.setError("Por favor describe el trabajo requerido");
                    Toast.makeText(getContext(), "Ingresa el detalle del servicio que necesitas.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(targetDate)) {
                    Toast.makeText(getContext(), "Selecciona una fecha para el servicio.", Toast.LENGTH_SHORT).show();
                    return;
                }

                double budgetVal = 0.0;
                if (!TextUtils.isEmpty(budgetStr)) {
                    try {
                        budgetVal = Double.parseDouble(budgetStr.replace("Bs.", "").trim());
                    } catch (Exception e) {
                        budgetVal = 0.0;
                    }
                }

                if (budgetVal <= 0) {
                    if (etBudget != null) etBudget.setError("Ingresa un monto numérico válido en Bs.");
                    Toast.makeText(getContext(), "Por favor ingresa un presupuesto válido en Bs.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(location)) {
                    if (etLocation != null) etLocation.setError("Ingresa la dirección o lugar");
                    Toast.makeText(getContext(), "Ingresa el lugar o dirección del servicio.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(prefTime)) prefTime = "09:00 AM";
                if (TextUtils.isEmpty(duration)) duration = "2 horas estimadas";

                String finalBudgetFormatted = String.format(Locale.getDefault(), "Bs. %.2f", budgetVal);

                // Agregar la nueva solicitud al conjunto de datos
                ServiceRequestModel newReq = new ServiceRequestModel(
                        "REQ-C0" + (RequestMockDataProvider.getClientRequests().size() + 1),
                        detail.length() > 30 ? detail.substring(0, 30) + "..." : detail,
                        provider.getCategory(),
                        "Juan Pérez (Cliente)",
                        provider.getName(),
                        detail,
                        location,
                        finalBudgetFormatted,
                        "",
                        targetDate,
                        prefTime,
                        duration,
                        "",
                        "PENDIENTE",
                        "Hoy, Justo ahora"
                );

                RequestMockDataProvider.getClientRequests().add(0, newReq);

                Toast.makeText(getContext(), "¡Cotización enviada a " + provider.getName() + "!\nFecha: " + targetDate + "\nPresupuesto: " + finalBudgetFormatted, Toast.LENGTH_LONG).show();
                dismiss();
            });
        }
    }
}