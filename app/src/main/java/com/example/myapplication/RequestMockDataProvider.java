package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class RequestMockDataProvider {

    private static List<ServiceRequestModel> clientRequests = null;
    private static List<ServiceRequestModel> providerRequests = null;

    public static List<ServiceRequestModel> getClientRequests() {
        if (clientRequests == null) {
            clientRequests = new ArrayList<>();
            clientRequests.add(new ServiceRequestModel(
                    "REQ-C01",
                    "Instalación de Tablero Trifásico Comercial",
                    "Electricidad",
                    "Juan Pérez",
                    "Ing. Carlos Eduardo Mendoza Ramos",
                    "Revisión de cableado general, montaje de gabinete térmico y balanceo de cargas en local comercial.",
                    "Av. América y Calle 4, Zona Central",
                    "Bs. 150",
                    "Bs. 180",
                    "Viernes, 17 Octubre",
                    "Mañana (09:00 AM - 12:00 PM)",
                    "3 - 4 horas estimadas",
                    "Sábado 18 Oct, 02:00 PM (Duración: 3.5h)",
                    "CONTRAOFERTA",
                    "Hoy, 10:30 AM"
            ));

            clientRequests.add(new ServiceRequestModel(
                    "REQ-C02",
                    "Detección y Reparación de Fuga en Baño",
                    "Plomería",
                    "Juan Pérez",
                    "Luis Fernando Silva Morales",
                    "Fuga de agua constante debajo del lavadero principal. Se requiere reparación urgente sin romper cerámica.",
                    "Calle Sucre #240, Zona Norte",
                    "Bs. 40",
                    "",
                    "Hoy mismo",
                    "Tarde (02:00 PM - 05:00 PM)",
                    "2 horas estimadas",
                    "",
                    "ACEPTADA",
                    "Ayer, 04:15 PM"
            ));

            clientRequests.add(new ServiceRequestModel(
                    "REQ-C03",
                    "Mantenimiento General de Calefón a Gas",
                    "Gasfitería",
                    "Juan Pérez",
                    "Roberto Mangueras & Fugas",
                    "Limpieza de inyectores y revisión de chispero automático en calefón de 12 litros.",
                    "Av. Ballivián #102",
                    "Bs. 60",
                    "",
                    "Lunes, 20 Octubre",
                    "Mañana (08:30 AM - 11:30 AM)",
                    "1.5 horas estimadas",
                    "",
                    "PENDIENTE",
                    "13 Oct, 09:00 AM"
            ));
        }
        return clientRequests;
    }

    public static List<ServiceRequestModel> getProviderRequests() {
        if (providerRequests == null) {
            providerRequests = new ArrayList<>();
            providerRequests.add(new ServiceRequestModel(
                    "REQ-P01",
                    "Instalación de Punto Eléctrico & Tomas Smart",
                    "Electricidad",
                    "Roberto M. (Cliente)",
                    "Juan Pérez (Especialista)",
                    "Instalación de 4 tomas de corriente dobles y punto de luz en cocina reformada.",
                    "Av. Heroínas y España #450",
                    "Bs. 120",
                    "",
                    "Mañana, 16 Octubre",
                    "Tarde (03:00 PM - 06:00 PM)",
                    "2.5 horas estimadas",
                    "",
                    "PENDIENTE",
                    "Hoy, 11:20 AM"
            ));

            providerRequests.add(new ServiceRequestModel(
                    "REQ-P02",
                    "Reparación de Cortocircuito en Cocina",
                    "Electricidad",
                    "Ana Morales (Cliente)",
                    "Juan Pérez (Especialista)",
                    "Salta la llave térmica principal al encender el horno eléctrico. Se requiere revisión urgente.",
                    "Calle Jordán #310",
                    "Bs. 100",
                    "",
                    "Ayer, 15 Octubre",
                    "Mañana (10:00 AM - 12:30 PM)",
                    "2 horas estimadas",
                    "",
                    "ACEPTADA",
                    "Ayer, 02:40 PM"
            ));

            providerRequests.add(new ServiceRequestModel(
                    "REQ-P03",
                    "Armado e Instalación de Tablero Residencial",
                    "Electricidad",
                    "Fernando G. (Cliente)",
                    "Juan Pérez (Especialista)",
                    "Montaje de tablero de 8 polos con llaves diferenciales y térmicas.",
                    "Av. Blanco Galindo km 3",
                    "Bs. 160",
                    "Bs. 200",
                    "Sábado, 18 Octubre",
                    "Mañana (09:00 AM - 01:00 PM)",
                    "4 horas estimadas",
                    "Domingo 19 Oct, 09:00 AM (Duración: 4h)",
                    "CONTRAOFERTA",
                    "12 Oct, 03:00 PM"
            ));
        }
        return providerRequests;
    }
}