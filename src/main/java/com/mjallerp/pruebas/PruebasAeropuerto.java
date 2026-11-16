package com.mjallerp.pruebas;

import com.mjallerp.datos.DatosEjemplo;
import com.mjallerp.modelo.PasajeroEmbarque;
import com.mjallerp.sistema.SistemaAeropuerto;

public class PruebasAeropuerto {
    private static int exitosas;
    private static int fallidas;

    public static void main(String[] args) throws Exception {
        SistemaAeropuerto sistema = new SistemaAeropuerto();
        DatosEjemplo.cargar(sistema);

        verificar("Carga inicial: 8 registrados y 8 pendientes",
                sistema.totalRegistrados() == 8 && sistema.totalPendientes() == 8);

        PasajeroEmbarque nuevo = sistema.registrarPasajero("np3x7a", "Daniela Castro", "1009012345", "av9301", "Bogotá", "16a");
        verificar("Registro válido: queda en la lista y al final de la cola",
                sistema.totalRegistrados() == 9 && sistema.totalPendientes() == 9 && sistema.estaPendiente(nuevo)
                        && nuevo.getCodigoReserva().equals("NP3X7A"));

        esperarError("Código de reserva duplicado (XK7P2Q)",
                () -> sistema.registrarPasajero("xk7p2q", "Otra", "1234567", "AV9301", "Bogotá", "30A"));
        esperarError("Asiento ocupado en el mismo vuelo (AV9301 12A)",
                () -> sistema.registrarPasajero("ZZ1111", "Otra", "1234567", "AV9301", "Bogotá", "12A"));
        esperarError("Código de reserva con formato inválido",
                () -> sistema.registrarPasajero("ABC", "Otra", "1234567", "AV9301", "Bogotá", "30A"));
        esperarError("Nombre vacío",
                () -> sistema.registrarPasajero("ZZ2222", "   ", "1234567", "AV9301", "Bogotá", "30A"));
        esperarError("Asiento inválido (12Z)",
                () -> sistema.registrarPasajero("ZZ3333", "Otra", "1234567", "AV9301", "Bogotá", "12Z"));

        PasajeroEmbarque primero = sistema.procesarSiguiente();
        PasajeroEmbarque segundo = sistema.procesarSiguiente();
        verificar("FIFO: embarcan primero XK7P2Q y luego LM4R8T",
                primero.getCodigoReserva().equals("XK7P2Q") && segundo.getCodigoReserva().equals("LM4R8T"));
        verificar("LIFO: la cima del historial es el último embarcado (LM4R8T)",
                sistema.ultimoEmbarcado().equals(segundo) && sistema.posicionEnHistorial(primero) == 2);

        PasajeroEmbarque deshecho = sistema.deshacerUltimoProcesamiento();
        verificar("Deshacer devuelve LM4R8T al final de la cola como pendiente",
                deshecho.equals(segundo) && PasajeroEmbarque.PENDIENTE.equals(deshecho.getEstado())
                        && sistema.estaPendiente(deshecho) && sistema.totalEmbarcados() == 1);

        PasajeroEmbarque cancelado = sistema.cancelarPendiente("rt5k1u");
        verificar("Cancelar RT5K1U (en el centro de la cola) con cola auxiliar",
                PasajeroEmbarque.CANCELADO.equals(cancelado.getEstado()) && !sistema.estaPendiente(cancelado)
                        && sistema.totalPendientes() == 7 && sistema.totalRegistrados() == 9);
        verificar("El orden de los demás pendientes se conserva", sistema.siguienteEnEmbarcar().getCodigoReserva().equals("QZ9W3E"));
        esperarError("No se puede cancelar un pasajero embarcado (XK7P2Q)", () -> sistema.cancelarPendiente("XK7P2Q"));
        esperarError("No se puede cancelar dos veces (RT5K1U)", () -> sistema.cancelarPendiente("RT5K1U"));
        esperarError("Cancelar un código inexistente", () -> sistema.cancelarPendiente("NO0000"));

        PasajeroEmbarque buscado = sistema.buscarPorCodigo("jd8v4c");
        verificar("Búsqueda por código en la lista general (JD8V4C)",
                buscado.getNombre().equals("Jorge Díaz") && sistema.posicionEnLista(buscado) == 5);
        esperarError("Búsqueda de un código inexistente", () -> sistema.buscarPorCodigo("NO0000"));

        PasajeroEmbarque libre = sistema.registrarPasajero("ZZ4444", "Pedro Lara", "1010101010", "LA4052", "Medellín", "7D");
        verificar("El asiento de un pasajero cancelado se puede reasignar (LA4052 7D)", libre.getAsiento().equals("7D"));

        while (sistema.totalPendientes() > 0) {
            sistema.procesarSiguiente();
        }
        verificar("Todos los pendientes quedan en el historial", sistema.totalEmbarcados() == 9 && sistema.totalCancelados() == 1);
        esperarError("Embarcar con la cola vacía", sistema::procesarSiguiente);

        while (sistema.totalEmbarcados() > 0) {
            sistema.deshacerUltimoProcesamiento();
        }
        esperarError("Deshacer con el historial vacío", sistema::deshacerUltimoProcesamiento);

        System.out.println("\nResultado: " + exitosas + " exitosas, " + fallidas + " fallidas");
    }

    private interface Accion {
        void ejecutar() throws Exception;
    }

    private static void verificar(String nombre, boolean ok) {
        if (ok) {
            exitosas++;
        } else {
            fallidas++;
        }
        System.out.println("[" + (ok ? "OK" : "FALLO") + "] " + nombre);
    }

    private static void esperarError(String nombre, Accion accion) {
        try {
            accion.ejecutar();
            verificar(nombre + " -> no lanzó error", false);
        } catch (Exception e) {
            verificar(nombre + " -> " + e.getMessage(), true);
        }
    }
}
