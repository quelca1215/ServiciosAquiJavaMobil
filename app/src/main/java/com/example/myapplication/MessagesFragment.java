package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MessagesFragment extends Fragment {

    public MessagesFragment() {
        // Constructor público requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getView() != null) {
            updateMessagesUI(getView());
        }
    }

    private void updateMessagesUI(View view) {
        boolean isProvider = UserRoleManager.isProviderMode(getContext());

        TextView tvBadge = view.findViewById(R.id.tvMessagesRoleBadge);
        TextView tvChat1Name = view.findViewById(R.id.tvChat1Name);
        TextView tvChat1Snippet = view.findViewById(R.id.tvChat1Snippet);
        TextView tvChat2Name = view.findViewById(R.id.tvChat2Name);
        TextView tvChat2Snippet = view.findViewById(R.id.tvChat2Snippet);
        TextView tvChat3Name = view.findViewById(R.id.tvChat3Name);
        TextView tvChat3Snippet = view.findViewById(R.id.tvChat3Snippet);

        View card1 = view.findViewById(R.id.cardChatItem1);
        View card2 = view.findViewById(R.id.cardChatItem2);
        View card3 = view.findViewById(R.id.cardChatItem3);

        if (isProvider) {
            // MODO PRESTADOR: AZUL ENTERPRISE CON MENSAJES DE CLIENTES
            if (tvBadge != null) {
                tvBadge.setText("Modo Prestador • Consultas de Clientes");
                tvBadge.setBackgroundResource(R.drawable.bg_badge_blue);
                tvBadge.setTextColor(Color.parseColor("#2563EB"));
            }

            if (tvChat1Name != null) tvChat1Name.setText("Cliente Roberto M.");
            if (tvChat1Snippet != null) tvChat1Snippet.setText("¿Tiene disponibilidad para el trabajo de tablero en Av. América mañana?");

            if (tvChat2Name != null) tvChat2Name.setText("Cliente Ana Morales");
            if (tvChat2Snippet != null) tvChat2Snippet.setText("Gracias por la reparación de la cocina, todo quedó funcionando perfecto.");

            if (tvChat3Name != null) tvChat3Name.setText("Cliente Fernando G.");
            if (tvChat3Snippet != null) tvChat3Snippet.setText("¿Acepta pago por transferencia QR o efectivo?");
        } else {
            // MODO CLIENTE: VERDE ESMERALDA CON CHATS DE PRESTADORES
            if (tvBadge != null) {
                tvBadge.setText("Modo Cliente • Chats con Prestadores");
                tvBadge.setBackgroundResource(R.drawable.bg_badge_emerald);
                tvBadge.setTextColor(Color.parseColor("#059669"));
            }

            if (tvChat1Name != null) tvChat1Name.setText("Electricista Carlos Mendoza");
            if (tvChat1Snippet != null) tvChat1Snippet.setText("Perfecto Juan, llego en 10 minutos a tu domicilio.");

            if (tvChat2Name != null) tvChat2Name.setText("Plomero Luis Silva");
            if (tvChat2Snippet != null) tvChat2Snippet.setText("Hola Juan, la cotización para reparar la fuga es de Bs. 40.");

            if (tvChat3Name != null) tvChat3Name.setText("Pintor Alberto Fernández");
            if (tvChat3Snippet != null) tvChat3Snippet.setText("Confirmado el color de pintura lavable para la sala.");
        }

        View.OnClickListener chatClickListener = v -> {
            if (getContext() != null) {
                Toast.makeText(getContext(), "Abriendo conversación...", Toast.LENGTH_SHORT).show();
            }
        };

        if (card1 != null) card1.setOnClickListener(chatClickListener);
        if (card2 != null) card2.setOnClickListener(chatClickListener);
        if (card3 != null) card3.setOnClickListener(chatClickListener);
    }
}