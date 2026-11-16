package com.mjallerp.sistema;

import com.mjallerp.estructuras.Cola;
import com.mjallerp.estructuras.Lista;
import com.mjallerp.estructuras.Pila;
import com.mjallerp.modelo.PasajeroEmbarque;

public class SistemaAeropuerto {
    private final Lista listaGeneral;
    private final Cola colaPendientes;
    private final Pila historialProcesados;

    public SistemaAeropuerto() {
        listaGeneral = new Lista();
        colaPendientes = new Cola();
        historialProcesados = new Pila();
    }

    public PasajeroEmbarque registrarPasajero(String codigoReserva, String nombre, String documento,
                                              String numeroVuelo, String destino, String asiento) throws Exception {
        String codigo = validarCodigoReserva(codigoReserva);
        String nombreValido = validarTexto(nombre, "El nombre del pasajero");
        String documentoValido = validarTexto(documento, "El documento");
        if (!documentoValido.matches("\\d{5,12}")) {
            throw new Exception("El documento debe tener entre 5 y 12 dígitos");
        }
        String vuelo = validarTexto(numeroVuelo, "El número de vuelo").toUpperCase();
        if (!vuelo.matches("[A-Z0-9]{2}\\d{1,4}")) {
            throw new Exception("El número de vuelo debe tener 2 letras de aerolínea y de 1 a 4 dígitos (ej. AV9301)");
        }
        String destinoValido = validarTexto(destino, "El destino");
        String asientoValido = validarTexto(asiento, "El asiento").toUpperCase();
        if (!asientoValido.matches("\\d{1,2}[A-F]")) {
            throw new Exception("El asiento debe tener fila y letra de la A a la F (ej. 12C)");
        }

        if (listaGeneral.contiene(new PasajeroEmbarque(codigo))) {
            throw new Exception("Ya existe un pasajero con el código de reserva " + codigo);
        }
        PasajeroEmbarque ocupante = buscarAsientoOcupado(vuelo, asientoValido);
        if (ocupante != null) {
            throw new Exception("El asiento " + asientoValido + " del vuelo " + vuelo
                    + " ya está asignado a la reserva " + ocupante.getCodigoReserva());
        }

        PasajeroEmbarque pasajero = new PasajeroEmbarque(codigo, nombreValido, documentoValido, vuelo,
                destinoValido, asientoValido);
        listaGeneral.agregar(pasajero);
        colaPendientes.encolar(pasajero);
        return pasajero;
    }

    public PasajeroEmbarque procesarSiguiente() throws Exception {
        if (colaPendientes.esVacia()) {
            throw new Exception("No hay pasajeros pendientes de embarque");
        }
        PasajeroEmbarque procesado = (PasajeroEmbarque) colaPendientes.desencolar();
        procesado.setEstado(PasajeroEmbarque.EMBARCADO);
        historialProcesados.apilar(procesado);
        return procesado;
    }

    public PasajeroEmbarque deshacerUltimoProcesamiento() throws Exception {
        if (historialProcesados.esVacia()) {
            throw new Exception("No hay embarques para deshacer");
        }
        PasajeroEmbarque ultimo = (PasajeroEmbarque) historialProcesados.desapilar();
        ultimo.setEstado(PasajeroEmbarque.PENDIENTE);
        colaPendientes.encolar(ultimo);
        return ultimo;
    }

    public PasajeroEmbarque cancelarPendiente(String codigoReserva) throws Exception {
        PasajeroEmbarque pasajero = buscarPorCodigo(codigoReserva);
        if (!PasajeroEmbarque.PENDIENTE.equals(pasajero.getEstado())) {
            throw new Exception("La reserva " + pasajero.getCodigoReserva() + " no está pendiente; estado actual: "
                    + pasajero.getEstado());
        }

        Cola colaAuxiliar = new Cola();
        PasajeroEmbarque cancelado = null;
        while (!colaPendientes.esVacia()) {
            PasajeroEmbarque actual = (PasajeroEmbarque) colaPendientes.desencolar();
            if (actual.equals(pasajero)) {
                cancelado = actual;
            } else {
                colaAuxiliar.encolar(actual);
            }
        }
        while (!colaAuxiliar.esVacia()) {
            colaPendientes.encolar(colaAuxiliar.desencolar());
        }

        if (cancelado == null) {
            throw new Exception("La reserva " + pasajero.getCodigoReserva() + " no está en la cola de pendientes");
        }
        cancelado.setEstado(PasajeroEmbarque.CANCELADO);
        return cancelado;
    }

    public PasajeroEmbarque buscarPorCodigo(String codigoReserva) throws Exception {
        String codigo = validarTexto(codigoReserva, "El código de reserva").toUpperCase();
        if (!listaGeneral.contiene(new PasajeroEmbarque(codigo))) {
            throw new Exception("No existe un pasajero con el código de reserva " + codigo);
        }
        int posicion = listaGeneral.buscarDato(new PasajeroEmbarque(codigo));
        return (PasajeroEmbarque) listaGeneral.buscarDato(posicion);
    }

    public int posicionEnLista(PasajeroEmbarque pasajero) throws Exception {
        return listaGeneral.buscarDato(pasajero);
    }

    public int posicionEnHistorial(PasajeroEmbarque pasajero) {
        return historialProcesados.buscar(pasajero);
    }

    public boolean estaPendiente(PasajeroEmbarque pasajero) {
        return colaPendientes.contiene(pasajero);
    }

    public PasajeroEmbarque siguienteEnEmbarcar() throws Exception {
        return (PasajeroEmbarque) colaPendientes.peek();
    }

    public PasajeroEmbarque ultimoEmbarcado() throws Exception {
        return (PasajeroEmbarque) historialProcesados.peek();
    }

    public void mostrarRegistrados() {
        listaGeneral.mostrarAdelante();
    }

    public void mostrarRegistradosAlReves() {
        listaGeneral.mostrarAtras();
    }

    public void mostrarPendientes() {
        colaPendientes.mostrar();
    }

    public void mostrarHistorial() {
        historialProcesados.mostrar();
    }

    public int totalRegistrados() {
        return listaGeneral.cuentaElementos();
    }

    public int totalPendientes() {
        return colaPendientes.tamanio();
    }

    public int totalEmbarcados() {
        return historialProcesados.tamanio();
    }

    public int totalCancelados() throws Exception {
        int cancelados = 0;
        for (int i = 0; i < listaGeneral.cuentaElementos(); i++) {
            PasajeroEmbarque pasajero = (PasajeroEmbarque) listaGeneral.buscarDato(i);
            if (PasajeroEmbarque.CANCELADO.equals(pasajero.getEstado())) {
                cancelados++;
            }
        }
        return cancelados;
    }

    private PasajeroEmbarque buscarAsientoOcupado(String vuelo, String asiento) throws Exception {
        for (int i = 0; i < listaGeneral.cuentaElementos(); i++) {
            PasajeroEmbarque pasajero = (PasajeroEmbarque) listaGeneral.buscarDato(i);
            boolean mismoAsiento = pasajero.getNumeroVuelo().equals(vuelo) && pasajero.getAsiento().equals(asiento);
            if (mismoAsiento && !PasajeroEmbarque.CANCELADO.equals(pasajero.getEstado())) {
                return pasajero;
            }
        }
        return null;
    }

    private String validarCodigoReserva(String codigoReserva) throws Exception {
        String codigo = validarTexto(codigoReserva, "El código de reserva").toUpperCase();
        if (!codigo.matches("[A-Z0-9]{6}")) {
            throw new Exception("El código de reserva debe tener exactamente 6 letras o números (ej. XK7P2Q)");
        }
        return codigo;
    }

    private String validarTexto(String valor, String campo) throws Exception {
        if (valor == null || valor.trim().isEmpty()) {
            throw new Exception(campo + " es obligatorio");
        }
        return valor.trim().replaceAll("\\s+", " ");
    }
}
