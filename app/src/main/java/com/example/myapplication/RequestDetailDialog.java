package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.Locale;

public class RequestDetailDialog extends BottomSheetDialogFragment {

    private static final String ARG_REQUEST = "arg_request";

    public interface OnRequestUpdatedListener {
        void onRequestUpdated();
    }

    private OnRequestUpdatedListener listener;

    public static RequestDetailDialog newInstance(ServiceRequestModel request) {
        RequestDetailDialog dialog = new RequestDetailDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_REQUEST, request);
        dialog.setArguments(args);
        return dialog;
    }

    public void setListener(OnRequestUpdatedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_request_detail, container, false);

        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_REQUEST)) {
            ServiceRequestModel request = (ServiceRequestModel) args.getSerializable(ARG_REQUEST);
            if (request != null) {
                bindData(view, request);
            }
        }

        return view;
    }

    private void bindData(View view, ServiceRequestModel request) {
        boolean isProvider = UserRoleManager.isProviderMode(getContext());

        TextView tvId = view.findViewById(R.id.tvRequestDetailId);
        TextView tvStatus = view.findViewById(R.id.tvRequestDetailStatus);
        TextView tvTitle = view.findViewById(R.id.tvRequestDetailTitle);
        TextView tvTargetLabel = view.findViewById(R.id.tvRequestTargetLabel);
        TextView tvTargetName = view.findViewById(R.id.tvRequestTargetName);
        TextView tvLocation = view.findViewById(R.id.tvRequestLocation);
        TextView tvBody = view.findViewById(R.id.tvRequestDetailBody);

        TextView tvDate = view.findViewById(R.id.tvRequestTargetDate);
        TextView tvSchedule = view.findViewById(R.id.tvRequestSchedule);
        TextView tvExecutionTime = view.findViewById(R.id.tvRequestExecutionTime);

        TextView tvOriginalBudget = view.findViewById(R.id.tvRequestOriginalBudget);
        TextView tvCounterOffer = view.findViewById(R.id.tvRequestCounterOffer);
        TextView tvCounterScheduleSummary = view.findViewById(R.id.tvCounterDateScheduleSummary);

        LinearLayout layoutCounterInput = view.findViewById(R.id.layoutCounterOfferInput);
        EditText etCounterInput = view.findViewById(R.id.etCounterOfferInput);
        EditText etCounterScheduleInput = view.findViewById(R.id.etCounterScheduleInput);

        Button btnPrimary = view.findViewById(R.id.btnRequestActionPrimary);
        Button btnSecondary = view.findViewById(R.id.btnRequestActionSecondary);
        Button btnDanger = view.findViewById(R.id.btnRequestActionDanger);

        if (tvId != null) tvId.setText("Solicitud #" + request.getId());
        if (tvStatus != null) tvStatus.setText(request.getStatus());
        if (tvTitle != null) tvTitle.setText(request.getTitle());
        if (tvBody != null) tvBody.setText(request.getDetail());
        if (tvLocation != null) tvLocation.setText(request.getLocation());

        if (tvDate != null) tvDate.setText("Fecha solicitada: " + (request.getTargetDate() != null ? request.getTargetDate() : "Mañana"));
        if (tvSchedule != null) tvSchedule.setText("Horario preferido: " + (request.getPreferredSchedule() != null ? request.getPreferredSchedule() : "Mañana (09:00 AM - 12:00 PM)"));
        if (tvExecutionTime != null) tvExecutionTime.setText("Duración de trabajo: " + (request.getExecutionTime() != null ? request.getExecutionTime() : "2-3 horas estimadas"));

        if (tvOriginalBudget != null) tvOriginalBudget.setText(request.getOriginalBudgetBs());

        if (tvCounterOffer != null) {
            if (!TextUtils.isEmpty(request.getCounterOfferBs())) {
                tvCounterOffer.setText(request.getCounterOfferBs());
            } else {
                tvCounterOffer.setText("Sin contraoferta");
            }
        }

        if (tvCounterScheduleSummary != null && !TextUtils.isEmpty(request.getCounterDateSchedule())) {
            tvCounterScheduleSummary.setVisibility(View.VISIBLE);
            tvCounterScheduleSummary.setText("Horario propuesto por prestador: " + request.getCounterDateSchedule());
        }

        // DATE / TIME PICKER NATIVO PARA CONTRAOFERTA DEL PRESTADOR
        if (etCounterScheduleInput != null) {
            etCounterScheduleInput.setOnClickListener(v -> {
                Calendar c = Calendar.getInstance();
                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);

                if (getContext() != null) {
                    DatePickerDialog datePicker = new DatePickerDialog(getContext(), (dateView, year, month, dayOfMonth) -> {
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, (month + 1), year);

                        // LUEGO DE ELEGIR FECHA, ABRIR TIME PICKER
                        TimePickerDialog timePicker = new TimePickerDialog(getContext(), (timeView, hourOfDay, minute) -> {
                            String amPm = hourOfDay >= 12 ? "PM" : "AM";
                            int formattedHour = hourOfDay % 12;
                            if (formattedHour == 0) formattedHour = 12;
                            String selectedTime = String.format(Locale.getDefault(), "%02d:%02d %s", formattedHour, minute, amPm);

                            etCounterScheduleInput.setText(selectedDate + " " + selectedTime);
                        }, 14, 0, false);
                        timePicker.show();

                    }, y, m, d);
                    datePicker.show();
                }
            });
        }

        if (isProvider) {
            if (tvTargetLabel != null) tvTargetLabel.setText("Cliente Solicitante:");
            if (tvTargetName != null) tvTargetName.setText(request.getClientName());

            if (request.getStatus().equalsIgnoreCase("PENDIENTE")) {
                btnPrimary.setText("Aceptar Solicitud (Presupuesto & Horario Cliente)");
                btnSecondary.setVisibility(View.VISIBLE);
                btnSecondary.setText("Proponer Contraoferta (Precio & Horario)");
                btnDanger.setVisibility(View.VISIBLE);

                btnPrimary.setOnClickListener(v -> {
                    request.setStatus("ACEPTADA");
                    Toast.makeText(getContext(), "¡Solicitud y horario aceptados exitosamente!", Toast.LENGTH_LONG).show();
                    if (listener != null) listener.onRequestUpdated();
                    dismiss();
                });

                btnSecondary.setOnClickListener(v -> {
                    if (layoutCounterInput.getVisibility() == View.GONE) {
                        layoutCounterInput.setVisibility(View.VISIBLE);
                        btnSecondary.setText("Confirmar y Enviar Contraoferta");
                    } else {
                        String counterValStr = etCounterInput != null ? etCounterInput.getText().toString().trim() : "";
                        String counterSched = etCounterScheduleInput != null ? etCounterScheduleInput.getText().toString().trim() : "";

                        double counterNum = 0.0;
                        try {
                            counterNum = Double.parseDouble(counterValStr.replace("Bs.", "").trim());
                        } catch (Exception e) {
                            counterNum = 0.0;
                        }

                        if (counterNum <= 0) {
                            if (etCounterInput != null) etCounterInput.setError("Ingresa un monto válido en Bs.");
                            Toast.makeText(getContext(), "Ingresa un monto numérico válido en Bs. para la contraoferta", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (TextUtils.isEmpty(counterSched)) {
                            counterSched = "Próximo Sábado 02:00 PM (3 horas)";
                        }

                        String formattedBs = String.format(Locale.getDefault(), "Bs. %.2f", counterNum);
                        request.setCounterOfferBs(formattedBs);
                        request.setCounterDateSchedule(counterSched);
                        request.setStatus("CONTRAOFERTA");

                        Toast.makeText(getContext(), "Contraoferta enviada (" + formattedBs + " - " + counterSched + ")", Toast.LENGTH_LONG).show();
                        if (listener != null) listener.onRequestUpdated();
                        dismiss();
                    }
                });

                btnDanger.setOnClickListener(v -> {
                    request.setStatus("RECHAZADA");
                    Toast.makeText(getContext(), "Solicitud rechazada", Toast.LENGTH_SHORT).show();
                    if (listener != null) listener.onRequestUpdated();
                    dismiss();
                });
            } else {
                btnPrimary.setText("Cerrar Detalle");
                btnSecondary.setVisibility(View.GONE);
                btnDanger.setVisibility(View.GONE);
                btnPrimary.setOnClickListener(v -> dismiss());
            }

        } else {
            // MODO CLIENTE
            if (tvTargetLabel != null) tvTargetLabel.setText("Especialista Asignado:");
            if (tvTargetName != null) tvTargetName.setText(request.getProviderName());

            if (request.getStatus().equalsIgnoreCase("CONTRAOFERTA")) {
                btnPrimary.setText("Aceptar Contraoferta (" + request.getCounterOfferBs() + ")");
                btnSecondary.setVisibility(View.GONE);
                btnDanger.setVisibility(View.VISIBLE);
                btnDanger.setText("Cancelar Solicitud");

                btnPrimary.setOnClickListener(v -> {
                    request.setStatus("ACEPTADA");
                    Toast.makeText(getContext(), "¡Contraoferta de precio y horario aceptada! Trabajo confirmado.", Toast.LENGTH_LONG).show();
                    if (listener != null) listener.onRequestUpdated();
                    dismiss();
                });

                btnDanger.setOnClickListener(v -> {
                    request.setStatus("RECHAZADA");
                    Toast.makeText(getContext(), "Solicitud cancelada", Toast.LENGTH_SHORT).show();
                    if (listener != null) listener.onRequestUpdated();
                    dismiss();
                });
            } else {
                btnPrimary.setText("Cerrar Detalle");
                btnSecondary.setVisibility(View.GONE);
                btnDanger.setVisibility(View.GONE);
                btnPrimary.setOnClickListener(v -> dismiss());
            }
        }
    }
}