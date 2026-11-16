package com.mjallerp.datos;

import com.mjallerp.sistema.SistemaAeropuerto;

public final class DatosEjemplo {
    private DatosEjemplo() {
    }

    public static void cargar(SistemaAeropuerto sistema) throws Exception {
        sistema.registrarPasajero("XK7P2Q", "Ana María Torres", "1001234567", "AV9301", "Bogotá", "12A");
        sistema.registrarPasajero("LM4R8T", "Carlos Pérez", "1002345678", "AV9301", "Bogotá", "12B");
        sistema.registrarPasajero("QZ9W3E", "Laura Gómez", "1003456789", "LA4052", "Medellín", "3C");
        sistema.registrarPasajero("BN2H6Y", "Andrés Ruiz", "1004567890", "AV9301", "Bogotá", "14F");
        sistema.registrarPasajero("RT5K1U", "Sofía Martínez", "1005678901", "LA4052", "Medellín", "7D");
        sistema.registrarPasajero("JD8V4C", "Jorge Díaz", "1006789012", "CM0805", "Panamá", "21A");
        sistema.registrarPasajero("PW3S9N", "Valentina Rojas", "1007890123", "CM0805", "Panamá", "21B");
        sistema.registrarPasajero("HG6F2M", "Mateo Herrera", "1008901234", "AV9301", "Bogotá", "15C");
    }
}
