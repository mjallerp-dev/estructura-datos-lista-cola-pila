package com.mjallerp.modelo;

import java.util.Objects;

public class PasajeroEmbarque {
    public static final String PENDIENTE = "Pendiente de embarque";
    public static final String EMBARCADO = "Embarcado";
    public static final String CANCELADO = "Cancelado";

    private String codigoReserva;
    private String nombre;
    private String documento;
    private String numeroVuelo;
    private String destino;
    private String asiento;
    private String estado;

    public PasajeroEmbarque(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public PasajeroEmbarque(String codigoReserva, String nombre, String documento, String numeroVuelo,
                            String destino, String asiento) {
        this.codigoReserva = codigoReserva;
        this.nombre = nombre;
        this.documento = documento;
        this.numeroVuelo = numeroVuelo;
        this.destino = destino;
        this.asiento = asiento;
        this.estado = PENDIENTE;
    }

    public String getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNumeroVuelo() {
        return numeroVuelo;
    }

    public void setNumeroVuelo(String numeroVuelo) {
        this.numeroVuelo = numeroVuelo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PasajeroEmbarque)) {
            return false;
        }
        PasajeroEmbarque otro = (PasajeroEmbarque) o;
        return Objects.equals(codigoReserva, otro.codigoReserva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoReserva);
    }

    @Override
    public String toString() {
        return codigoReserva + " | " + nombre + " | Doc. " + documento + " | Vuelo " + numeroVuelo
                + " -> " + destino + " | Asiento " + asiento + " | " + estado;
    }
}
