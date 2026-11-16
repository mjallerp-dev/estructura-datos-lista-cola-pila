package com.mjallerp.consola;

import com.mjallerp.modelo.PasajeroEmbarque;
import com.mjallerp.sistema.SistemaAeropuerto;

public class MenuAeropuerto {
    private final LectorConsola lector;
    private final SistemaAeropuerto sistema;

    public MenuAeropuerto(LectorConsola lector, SistemaAeropuerto sistema) {
        this.lector = lector;
        this.sistema = sistema;
    }

    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n========== AEROPUERTO - CONTROL DE EMBARQUE ==========");
            System.out.println("1. Registrar pasajero");
            System.out.println("2. Ver todos los pasajeros registrados");
            System.out.println("3. Ver pasajeros pendientes de embarque");
            System.out.println("4. Embarcar siguiente pasajero");
            System.out.println("5. Ver historial de pasajeros embarcados");
            System.out.println("6. Buscar pasajero por código de reserva");
            System.out.println("7. Cancelar pasajero pendiente");
            System.out.println("8. Deshacer último embarque");
            System.out.println("9. Ver cantidad de pasajeros");
            System.out.println("10. Salir");
            opcion = lector.leerEntero("Seleccione una opción: ");
            System.out.println();
            try {
                switch (opcion) {
                    case 1:
                        registrar();
                        break;
                    case 2:
                        verRegistrados();
                        break;
                    case 3:
                        verPendientes();
                        break;
                    case 4:
                        embarcarSiguiente();
                        break;
                    case 5:
                        verHistorial();
                        break;
                    case 6:
                        buscar();
                        break;
                    case 7:
                        cancelar();
                        break;
                    case 8:
                        deshacer();
                        break;
                    case 9:
                        verCantidades();
                        break;
                    case 10:
                        System.out.println("Hasta luego.");
                        break;
                    default:
                        System.out.println("Opción inválida. Elija un número del 1 al 10.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            if (opcion != 10) {
                lector.pausar();
            }
        } while (opcion != 10);
    }

    private void registrar() throws Exception {
        System.out.println("--- Registrar pasajero ---");
        String codigo = lector.leerTexto("Código de reserva (6 caracteres, ej. XK7P2Q): ");
        String nombre = lector.leerTexto("Nombre completo: ");
        String documento = lector.leerTexto("Documento: ");
        String vuelo = lector.leerTexto("Número de vuelo (ej. AV9301): ");
        String destino = lector.leerTexto("Destino: ");
        String asiento = lector.leerTexto("Asiento (ej. 12C): ");

        PasajeroEmbarque pasajero = sistema.registrarPasajero(codigo, nombre, documento, vuelo, destino, asiento);
        System.out.println("\nPasajero registrado: " + pasajero);
        System.out.println("  listaGeneral.agregar(pasajero)       -> posición " + sistema.posicionEnLista(pasajero)
                + " de la lista general");
        System.out.println("  colaPendientes.encolar(pasajero)     -> turno " + sistema.totalPendientes()
                + " de " + sistema.totalPendientes() + " en la cola de embarque");
    }

    private void verRegistrados() {
        System.out.println("--- Pasajeros registrados (Lista general, " + sistema.totalRegistrados() + ") ---");
        sistema.mostrarRegistrados();
        if (sistema.totalRegistrados() > 1 && lector.confirmar("\n¿Ver también en orden inverso (de atrás hacia adelante)?")) {
            sistema.mostrarRegistradosAlReves();
        }
    }

    private void verPendientes() throws Exception {
        System.out.println("--- Pasajeros pendientes de embarque (Cola FIFO, " + sistema.totalPendientes() + ") ---");
        sistema.mostrarPendientes();
        if (sistema.totalPendientes() > 0) {
            System.out.println("Siguiente en embarcar (peek): " + sistema.siguienteEnEmbarcar().getCodigoReserva());
        }
    }

    private void embarcarSiguiente() throws Exception {
        PasajeroEmbarque embarcado = sistema.procesarSiguiente();
        System.out.println("colaPendientes.desencolar() -> embarcó el primero que llegó a la cola:");
        System.out.println("  " + embarcado);
        System.out.println("historialProcesados.apilar(pasajero) -> quedó en la cima del historial.");
        System.out.println("Pendientes restantes: " + sistema.totalPendientes());
    }

    private void verHistorial() throws Exception {
        System.out.println("--- Historial de embarcados (Pila LIFO, " + sistema.totalEmbarcados() + ") ---");
        sistema.mostrarHistorial();
        if (sistema.totalEmbarcados() > 0) {
            System.out.println("Último embarcado (peek): " + sistema.ultimoEmbarcado().getCodigoReserva());
        }
    }

    private void buscar() throws Exception {
        String codigo = lector.leerTexto("Código de reserva a buscar: ");
        PasajeroEmbarque pasajero = sistema.buscarPorCodigo(codigo);
        System.out.println("Pasajero encontrado en la posición " + sistema.posicionEnLista(pasajero) + " de la lista general:");
        System.out.println("  " + pasajero);
        System.out.println("  ¿Está en la cola de pendientes?  " + (sistema.estaPendiente(pasajero) ? "Sí" : "No"));
        int posicionHistorial = sistema.posicionEnHistorial(pasajero);
        System.out.println("  ¿Está en el historial?           "
                + (posicionHistorial == -1 ? "No" : "Sí, posición " + posicionHistorial + " desde la cima"));
    }

    private void cancelar() throws Exception {
        System.out.println("--- Cancelar pasajero pendiente ---");
        sistema.mostrarPendientes();
        if (sistema.totalPendientes() == 0) {
            return;
        }
        String codigo = lector.leerTexto("Código de reserva a cancelar: ");
        PasajeroEmbarque cancelado = sistema.cancelarPendiente(codigo);
        System.out.println("Reserva cancelada usando una cola auxiliar: " + cancelado);
        System.out.println("Cola de pendientes después de cancelar:");
        sistema.mostrarPendientes();
    }

    private void deshacer() throws Exception {
        PasajeroEmbarque devuelto = sistema.deshacerUltimoProcesamiento();
        System.out.println("historialProcesados.desapilar() -> se deshizo el último embarque:");
        System.out.println("  " + devuelto);
        System.out.println("colaPendientes.encolar(pasajero) -> volvió al final de la cola de embarque.");
    }

    private void verCantidades() throws Exception {
        System.out.println("--- Reporte de pasajeros ---");
        System.out.println("Registrados (Lista general):        " + sistema.totalRegistrados());
        System.out.println("Pendientes de embarque (Cola):      " + sistema.totalPendientes());
        System.out.println("Embarcados (Pila de historial):     " + sistema.totalEmbarcados());
        System.out.println("Cancelados (estado en la lista):    " + sistema.totalCancelados());
    }
}
