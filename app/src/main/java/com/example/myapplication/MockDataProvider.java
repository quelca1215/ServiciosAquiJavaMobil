package com.example.myapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockDataProvider {

    public static List<ProfessionalModel> getAllProfessionals() {
        List<ProfessionalModel> list = new ArrayList<>();

        // Electricidad
        list.add(new ProfessionalModel(
                "1",
                "Ing. Carlos Eduardo Mendoza Ramos",
                "Electricista Industrial & Residencial",
                "Electricidad",
                4.9,
                142,
                1.2,
                "Bs. 50 / cotizar",
                "Disponible Ahora",
                "+51 912 345 678",
                "Especialista en cortocircuitos, tableros trifásicos, instalaciones LED y certificación de tableros. Más de 10 años de experiencia.",
                Arrays.asList("Cortocircuitos", "Tableros Eléctricos", "Iluminación LED", "Atención 24/7"),
                true
        ));

        list.add(new ProfessionalModel(
                "2",
                "Juan Pérez Rodríguez",
                "Técnico Electricista Certificado",
                "Electricidad",
                4.8,
                89,
                2.1,
                "Bs. 45 / cotizar",
                "Respuesta < 15 min",
                "+51 923 456 789",
                "Mantenimiento preventivo y correctivo de hogares y comercios. Garantía de 6 meses en todos los trabajos.",
                Arrays.asList("Cableado", "Tomas y Interruptores", "Garantía 6 meses"),
                true
        ));

        list.add(new ProfessionalModel(
                "2b",
                "Marco Antonio Ramos Delgado",
                "Automatización & Tableros Eléctricos",
                "Electricidad",
                4.9,
                112,
                0.9,
                "Bs. 55 / cotizar",
                "Disponible Ahora",
                "+51 922 111 333",
                "Instalación de luminarias inteligentes, pozos a tierra y tableros térmicos.",
                Arrays.asList("Pozo a Tierra", "Smart Home", "Garantía Pro"),
                true
        ));

        list.add(new ProfessionalModel(
                "2c",
                "Marcos Javier Delgado Tech",
                "Instalaciones Eléctricas Generales",
                "Electricidad",
                4.7,
                65,
                1.8,
                "Bs. 40 / cotizar",
                "Disponible Hoy",
                "+51 944 555 666",
                "Reparación de fugas eléctricas, balanceo de cargas y llaves termomagnéticas.",
                Arrays.asList("Balanceo de Cargas", "Urgencias 24h"),
                false
        ));

        // Plomería / Gasfitería
        list.add(new ProfessionalModel(
                "3",
                "Luis Fernando Silva Morales",
                "Maestro Gasfitero & Plomero",
                "Plomería",
                4.9,
                118,
                1.5,
                "Bs. 40 / cotizar",
                "Disponible Ahora",
                "+51 934 567 890",
                "Detección electrónica de fugas de agua, reparación de griferías, bombas de agua y desatoro de tuberías de desagüe.",
                Arrays.asList("Detección Fugas", "Bombas de Agua", "Desatoro Tuberías", "Presupuesto Gratis"),
                true
        ));

        list.add(new ProfessionalModel(
                "4",
                "Roberto Mangueras & Fugas Sanitaria",
                "Especialista en Instalación Sanitaria",
                "Gasfitería",
                4.7,
                64,
                3.0,
                "Bs. 35 / cotizar",
                "Agenda para hoy",
                "+51 945 678 901",
                "Instalación de termas a gas y eléctricas, sanitarios, lavaderos e hidromasajes.",
                Arrays.asList("Termas a Gas", "Grifería Moderna", "Limpieza Tanques"),
                true
        ));

        list.add(new ProfessionalModel(
                "4b",
                "Gasfitería Express Lima Pro",
                "Desatoros & Redes de Agua",
                "Plomería",
                4.8,
                94,
                1.1,
                "Bs. 45 / cotizar",
                "Disponible Ahora",
                "+51 933 222 111",
                "Equipos con cámara de inspección para tuberías de desagüe sin romper pisos.",
                Arrays.asList("Cámara Inspección", "Desalojo de Agua", "Garantía"),
                true
        ));

        // Hogar
        list.add(new ProfessionalModel(
                "5",
                "Mantenimiento Total Pro - José Vargas",
                "Remodelación & Pintura de Interiores",
                "Hogar",
                4.9,
                205,
                0.8,
                "Desde Bs. 80 / cotizar",
                "Disponible Ahora",
                "+51 956 789 012",
                "Pintura lavable, drywall, armado de muebles en melamina, cerámicos y arreglos generales del hogar.",
                Arrays.asList("Pintura", "Drywall", "Armado Muebles", "Cerámicos"),
                true
        ));

        list.add(new ProfessionalModel(
                "6",
                "Alberto Carpintería & Hogar Fernández",
                "Maestro Carpintero Profesional",
                "Hogar",
                4.8,
                77,
                2.4,
                "A cotizar (Bs.)",
                "Respuesta < 30 min",
                "+51 967 890 123",
                "Reparación de puertas, reposteros de cocina, clósets a medida y restauración de muebles de madera.",
                Arrays.asList("Muebles a Medida", "Clósets", "Puertas de Madera"),
                false
        ));

        // Limpieza
        list.add(new ProfessionalModel(
                "7",
                "Limpieza Profunda EcoClean - María Solís",
                "Servicio de Limpieza Integral",
                "Limpieza",
                5.0,
                310,
                1.1,
                "Bs. 60 / cotizar",
                "Disponible Ahora",
                "+51 978 901 234",
                "Limpieza profunda post-construcción, lavado de alfombras, muebles, vidrios y desinfección con vapor.",
                Arrays.asList("Desinfección Vapor", "Lavado Muebles", "Eco-Friendly"),
                true
        ));

        // Transporte
        list.add(new ProfessionalModel(
                "8",
                "Fletes & Mudanzas Don Pedro Gutiérrez",
                "Transporte de Carga & Mudanzas",
                "Transporte",
                4.8,
                156,
                1.9,
                "Desde Bs. 70 / cotizar",
                "Disponible Ahora",
                "+51 989 012 345",
                "Camión cerrado de 4 toneladas, incluye personal de carga y embalaje seguro de artefactos y muebles.",
                Arrays.asList("Camión Cerrado", "Incluye Carga", "Embalaje Pro", "Seguro Carga"),
                true
        ));

        return list;
    }

    public static List<ProfessionalModel> getProfessionalsByCategory(String categoryName) {
        List<ProfessionalModel> all = getAllProfessionals();
        if (categoryName == null || categoryName.isEmpty() || categoryName.equalsIgnoreCase("Todas") || categoryName.equalsIgnoreCase("Ver todas")) {
            return all;
        }

        List<ProfessionalModel> filtered = new ArrayList<>();
        for (ProfessionalModel p : all) {
            if (p.getCategory().equalsIgnoreCase(categoryName) ||
                (categoryName.equalsIgnoreCase("Gasfitería") && p.getCategory().equalsIgnoreCase("Plomería")) ||
                (categoryName.equalsIgnoreCase("Plomería") && p.getCategory().equalsIgnoreCase("Gasfitería"))) {
                filtered.add(p);
            }
        }

        return filtered.isEmpty() ? all : filtered;
    }
}